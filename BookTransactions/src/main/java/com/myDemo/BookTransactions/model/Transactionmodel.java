package com.myDemo.BookTransactions.model;
import java.util.Comparator;



public class Transactionmodel implements Comparable<Transactionmodel> {
	public int amount;
	public String key;
	public String trID;
	
	public Transactionmodel(String key, int amount, String trID) {
	    super();
	    this.key = key;
	    this.amount = amount;
	    this.trID = trID;
	}

	public Transactionmodel(int amount, String trID) {
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

	public void setTrID(String trID) {
		this.trID = trID;
	}
	@Override
	public int compareTo(Transactionmodel s) {
		 return this.getTrID().compareTo(s.getTrID());
	}

/** compare & sort the list by unique key field and if key is same sort according to the trID field **/
	public static Comparator<Transactionmodel> NameComparator = new Comparator<Transactionmodel>() {
	@Override
	public int compare(Transactionmodel s1, Transactionmodel s2) {
		int keyname = s1.getKey().compareTo(s2.getKey());
		return keyname == 0 ? s1.getTrID().compareTo(s2.getTrID()) : keyname;	
	}
	};

	@Override
	public String toString() {
		return "Transactionmodel [amount=" + amount + ", key=" + key + ", trID=" + trID + "]";
	}
	
}
