package com.greenprotect.am.service;

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

/**
 * Manager class that handles the activity reports
 */
public class ActivityManager {
	FileReader fileReader;
	BufferedReader reader;

	/*
	 * This method is used to group the view objects with (title,precedingOfficer)
	 * as key Params: dataFileName Returns: Map of grouped activity details.
	 */
	public Map<ActivityReportKey, List<ActivityDetailsVO>> getGroupedActivityDetails(String filePath)
			throws ActivityParseException {

		Map<ActivityReportKey, List<ActivityDetailsVO>> groupedActivityMap = loadAllActivityDetails(filePath)
				.stream()
				.collect(Collectors.groupingBy(a -> new ActivityReportKey(a.getTitle(), a.getPrecedingOfficer())));
		
		return groupedActivityMap;
	}

	/*
	 * This method is used to get the view objects with searched text Params:
	 * dataFileName and location Returns: Map of searched activity details.
	 */
	public Map<String, Set<ActivityDetailsVO>> getSearchedActivityDetails(String filePath, String searchText)
			throws ActivityParseException {

		Map<String, Set<ActivityDetailsVO>> searchedActivityMap = new HashMap<String, Set<ActivityDetailsVO>>();

		Set<ActivityDetailsVO> activitiesSet = loadAllActivityDetails(filePath)
			.stream()
			.filter(a -> a.getLocation().equals(searchText))
			.sorted((a1,a2) -> a2.getActualConductionDate().compareTo(a1.getActualConductionDate()))
			.collect(Collectors.toSet());
		
		searchedActivityMap.put(searchText, activitiesSet);
		
		return searchedActivityMap;
	}

	/*
	 * This method is used to get all the Activity details Param: dataFileName
	 * Returns: List of Pojo values from data Files.
	 */
	public List<ActivityDetailsVO> loadAllActivityDetails(String filePath) throws ActivityParseException {

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
				if ((length<3) || (length>20)) {
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
				throw new InvalidActivityUncheckedException(activity+"::"+errors.toString());
			}

		}

		return valid;
	}
}

/* Activity Value Object */
class ActivityDetailsVO {
	private int activityId;
	private String title;
	private String location;
	private double budget;
	private LocalDate expectedConductionDate;
	private LocalDate actualConductionDate;
	private String precedingOfficer;
	private List<String> Participants;

	public ActivityDetailsVO() {

	}

	public ActivityDetailsVO(int activityId, String title, String location, double budget,
			LocalDate expectedConductionDate, LocalDate actualConductionDate, String precedingOfficer,
			List<String> participants) {
		super();
		this.activityId = activityId;
		this.title = title;
		this.location = location;
		this.budget = budget;
		this.expectedConductionDate = expectedConductionDate;
		this.actualConductionDate = actualConductionDate;
		this.precedingOfficer = precedingOfficer;
		Participants = participants;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public LocalDate getExpectedConductionDate() {
		return expectedConductionDate;
	}

	public void setExpectedConductionDate(LocalDate expectedConductionDate) {
		this.expectedConductionDate = expectedConductionDate;
	}

	public LocalDate getActualConductionDate() {
		return actualConductionDate;
	}

	public void setActualConductionDate(LocalDate actualConductionDate) {
		this.actualConductionDate = actualConductionDate;
	}

	public String getPrecedingOfficer() {
		return precedingOfficer;
	}

	public void setPrecedingOfficer(String precedingOfficer) {
		this.precedingOfficer = precedingOfficer;
	}

	public List<String> getParticipants() {
		return Participants;
	}

	public void setParticipants(List<String> participants) {
		Participants = participants;
	}

	@Override
	public String toString() {
		return "ActivityDetailsVO [activityId=" + activityId + ", title=" + title + ", location=" + location
				+ ", budget=" + budget + ", expectedConductionDate=" + expectedConductionDate
				+ ", actualConductionDate=" + actualConductionDate + ", precedingOfficer=" + precedingOfficer
				+ ", Participants=" + Participants + "]\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Participants == null) ? 0 : Participants.hashCode());
		result = prime * result + activityId;
		result = prime * result + ((actualConductionDate == null) ? 0 : actualConductionDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(budget);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((expectedConductionDate == null) ? 0 : expectedConductionDate.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((precedingOfficer == null) ? 0 : precedingOfficer.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityDetailsVO other = (ActivityDetailsVO) obj;
		if (Participants == null) {
			if (other.Participants != null)
				return false;
		} else if (!Participants.equals(other.Participants))
			return false;
		if (activityId != other.activityId)
			return false;
		if (actualConductionDate == null) {
			if (other.actualConductionDate != null)
				return false;
		} else if (!actualConductionDate.equals(other.actualConductionDate))
			return false;
		if (Double.doubleToLongBits(budget) != Double.doubleToLongBits(other.budget))
			return false;
		if (expectedConductionDate == null) {
			if (other.expectedConductionDate != null)
				return false;
		} else if (!expectedConductionDate.equals(other.expectedConductionDate))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (precedingOfficer == null) {
			if (other.precedingOfficer != null)
				return false;
		} else if (!precedingOfficer.equals(other.precedingOfficer))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}

/* Activity Report Key */
class ActivityReportKey {
	private String title;
	private String precedingOfficer;

	public ActivityReportKey() {

	}

	public ActivityReportKey(String title, String precedingOfficer) {
		super();
		this.title = title;
		this.precedingOfficer = precedingOfficer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrecedingOfficer() {
		return precedingOfficer;
	}

	public void setPrecedingOfficer(String precedingOfficer) {
		this.precedingOfficer = precedingOfficer;
	}

	@Override
	public String toString() {
		return "ActivityReportKey [title=" + title + ", precedingOfficer=" + precedingOfficer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((precedingOfficer == null) ? 0 : precedingOfficer.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityReportKey other = (ActivityReportKey) obj;
		if (precedingOfficer == null) {
			if (other.precedingOfficer != null)
				return false;
		} else if (!precedingOfficer.equals(other.precedingOfficer))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}

/* User defined Exceptions */
class InvalidActivityUncheckedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidActivityUncheckedException(String message) {
		super(message);
	}

	public InvalidActivityUncheckedException(Throwable throwable) {
		super(throwable);
	}
}

/* User defined Exceptions */
class ActivityParseException extends Exception {
	private static final long serialVersionUID = 1L;

	public ActivityParseException(String message) {
		super(message);
	}

	public ActivityParseException(Throwable throwable) {
		super(throwable);
	}
}