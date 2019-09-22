package com.myDemo.BookTransactions.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.myDemo.BookTransactions.dto.TransactionDTO;


@Service
public class TransactionService {
	ArrayList<String> aList = new ArrayList<String>();	
	
		public List<TransactionDTO> getDataFromCSVtoList(String inputTransaction[]) {
			List<TransactionDTO> transactions = new ArrayList<>();

			/**
			 * concatenate fname,lname, email to one field as Key to make the list unique
			 **/
			for (String transactionString : inputTransaction) {
				String[] column = transactionString.split(",");
				String fName = column[0];
				String lName = column[1];
				String email = column[2];
				String key = fName + lName + email;
				int amount = Integer.valueOf(column[3]);
				String trID = column[4];
				transactions.add(new TransactionDTO(key, amount, trID,fName,lName ,email));
			}
			/**
			 * Sort each row of the file in ascending order considering key field, and if
			 * key is same this will sort according to transactionID field
			 **/
			Collections.sort(transactions, TransactionDTO.NameComparator);
			System.out.println(transactions);
			return transactions;
		}
		

		public JSONArray findRejectedTransactions(List<TransactionDTO> transactions, int creditLimit) {

			Map<String,Integer> successList = new HashMap<>();
			Map<String,TransactionDTO> rejectList = new HashMap<>();
			String user = null;
			for(TransactionDTO transaction : transactions){
				int transValue = 0;
				user = transaction.getFirstName()+" "+transaction.getLastName();
				if(successList.containsKey(user))
					transValue = successList.get(user) + transaction.getAmount();
				else
					transValue = transaction.getAmount();				
				if(transValue>creditLimit)
					rejectList.put(user, transaction);
				else
					successList.put(user, transValue);
			}
			
			JSONArray rejectedArray =  new JSONArray();
			JSONObject rejectedUser = null;
			try {
			for (Map.Entry<String,TransactionDTO> entry : rejectList.entrySet()){
				rejectedUser = new JSONObject();
				rejectedUser.put("First Name", entry.getValue().getFirstName());
				rejectedUser.put("Last Name", entry.getValue().getLastName());
				rejectedUser.put("Email ID", entry.getValue().getEmail());
				rejectedUser.put("Transaction Number", entry.getValue().getTrID());
				rejectedArray.put(rejectedUser);
			}
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return rejectedArray;
		}

}
