package com.myDemo.BookTransactions.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myDemo.BookTransactions.dto.TransactionDTO;
import com.myDemo.BookTransactions.service.TransactionService;

@RestController
@RequestMapping("/demo")
public class WelcomeController {

	@Autowired
	TransactionService tservice;
	
	public static final int creditLimit = 200;
		
	@RequestMapping(value = "/welcome", method = RequestMethod.POST, produces = "application/json")
	public String findProducByName(@RequestBody String inputData) throws IOException{
		String rejected=" ";
		if(!inputData.isEmpty()) {
		String inputTransaction[] = inputData.split("\n");
		System.out.println("inputTransaction.length="+inputTransaction.length);
		
			List<TransactionDTO> transactions = tservice.getDataFromCSVtoList(inputTransaction);		
			JSONArray rejectedList = tservice.findRejectedTransactions(transactions, creditLimit);
			rejected=rejectedList.toString();
			
		}
		return rejected;
	}
}
