package com.myDemo.BookTransactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.core.IsNull;
import org.hamcrest.text.IsEmptyString;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myDemo.BookTransactions.dto.TransactionDTO;
import com.myDemo.BookTransactions.service.TransactionService;
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookTransactionsApplicationTests {

	@Autowired
	TransactionService ts;

   @Test
    public void shouldReturnEmptyListIfThereIsATransactionWithinCreditLimit() {
        List<String> input = Arrays.asList("John,Doe,john@doe.com,200,TR0001");
        String[] stringArray = input.toArray(new String[input.size()]);
        List<TransactionDTO> transactions = ts.getDataFromCSVtoList(stringArray);
        String rejectedTransactions = ts.findRejectedTransactions(transactions, 200).toString();
        assertThat(rejectedTransactions.length(), is(0));
    }
   
  
    @Test
      public void shouldReturnTransationThatIsOverCreditLimit() {
          List<String> input = Arrays.asList("John,Doe,john@doe.com,201,TR0001");
          String[] stringArray = input.toArray(new String[input.size()]);
          List<TransactionDTO> transactions = ts.getDataFromCSVtoList(stringArray);         
          String rejectedTransactions = ts.findRejectedTransactions(transactions, 200).toString();
          assertTrue(rejectedTransactions.contains("TR0001"));
      }

    @Test
    public void shouldReturnTransationThatIsOverCreditLimit2List() {
    	 List<String> input = Arrays.asList("John,Doe,john@doe.com,20,TR0001","John,Doe,john@doe.com,209,TR0002");
        String[] stringArray = input.toArray(new String[input.size()]);
        List<TransactionDTO> transactions = ts.getDataFromCSVtoList(stringArray);         
        String rejectedTransactions = ts.findRejectedTransactions(transactions, 200).toString();
        assertFalse(rejectedTransactions.contains("TR0001"));
    }

    @Test
    public void shouldReturnTransationThatHasVariousCreditLimit() {
    	 List<String> input = Arrays.asList("John,Doe,john@doe.com,190,TR0001",
    			 "John,Doe1,john@doe1.com,199,TR0002","John,Doe2,john@doe2.com,209,TR0003",
    			 "John,Doe,john@doe.com,9,TR0004","John,Doe,john@doe.com,2,TR0005");
        String[] stringArray = input.toArray(new String[input.size()]);
        List<TransactionDTO> transactions = ts.getDataFromCSVtoList(stringArray);         
        String rejectedTransactions = ts.findRejectedTransactions(transactions, 200).toString();        
        assertTrue(rejectedTransactions.contains("TR0003") && rejectedTransactions.contains("TR0005"));
    }
    
    @Test
    public void shouldReturnEmptyListIfThereIsNoTransactions() {
        assertThat(ts.findRejectedTransactions(new ArrayList<>(), 0).length(), is(0));
    }
}

