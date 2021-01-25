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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BudgetManagerTest {

	private BudgetManager budgetManager = new BudgetManager();

	@Test
	@DisplayName("should throw exception given invalid field ")
	void testGetFieldWiseBudget1() {
		assertThrows(IllegalFieldNameException.class, 
				()->{budgetManager.getFieldWiseBudget("XYZ",Aggregate.SUM);});
	}
	
	@Test
	@DisplayName("should return map given valid field and sum aggregate")
	void testGetFieldWiseBudget2() throws IllegalFieldNameException {
		
		Map<String,Double> expected = new HashMap<String,Double>(); 
		
		expected.put("CleanAndGreen",127000.0);
		expected.put("OrganicCultivation",65000.0);
		expected.put("PollutionControl",80500.0);
		expected.put("PaperLessDining",35000.0);
		
		Map<String,Double> actual = budgetManager.getFieldWiseBudget("title", Aggregate.SUM);
		
		expected.keySet().stream().forEach((title)->{
			assertTrue(actual.containsKey(title));
			assertEquals(actual.get(title),expected.get(title));
		});
	}
	
	@Test
	@DisplayName("should return map given valid field and min aggregate")
	void testGetFieldWiseBudget3() throws IllegalFieldNameException {
		
		Map<String,Double> expected = new HashMap<String,Double>(); 
		
		expected.put("CleanAndGreen",22000.0);
		expected.put("OrganicCultivation",29000.0);
		expected.put("PollutionControl",20000.0);
		expected.put("PaperLessDining",35000.0);
		
		Map<String,Double> actual = budgetManager.getFieldWiseBudget("title", Aggregate.MIN);
		
		expected.keySet().stream().forEach((title)->{
			assertTrue(actual.containsKey(title));
			assertEquals(actual.get(title),expected.get(title));
		});
	}
	
	@Test
	@DisplayName("should return map given valid field and max aggregate")
	void testGetFieldWiseBudget4() throws IllegalFieldNameException {
		
		Map<String,Double> expected = new HashMap<String,Double>(); 
		
		expected.put("CleanAndGreen",40000.0);
		expected.put("OrganicCultivation",36000.0);
		expected.put("PollutionControl",36000.0);
		expected.put("PaperLessDining",35000.0);
		
		Map<String,Double> actual = budgetManager.getFieldWiseBudget("title", Aggregate.MAX);
		
		expected.keySet().stream().forEach((title)->{
			assertTrue(actual.containsKey(title));
			assertEquals(actual.get(title),expected.get(title));
		});
	}
	
	@Test
	@DisplayName("GetCostliestActivity")
	void testGetCostliestActivity() throws IllegalFieldNameException {
		ActivityDetailsVO expected=ActivityDetailsRepo.getActivities().get(1);
		ActivityDetailsVO actual=budgetManager.getCostliestActivity();
		assertEquals(expected, actual);		
	}
	
	@Test
	@DisplayName("GetMonthWiseBudget")
	void testGetMonthWiseBudget() {
		Map<String,Double> expected = new HashMap<>();
		expected.put("Nov-2020",261000.0);
		expected.put("Dec-2020",22000.0);
		expected.put("Apr-2020",24500.0);
		
		Map<String,Double> actual = budgetManager.getMonthWiseBudget();
		expected.keySet().stream().forEach((m)->{
			assertTrue(actual.containsKey(m));
			assertEquals(actual.get(m),expected.get(m));
		});		
	}
}
