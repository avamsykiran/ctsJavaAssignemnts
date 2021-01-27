package in.tdh.sm.service;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestBillManagementService {
	private BillManagementService service = new BillManagementService();
	private static List<BillVO> testData;
	
	@BeforeAll
	static void init() {
		testData = List.of(
				new BillVO("9247175830", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 614.0,"Monthly"),
				new BillVO("9247212345", LocalDate.parse("2021-01-10"), LocalDate.parse("2022-01-10"), 6776.0,"Annually"),
				new BillVO("9848012345", LocalDate.parse("2021-01-11"), LocalDate.parse("2022-01-11"), 9009.0,"Annually"),
				new BillVO("9848023456", LocalDate.parse("2021-01-11"), LocalDate.parse("2021-02-11"), 819.0,"Monthly"),
				new BillVO("8093466666", LocalDate.parse("2021-01-12"), LocalDate.parse("2021-02-12"), 919.0,"Monthly"),
				new BillVO("8093477777", LocalDate.parse("2021-01-12"), LocalDate.parse("2021-02-12"), 614.0,"Monthly"),
				new BillVO("8093456565", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 616.0,"Monthly"),
				new BillVO("8093478789", LocalDate.parse("2021-01-11"), LocalDate.parse("2022-01-11"), 9009.0,"Annually"),
				new BillVO("9247212541", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 717.0,"Monthly"),
				new BillVO("9247112350", LocalDate.parse("2021-01-11"),LocalDate.parse("2022-01-11"), 11231.0,"Annually")
			);
		
	}
	
	@Test
	void testGetBills() {		
		List<BillVO> expected = testData;
		List<BillVO> actual = service.getBills();
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testSortBills() {		
		List<BillVO> expected =List.of(
				new BillVO("9247212345", LocalDate.parse("2021-01-10"), LocalDate.parse("2022-01-10"), 6776.0,"Annually"),
				new BillVO("9848012345", LocalDate.parse("2021-01-11"), LocalDate.parse("2022-01-11"), 9009.0,"Annually"),
				new BillVO("8093478789", LocalDate.parse("2021-01-11"), LocalDate.parse("2022-01-11"), 9009.0,"Annually"),
				new BillVO("9247112350", LocalDate.parse("2021-01-11"),LocalDate.parse("2022-01-11"), 11231.0,"Annually"),				
				new BillVO("9247175830", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 614.0,"Monthly"),
				new BillVO("8093456565", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 616.0,"Monthly"),				
				new BillVO("9247212541", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 717.0,"Monthly"),
				new BillVO("9848023456", LocalDate.parse("2021-01-11"), LocalDate.parse("2021-02-11"), 819.0,"Monthly"),
				new BillVO("8093466666", LocalDate.parse("2021-01-12"), LocalDate.parse("2021-02-12"), 919.0,"Monthly"),
				new BillVO("8093477777", LocalDate.parse("2021-01-12"), LocalDate.parse("2021-02-12"), 614.0,"Monthly")				
			);
		List<BillVO> actual = service.sortBills(testData);
		assertIterableEquals(expected, actual);
	}
	
	@Test
	void testGetBillsOnOrBefore() {
		List<BillVO> expected =List.of(
				new BillVO("9247175830", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 614.0,"Monthly"),
				new BillVO("8093456565", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 616.0,"Monthly"),				
				new BillVO("9247212541", LocalDate.parse("2021-01-10"), LocalDate.parse("2021-02-10"), 717.0,"Monthly")								
			);
		List<BillVO> actual = service.getBillsOnOrBefore(testData, LocalDate.parse("2021-02-10"));
		assertIterableEquals(expected, actual);
	}
}
