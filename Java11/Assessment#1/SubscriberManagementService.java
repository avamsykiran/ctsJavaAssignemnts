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

/*
 * Manages the SubscriberDetails
 */
public class SubscriberManagementService {

	/*
	 *This method load subscriber details form the file into a list.
	 *Skip first line as that has headings.
	 *Params: absolute path of the file contianing subscriber detials.
	 *Returns: List<SubscriberVO> having each record as one SubscriberVO object.  
	 */
	public List<SubscriberVO> loadAllSubscribers(String fileName) throws SubscriberParseException {
		List<SubscriberVO> subscribersList = new ArrayList<SubscriberVO>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-yy");
			
			subscribersList = reader.lines().skip(1).map(line -> {
				String [] parts = line.split(",");
				
				return new SubscriberVO(
						Integer.parseInt(parts[0]), parts[1], parts[2], 
						Arrays.asList(parts[3].split(";")), 
						parts[4], LocalDate.parse(parts[5], format), 
						parts[6]);
				
			}).filter(s -> isValidSubscriber(s)).collect(Collectors.toList());
			
		} catch(IOException excep) {
			throw new SubscriberParseException(excep); 
		}
		return subscribersList;
	}

	/*
	 *This method returns the sorted set of distinct package names
	 *Params: absolute path of the file containing subscriber details.
	 *Returns: SortedSet<String> of distinct packageNames  
	 */
	public SortedSet<String> getDistinctPackages(String fileName) throws SubscriberParseException {
		TreeSet<String> packagesSet=null;
		
		List<SubscriberVO> subscribersList = loadAllSubscribers(fileName);
				
		packagesSet=subscribersList.stream().flatMap(s -> s.getPackages().stream())
				.collect(Collectors.toCollection(()->new TreeSet<String>()));
		
		return packagesSet;
	}
	
	/*
	 *This method return the set of fullNames of subscribers package wise.
	 *Params: absolute path of the file contianing subscriber detials.
	 *Returns: Map<String,List<SubscriberVO>> having packageName as key and 
	 *value as set of subscriber fullNames.  
	 */
	public Map<String,List<String>> getPackageWiseSubscribers(String fileName) throws SubscriberParseException {
		Map<String,List<String>> packageWiseSubscriberNamesMap =null;
		
		List<SubscriberVO> subscribersList = loadAllSubscribers(fileName);
		
		SortedSet<String> packages = getDistinctPackages(fileName);
		
		packageWiseSubscriberNamesMap = packages.stream().collect(Collectors.toMap(
				Function.identity(),
				p -> (
						subscribersList.stream().filter(s->s.getPackages().contains(p))
						.map(s->s.getFirstName()+" "+s.getLastName())
						.collect(Collectors.toList())
					)				
				));
						
		return packageWiseSubscriberNamesMap;
	}

	private boolean isValidSubscriber(SubscriberVO subscriber) throws InvalidSubscriberException {
		boolean valid=false;
		
		List<String> errors = new ArrayList<String>();
		
		if(subscriber!=null) {
			if (subscriber.getSubscriberId() <= 0) {
				errors.add("Subscriber Id cannot be 0 or negative");
			}
			if (subscriber.getFirstName() == null || subscriber.getFirstName().isBlank() || subscriber.getFirstName().length() < 4 || subscriber.getFirstName().length() > 25) {
				errors.add("First Name should not be BLANK and should be of 4 to 25 characters in length");
			}
			if (subscriber.getLastName() == null || subscriber.getLastName().isBlank() || subscriber.getLastName().length() < 4 || subscriber.getLastName().length() > 25) {
				errors.add("Last Name should not be BLANK and should be of 4 to 25 characters in length");
			}
			if (subscriber.getRegistrationDate() == null || subscriber.getRegistrationDate().isAfter(LocalDate.now())) {
				errors.add("Registration Date should not be a Future Date");
			}
			if (subscriber.getMobileNumber() == null || !subscriber.getMobileNumber().matches("\\d{10}")) {
				errors.add("Mobile number cannot be null and must be a 10 digit number");
			}
			if (subscriber.getBillingTerm() == null || !subscriber.getBillingTerm().matches("Monthly|Annually" )) {
				errors.add("Billing term cannot be null and should be either Monthly or Annually");
			}
				
		}
		
		valid=errors.isEmpty();
		
		if(!valid)
			throw new InvalidSubscriberException(errors.toString());
		
		return valid;
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

class InvalidSubscriberException extends RuntimeException {
	public InvalidSubscriberException(String message) {
		super(message);
	}

	public InvalidSubscriberException(Throwable th) {
		super(th);
	}
}

class SubscriberParseException extends Exception {
	public SubscriberParseException(String message) {
		super(message);
	}

	public SubscriberParseException(Throwable th) {
		super(th);
	}
}
