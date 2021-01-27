package in.tdh.sm.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;

/*
 * Manages the SubscriberDetails
 */
public class BillManagementService {

	/*
	 * This method computes the bills of each subscriber Params : none Returns:
	 * List<BillVO> where each BillVO represents the bill details of each subscriber
	 */
	public List<BillVO> getBills() {
		List<BillVO> billsList = null;

	
		return billsList;
	}
	
	/*
	 * This method Sorts the List of bills on BillTerm and ValidTillDate in Ascending order
	 * Param: unordered List<BillVO>  
	 * Returns: Ordered List<BillVO> on BillTerm and ValidTillDate in Ascending order
	 */
	public List<BillVO> sortBills(List<BillVO> unorderedBillsList) {
		List<BillVO> orderedBillsList = null;


		return orderedBillsList;
	}

	/*
	 * This method retrieve the List of bills whose validTillDate is on or before given date 
	 * WITHOUT USING "filter()" OPERATION. 
	 * Param: List<BillVO> having all bill records,a date as LocalDate object
	 * Returns: List<BillVO> having BillVO objects whose validTillDate is on or before given date.
	 */
	public List<BillVO> getBillsOnOrBefore(List<BillVO> billsList,LocalDate date) {
		List<BillVO> filteredBillsList = null;

	
		return filteredBillsList;
	}
}

class BillVO {
	private String mobileNumber;
	private LocalDate registrationDate;
	private LocalDate validTillDate;
	private double amountPayable;
	private String billingTerm;

	public BillVO() {

	}

	public BillVO(String mobileNumber, LocalDate registrationDate, LocalDate validTillDate, double amountPayable,String billingTerm) {
		super();
		this.mobileNumber = mobileNumber;
		this.registrationDate = registrationDate;
		this.validTillDate = validTillDate;
		this.amountPayable = amountPayable;
		this.billingTerm=billingTerm;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LocalDate getValidTillDate() {
		return validTillDate;
	}

	public void setValidTillDate(LocalDate validTillDate) {
		this.validTillDate = validTillDate;
	}

	public double getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(double amountPayable) {
		this.amountPayable = amountPayable;
	}

	public String getBillingTerm() {
		return billingTerm;
	}

	public void setBillingTerm(String billingTerm) {
		this.billingTerm = billingTerm;
	}

	@Override
	public String toString() {
		return "BillVO [mobileNumber=" + mobileNumber + ", registrationDate=" + registrationDate + ", validTillDate="
				+ validTillDate + ", amountPayable=" + amountPayable + ", billingTerm=" + billingTerm + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amountPayable);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((billingTerm == null) ? 0 : billingTerm.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + ((validTillDate == null) ? 0 : validTillDate.hashCode());
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
		BillVO other = (BillVO) obj;
		if (Double.doubleToLongBits(amountPayable) != Double.doubleToLongBits(other.amountPayable))
			return false;
		if (billingTerm == null) {
			if (other.billingTerm != null)
				return false;
		} else if (!billingTerm.equals(other.billingTerm))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (validTillDate == null) {
			if (other.validTillDate != null)
				return false;
		} else if (!validTillDate.equals(other.validTillDate))
			return false;
		return true;
	}
 
}

class SubscriberVO {
	private int subscriberId;
	private String firstName;
	private String lastName;
	private List<String> packages;
	private String mobileNumber;
	private LocalDate registrationDate;
	private String billingTerm;

	public SubscriberVO() {
		// TODO Auto-generated constructor stub
	}

	public SubscriberVO(int subscriberId, String firstName, String lastName, List<String> packages, String mobileNumber,
			LocalDate registrationDate, String billingTerm) {
		super();
		this.subscriberId = subscriberId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.packages = packages;
		this.mobileNumber = mobileNumber;
		this.registrationDate = registrationDate;
		this.billingTerm = billingTerm;
	}

