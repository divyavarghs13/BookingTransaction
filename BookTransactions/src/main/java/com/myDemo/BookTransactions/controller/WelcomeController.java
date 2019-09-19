package com.myDemo.BookTransactions.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myDemo.BookTransactions.service.TransactionService;

import com.myDemo.BookTransactions.model.Transactionmodel;

@RestController
@RequestMapping("/demo")
public class WelcomeController {

	@Autowired
	TransactionService tservice;
	
	public static final String CSV_PATH = "C:\\Users\\Divya\\Documents\\Divya\\hh.csv";
	public static final int creditLimit = 200;
	
	ArrayList<String> csvData = new ArrayList<String>();	
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<String> findProducByName() throws IOException{
		
		csvData = tservice.readAllLinesFromFile(CSV_PATH);		
		List<Transactionmodel> transactions = tservice.getDataFromCSVtoList(csvData);
		List<String> rejectedList = tservice.findRejectedTransactions(transactions, creditLimit);		
		return rejectedList;
		
	}
}
