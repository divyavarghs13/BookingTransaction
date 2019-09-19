package com.myDemo.BookTransactions.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myDemo.BookTransactions.model.Transactionmodel;

@Service
public class TransactionService {
	ArrayList<String> aList = new ArrayList<String>();
	
		public ArrayList<String> readAllLinesFromFile(String path) throws IOException {

			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				aList.add(line);
			}
			bufferedReader.close();

			return aList;

		}
		
	
		public List<Transactionmodel> getDataFromCSVtoList(List<String> transactionsStrings) {
			List<Transactionmodel> transactions = new ArrayList<>();

			/**
			 * concatenate fname,lname, email to one field as Key to make the list unique
			 **/
			for (String transactionString : transactionsStrings) {
				String[] column = transactionString.split(",");
				String fName = column[0];
				String lName = column[1];
				String email = column[2];
				String key = fName + lName + email;
				int amount = Integer.valueOf(column[3]);
				String trID = column[4];
				transactions.add(new Transactionmodel(key, amount, trID));
			}
			/**
			 * Sort each row of the file in ascending order considering key field, and if
			 * key is same this will sort according to transactionID field
			 **/
			Collections.sort(transactions, Transactionmodel.NameComparator);
			System.out.println(transactions);
			return transactions;
		}
		

		public List<String> findRejectedTransactions(List<Transactionmodel> transactions, int creditLimit) {

			List<String> rejectedList = new ArrayList<String>();
			List<String> keylist = new ArrayList<String>();
			List<Integer> amountlist = new ArrayList<Integer>();
			List<String> trIDlist = new ArrayList<String>();
			
			int lsize = transactions.size();
						
			for (Transactionmodel transaction : transactions) {
				keylist.add(transaction.getKey());
				amountlist.add(transaction.getAmount());
				trIDlist.add(transaction.getTrID());				
			}								

			if (creditLimit > 0) {
				
				if (lsize > 1) {
					for (int i = 0; i < (lsize); i++) {
						if (amountlist.get(i) > creditLimit) {		
							rejectedList.add(trIDlist.get(i).toString());	
						}
						else{
						for (int j = i+1; j <(lsize) ; j++) { 						
						 if (keylist.get(i).equals(keylist.get(j))) {
								if (amountlist.get(i) > creditLimit) {
									rejectedList.add(trIDlist.get(i).toString());
								}
								else if ((amountlist.get(i) + amountlist.get(j)) > creditLimit) {
									rejectedList.add(trIDlist.get(j).toString());
								}
							}
						}
					}
					}
				}
				if (lsize == 1 && amountlist.get(0) > creditLimit) {				
					rejectedList.add(trIDlist.get(0).toString());
				}

			}
			 System.out.println("Rejected Transactions: " + rejectedList);
			return rejectedList;
		}

}
