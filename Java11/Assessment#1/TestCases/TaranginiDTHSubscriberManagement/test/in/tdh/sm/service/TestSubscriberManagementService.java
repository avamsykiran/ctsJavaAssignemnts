package in.tdh.sm.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestSubscriberManagementService {
	
	private SubscriberManagementService service = new SubscriberManagementService();
	
	private final String DATA_FILE = "./data/subscribers.txt";
	
	private static List<SubscriberVO> testData;
	private static DateTimeFormatter fmt; 
	
	@BeforeAll
	static void init() {
		
		fmt = DateTimeFormatter.ofPattern("dd-MMM-yy");
		
		testData = List.of(
					new SubscriberVO(101,"Srinivas","Dachepalli",List.of("SouthPlus","News"),"9247175830",LocalDate.parse("10-Jan-21",fmt),"Monthly"),
					new SubscriberVO(102,"Lakshmi","Manchu",List.of("SouthPlus","Kids","Sports"),"9247212345",LocalDate.parse("10-Jan-21",fmt),"Annually"),
					new SubscriberVO(103,"Sudheer","Yalamanchili",List.of("NorthPlus","Sports","Movies"),"9848012345",LocalDate.parse("11-Jan-21", fmt),"Annually"),					
					new SubscriberVO(104,"Anasuya","Ramalingam",List.of("NorthPlus","Kids","Movies"),"9848023456",LocalDate.parse("11-Jan-21", fmt),"Monthly"),
					new SubscriberVO(105,"Rashmi","Goutham",List.of("SouthPlus","Movies","News"),"8093466666",LocalDate.parse("12-Jan-21", fmt),"Monthly"),
					new SubscriberVO(106,"Aditya","Hyper",List.of("NorthPlus","News"),"8093477777",LocalDate.parse("12-Jan-21", fmt),"Monthly"),
					new SubscriberVO(107,"Rakesh","Rocking",List.of("SouthPlus","Kids","Sports"),"8093456565",LocalDate.parse("10-Jan-21", fmt),"Monthly"),
					new SubscriberVO(108,"Abhi","Attili",List.of("SouthPlus","Movies","Kids"),"8093478789",LocalDate.parse("11-Jan-21", fmt),"Annually"),
					new SubscriberVO(109,"Ramesh","Rachakonda",List.of("NorthPlus","Movies"),"9247212541",LocalDate.parse("10-Jan-21", fmt),"Monthly"),
					new SubscriberVO(110,"Dora","Babu",List.of("SouthPlus","Sports","Movies","News"),"9247112350",LocalDate.parse("11-Jan-21", fmt),"Annually")
				);
	}
	
	@Test
	void testLoadAllSubscribers() throws SubscriberParseException {
		List<SubscriberVO> expected = testData;
		List<SubscriberVO> actual = service.loadAllSubscribers(DATA_FILE);
		assertEquals(expected, actual); 
	}

	@Test
	void testDistinctPackages() throws SubscriberParseException {
		SortedSet<String> expected = new TreeSet(Set.of("SouthPlus","NorthPlus","Kids","Movies","News","Sports"));
		SortedSet<String> actual = service.getDistinctPackages(DATA_FILE);
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testGetPackageWiseSubscribers() throws SubscriberParseException {
		Map<String,List<String>> expected = Map.of(
				"Kids",List.of("Lakshmi Manchu","Anasuya Ramalingam","Rakesh Rocking","Abhi Attili"),
				"Movies",List.of("Sudheer Yalamanchili","Anasuya Ramalingam","Rashmi Goutham","Abhi Attili", "Ramesh Rachakonda", "Dora Babu"),
				"News",List.of("Srinivas Dachepalli","Rashmi Goutham", "Aditya Hyper", "Dora Babu"),
				"NorthPlus",List.of("Sudheer Yalamanchili","Anasuya Ramalingam", "Aditya Hyper", "Ramesh Rachakonda"),
				"SouthPlus",List.of("Srinivas Dachepalli","Lakshmi Manchu", "Rashmi Goutham", "Rakesh Rocking", "Abhi Attili", "Dora Babu"),
				"Sports",List.of("Lakshmi Manchu", "Sudheer Yalamanchili","Rakesh Rocking", "Dora Babu")
				);
		
		Map<String,List<String>> actual = service.getPackageWiseSubscribers(DATA_FILE);
		expected.keySet().stream().forEach(k->{
			assertTrue(actual.containsKey(k));
			assertIterableEquals(expected.get(k), actual.get(k),
					"For " + k + "expecting "+expected.get(k) + "but was " + actual.get(k) 
					);
		});
	}
}
