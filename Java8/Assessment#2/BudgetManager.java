package com.greenprotect.am.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Manager class that handles the budget reports
 */
public class BudgetManager {
	
	/*
	 * This method should return the aggregate on budget grouped by the field given.
	 * Throw Exception if the field name is not one of title,location,precedingOfficer.
	 * params: Field Name,Aggregate
	 * output: Map<String,Dobule> where key being field and aggregate on budget being value
	 */
	public Map<String, Double> getFieldWiseBudget(String field, Aggregate aggregate) throws IllegalFieldNameException {

		List<ActivityDetailsVO> activites = ActivityDetailsRepo.getActivities();

		Map<String, Double> result = null;


		return result;
	}

	/*
	 * This method return the activity whose budget is the highest.
	 */
	public ActivityDetailsVO getCostliestActivity() throws IllegalFieldNameException {
		
		List<ActivityDetailsVO> activites = ActivityDetailsRepo.getActivities();
		ActivityDetailsVO activity=null;
				
		return activity;
	}
	
	/*
	 * This method should return the total budget grouped by MonthAndYear.
	 * output: Map<String,Dobule> where key being MoanthAndYear in MMM-yyyy format
	 * and total budget being value
	 */
	public Map<String, Double> getMonthWiseBudget() {

		List<ActivityDetailsVO> activites = ActivityDetailsRepo.getActivities();

		Map<String, Double> result = null;
		
		return result;
	}
}

enum Aggregate {
	SUM, MIN, MAX;
}

/* Activity Value Object */
class ActivityDetailsVO {
	private int activityId;
	private String title;
	private String location;
	private double budget;
	private LocalDate activityDate;
	private String precedingOfficer;

	public ActivityDetailsVO() {

	}

	public ActivityDetailsVO(int activityId, String title, String location, double budget, LocalDate activityDate,
			String precedingOfficer) {
		super();
		this.activityId = activityId;
		this.title = title;
		this.location = location;
		this.budget = budget;
		this.activityDate = activityDate;
		this.precedingOfficer = precedingOfficer;
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

	public LocalDate getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(LocalDate activityDate) {
		this.activityDate = activityDate;
	}

	public String getPrecedingOfficer() {
		return precedingOfficer;
	}

	public void setPrecedingOfficer(String precedingOfficer) {
		this.precedingOfficer = precedingOfficer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityDate == null) ? 0 : activityDate.hashCode());
		result = prime * result + activityId;
		long temp;
		temp = Double.doubleToLongBits(budget);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (activityDate == null) {
			if (other.activityDate != null)
				return false;
		} else if (!activityDate.equals(other.activityDate))
			return false;
		if (activityId != other.activityId)
			return false;
		if (Double.doubleToLongBits(budget) != Double.doubleToLongBits(other.budget))
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

	@Override
	public String toString() {
		return "ActivityDetailsVO [activityId=" + activityId + ", title=" + title + ", location=" + location
				+ ", budget=" + budget + ", activityDate=" + activityDate + ", precedingOfficer=" + precedingOfficer
				+ "]\n";
	}

}

/* User defined Exceptions */
class IllegalFieldNameException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalFieldNameException(String message) {
		super(message);
	}

	public IllegalFieldNameException(Throwable throwable) {
		super(throwable);
	}
}

/* Data Repository */
class ActivityDetailsRepo {
	private static List<ActivityDetailsVO> activities;

	public static List<ActivityDetailsVO> getActivities() {

		if (activities == null) {
			activities = new ArrayList<ActivityDetailsVO>() {
				{
					add(new ActivityDetailsVO(1, "CleanAndGreen", "Visakhapatnam", 22000,
							LocalDate.of(2020, Month.DECEMBER, 10), "Vamsy"));
					add(new ActivityDetailsVO(2, "CleanAndGreen", "Banglore", 40000,
							LocalDate.of(2020, Month.NOVEMBER, 11), "Vamsy"));
					add(new ActivityDetailsVO(3, "CleanAndGreen", "Vijayawada", 30000,
							LocalDate.of(2020, Month.NOVEMBER, 12), "Vamsy"));
					add(new ActivityDetailsVO(4, "CleanAndGreen", "Pune", 35000, LocalDate.of(2020, Month.NOVEMBER, 13),
							"Vamsy"));
					add(new ActivityDetailsVO(5, "OrganicCultivation", "Pune", 36000,
							LocalDate.of(2020, Month.NOVEMBER, 14), "Vamsy"));
					add(new ActivityDetailsVO(6, "OrganicCultivation", "Vijayawada", 29000,
							LocalDate.of(2020, Month.NOVEMBER, 30), "Srinu"));
					add(new ActivityDetailsVO(7, "PollutionControl", "Banglore", 20000,
							LocalDate.of(2020, Month.NOVEMBER, 11), "Srinu"));
					add(new ActivityDetailsVO(8, "PollutionControl", "Visakhapatnam", 24500,
							LocalDate.of(2020, Month.APRIL, 12), "Srinu"));
					add(new ActivityDetailsVO(9, "PaperLessDining", "Banglore", 35000,
							LocalDate.of(2020, Month.NOVEMBER, 13), "Srinu"));
					add(new ActivityDetailsVO(10, "PollutionControl", "Pune", 36000,
							LocalDate.of(2020, Month.NOVEMBER, 14), "Srinu"));
				}
			};
		}

		return activities;
	}
}