	public int getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(int subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getPackages() {
		return packages;
	}

	public void setPackages(List<String> packages) {
		this.packages = packages;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getBillingTerm() {
		return billingTerm;
	}

	public void setBillingTerm(String billingTerm) {
		this.billingTerm = billingTerm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billingTerm == null) ? 0 : billingTerm.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((packages == null) ? 0 : packages.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
		result = prime * result + subscriberId;
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
		SubscriberVO other = (SubscriberVO) obj;
		if (billingTerm == null) {
			if (other.billingTerm != null)
				return false;
		} else if (!billingTerm.equals(other.billingTerm))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (packages == null) {
			if (other.packages != null)
				return false;
		} else if (!packages.equals(other.packages))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		if (subscriberId != other.subscriberId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscriber [subscriberId=" + subscriberId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", packages=" + packages + ", mobileNumber=" + mobileNumber + ", registrationDate=" + registrationDate
				+ ", billingTerm=" + billingTerm + "]";
	}
}

class PackageVO {
	private String packageName;
	private double monthlyFee;
	private double annualFee;

	public PackageVO() {

	}

	public PackageVO(String packageName, double monthlyFee, double annualFee) {
		super();
		this.packageName = packageName;
		this.monthlyFee = monthlyFee;
		this.annualFee = annualFee;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public double getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public double getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(double annualFee) {
		this.annualFee = annualFee;
	}

	@Override
	public String toString() {
		return "PackageVO [packageName=" + packageName + ", monthlyFee=" + monthlyFee + ", annualFee=" + annualFee
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(annualFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(monthlyFee);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
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
		PackageVO other = (PackageVO) obj;
		if (Double.doubleToLongBits(annualFee) != Double.doubleToLongBits(other.annualFee))
			return false;
		if (Double.doubleToLongBits(monthlyFee) != Double.doubleToLongBits(other.monthlyFee))
			return false;
		if (packageName == null) {
			if (other.packageName != null)
				return false;
		} else if (!packageName.equals(other.packageName))
			return false;
		return true;
	}
}

class DTHDataRepo {

	private static List<SubscriberVO> subscribers;
	private static List<PackageVO> packages;

	public static List<SubscriberVO> getSubscribers() {

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yy");

		if (subscribers == null) {
			subscribers = new ArrayList<SubscriberVO>(List.of(
					new SubscriberVO(101, "Srinivas", "Dachepalli", List.of("SouthPlus", "News"), "9247175830",
							LocalDate.parse("10-Jan-21", fmt), "Monthly"),
					new SubscriberVO(102, "Lakshmi", "Manchu", List.of("SouthPlus", "Kids", "Sports"), "9247212345",
							LocalDate.parse("10-Jan-21", fmt), "Annually"),
					new SubscriberVO(103, "Sudheer", "Yalamanchili", List.of("NorthPlus", "Sports", "Movies"),
							"9848012345", LocalDate.parse("11-Jan-21", fmt), "Annually"),
					new SubscriberVO(104, "Anasuya", "Ramalingam", List.of("NorthPlus", "Kids", "Movies"), "9848023456",
							LocalDate.parse("11-Jan-21", fmt), "Monthly"),
					new SubscriberVO(105, "Rashmi", "Goutham", List.of("SouthPlus", "Movies", "News"), "8093466666",
							LocalDate.parse("12-Jan-21", fmt), "Monthly"),
					new SubscriberVO(106, "Aditya", "Hyper", List.of("NorthPlus", "News"), "8093477777",
							LocalDate.parse("12-Jan-21", fmt), "Monthly"),
					new SubscriberVO(107, "Rakesh", "Rocking", List.of("SouthPlus", "Kids", "Sports"), "8093456565",
							LocalDate.parse("10-Jan-21", fmt), "Monthly"),
					new SubscriberVO(108, "Abhi", "Attili", List.of("SouthPlus", "Movies", "Kids"), "8093478789",
							LocalDate.parse("11-Jan-21", fmt), "Annually"),
					new SubscriberVO(109, "Ramesh", "Rachakonda", List.of("NorthPlus", "Movies"), "9247212541",
							LocalDate.parse("10-Jan-21", fmt), "Monthly"),
					new SubscriberVO(110, "Dora", "Babu", List.of("SouthPlus", "Sports", "Movies", "News"),
							"9247112350", LocalDate.parse("11-Jan-21", fmt), "Annually")));
		}
		return subscribers;
	}

	public static List<PackageVO> getPackages() {

		if (packages == null) {
			packages = new ArrayList<PackageVO>(
					List.of(new PackageVO("Kids", 102.0, 1122.0), new PackageVO("Sports", 102.0, 1122.0),
							new PackageVO("News", 202.0, 2222.0), new PackageVO("Movies", 305.0, 3355.0),
							new PackageVO("NorthPlus", 412.0, 4532.0), new PackageVO("SouthPlus", 412.0, 4532.0)));
		}

		return packages;
	}
}