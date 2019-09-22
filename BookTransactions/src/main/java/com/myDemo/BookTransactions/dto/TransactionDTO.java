package com.myDemo.BookTransactions.dto;
import java.util.Comparator;



public class TransactionDTO implements Comparable<TransactionDTO> {
	private int amount;
	private String key;
	private String firstName;
	private String lastName;
	private String email;
	private String trID;
	
	public TransactionDTO(String key, int amount, String trID, String firstName, String lastName, String email) {
	    super();
	    this.key = key;
	    this.amount = amount;
	    this.trID = trID;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.email = email;
	}

	public TransactionDTO(int amount, String trID) {
	    super();
	    this.amount = amount;
	    this.trID = trID;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTrID() {
		return trID;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTrID(String trID) {
		this.trID = trID;
	}
	@Override
	public int compareTo(TransactionDTO s) {
		 return this.getTrID().compareTo(s.getTrID());
	}

/** compare & sort the list by unique key field and if key is same sort according to the trID field **/
	public static Comparator<TransactionDTO> NameComparator = new Comparator<TransactionDTO>() {
	@Override
	public int compare(TransactionDTO s1, TransactionDTO s2) {
		int keyname = s1.getKey().compareTo(s2.getKey());
		return keyname == 0 ? s1.getTrID().compareTo(s2.getTrID()) : keyname;	
	}
	};

	@Override
	public String toString() {
		return "Transactionmodel [amount=" + amount + ", key=" + key + ", trID=" + trID + "]";
	}
	
}
