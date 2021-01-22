package com.greenprotect.am.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ActivityManagerTest {

	private final String DATA_FILE_NAME = "./data/ActivityReport.txt";
	private ActivityManager activityManager = new ActivityManager();

	@Test
	void testLoadAllActivityDetails() {

		try {
			List<ActivityDetailsVO> expected = loadAllActivityDetails(DATA_FILE_NAME);

			List<ActivityDetailsVO> actual = activityManager.loadAllActivityDetails(DATA_FILE_NAME);

			assertIterableEquals(expected, actual);
		} catch (ActivityParseException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetGroupedActivityDetails() {
		try {

			Map<ActivityReportKey, List<ActivityDetailsVO>> expected = getGroupedActivityDetails(DATA_FILE_NAME);

			Map<ActivityReportKey, List<ActivityDetailsVO>> actual = activityManager
					.getGroupedActivityDetails(DATA_FILE_NAME);

			assertEquals(expected, actual);

		} catch (ActivityParseException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetSearchedActivityDetails() {
		try {

			Map<String, Set<ActivityDetailsVO>> expected = getSearchedActivityDetails(DATA_FILE_NAME, "Pune");
			Map<String, Set<ActivityDetailsVO>> actual = activityManager.getSearchedActivityDetails(DATA_FILE_NAME, "Pune");
			
			assertEquals(expected, actual);

		} catch (ActivityParseException e) {
			fail(e.getMessage());
		}
	}

	private Map<ActivityReportKey, List<ActivityDetailsVO>> getGroupedActivityDetails(String filePath)
			throws ActivityParseException {

		Map<ActivityReportKey, List<ActivityDetailsVO>> groupedActivityMap = loadAllActivityDetails(filePath).stream()
				.collect(Collectors.groupingBy(a -> new ActivityReportKey(a.getTitle(), a.getPrecedingOfficer())));

		return groupedActivityMap;
	}

	private Map<String, Set<ActivityDetailsVO>> getSearchedActivityDetails(String filePath, String searchText)
			throws ActivityParseException {

		Map<String, Set<ActivityDetailsVO>> searchedActivityMap = new HashMap<String, Set<ActivityDetailsVO>>();

		Set<ActivityDetailsVO> activitiesSet = loadAllActivityDetails(filePath).stream()
				.filter(a -> a.getLocation().equals(searchText))
				.sorted((a1, a2) -> a2.getActualConductionDate().compareTo(a1.getActualConductionDate()))
				.collect(Collectors.toSet());

		searchedActivityMap.put(searchText, activitiesSet);

		return searchedActivityMap;
	}

	private List<ActivityDetailsVO> loadAllActivityDetails(String filePath) throws ActivityParseException {

		List<ActivityDetailsVO> activityDetailsObjList = new ArrayList<ActivityDetailsVO>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

			DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			activityDetailsObjList = reader.lines().skip(1).map(line -> {
				String[] arr = line.split(",");

				ActivityDetailsVO record = new ActivityDetailsVO(Integer.parseInt(arr[0]), arr[1], arr[2],
						Double.parseDouble(arr[3]), LocalDate.parse(arr[4], dateFmt), LocalDate.parse(arr[5], dateFmt),
						arr[6], Arrays.asList(arr[7].split(";")));

				return record;
			}).filter(b -> isValid(b)).collect(Collectors.toList());

		} catch (IOException exp) {
			throw new ActivityParseException(exp);
		}

		return activityDetailsObjList;
	}

	private boolean isValid(ActivityDetailsVO activity) throws InvalidActivityUncheckedException {

		boolean valid = false;

		List<String> errors = new ArrayList<String>();

		if (activity != null) {
			if (activity.getActivityId() <= 0) {
				errors.add("ActivityId can not be zero or negative");
			}

			if (activity.getTitle() == null || activity.getTitle().isEmpty()) {
				errors.add("Ttile can not be null or empty ");
			} else {
				int length = activity.getTitle().length();
				if ((length < 3) || (length > 20)) {
					errors.add("Title should be a String of 3 to 20 characters ");
				}
			}
			if (activity.getExpectedConductionDate() == null
					|| activity.getExpectedConductionDate().isAfter(LocalDate.now())) {
				errors.add("Expected Conduction Date is supposed to be a past or present one.");
			} else if (activity.getActualConductionDate() == null
					|| activity.getActualConductionDate().isBefore(activity.getExpectedConductionDate())) {
				{
					errors.add("Actual Conduction Date is supposed to be post the expected one.");
				}
			}

			valid = errors.isEmpty();

			if (!valid) {
				throw new InvalidActivityUncheckedException(activity + "::" + errors.toString());
			}

		}

		return valid;
	}
}
