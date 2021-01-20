package com.greenprotect.am.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manager class that handles the activity reports 
 */
public class ActivityManager {
	FileReader fileReader;
	BufferedReader reader;

	/* 
	 * This method is used to group the view objects with (location,precedingOfficer) as key
	 * Params: dataFileName 
	 * Returns: Map of grouped activity details. 
	 * */
	public Map<ActivityReportKey, List<ActivityDetailsVO>> getGroupedActivityDetails(String filePath) 
			throws ActivityParseException {

		Map<ActivityReportKey, List<ActivityDetailsVO>> groupedActivityMap = new HashMap<ActivityReportKey, List<ActivityDetailsVO>>(); 
		
		return groupedActivityMap;
	}

	
	/* 
	 * This method is used to get the view objects with searched text
	 * Params: dataFileName and Activity title
	 * Returns: Map of searched activity details. 
	 * */
	public Map<String, Set<ActivityDetailsVO>> getSearchedActivityDetails(String filePath, String searchText) 
			throws ActivityParseException {

		Map<String, Set<ActivityDetailsVO>> searchedActivityMap = new HashMap<String, Set<ActivityDetailsVO>>(); 
		
		return searchedActivityMap;
	}
	
	
	/* This method is used to get all the Activity details
	 * Param: dataFileName
	 * Returns: List of Pojo values from data Files. 
	 * */
	public List<ActivityDetailsVO> loadAllActivityDetails(String filePath)
			throws ActivityParseException{
			
			List<ActivityDetailsVO> activityDetailsObjList = new ArrayList<ActivityDetailsVO>();
			
			return activityDetailsObjList;
		}
}

/*Activity Value Object*/
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
	
}	

/*Activity Report Key */
class ActivityReportKey {
	private String location;
	private String precedingOfficer;
	
	public ActivityReportKey() {
	
	}
	
	public ActivityReportKey(String location, String precedingOfficer) {
		super();
		this.location = location;
		this.precedingOfficer = precedingOfficer;
	}

	@Override
	public String toString() {
		return "ActivityReportKey [location=" + location + ", precedingOfficer=" + precedingOfficer + "]";
	}
}


/* User defined Exception */
class ActivityParseException extends Exception {
	private static final long serialVersionUID = 1L;

	public ActivityParseException(String message) {
		super(message);
	}

	public ActivityParseException(Throwable throwable) {
		super(throwable);
	}
}