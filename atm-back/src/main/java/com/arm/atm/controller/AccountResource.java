package com.arm.atm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arm.atm.dto.AccountCashWithDrawDTO;
import com.arm.atm.model.Account;
import com.arm.atm.model.Bank;
import com.arm.atm.response.Response;
import com.arm.atm.service.AccountService;
import com.arm.atm.service.exception.AccountExistException;
import com.arm.atm.service.exception.BalanceInvalidException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/account")
@Api("Account operations")
@CrossOrigin(maxAge=10, origins = {"http://localhost:4200"})
public class AccountResource {

	private static final Logger log = LoggerFactory.getLogger(AccountResource.class);
	
	@Autowired
	private AccountService accountService;
				
	@PostMapping(value= "/createnewaccount", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create a new Account")
	public ResponseEntity<Response<Account>> createAccount(@Valid @RequestBody Account account, 
			BindingResult result) {
				
		Response<Account> response = new Response<Account>();
		
		if (result.hasErrors()) {
			log.error("Account validation error.", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		log.info("Account Persistence");
		List<Account> accountDbList = new ArrayList<Account>();
		accountDbList.add(accountService.saveAccount(account));
				
		response.setData(accountDbList);
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/findAccount/{bank}/{number}")
	@ApiOperation(value = "Account Validation")
	public ResponseEntity<Response<Account>> findAccount(@PathVariable("bank") String bank,
												@PathVariable("number") Long number){
		
		Response<Account> response = new Response<Account>();
		Account account=accountService.accountValidation(bank, number);
				
		if(account==null) {
			response.getErrors().add("Account not found!");
			return ResponseEntity.badRequest().body(response);
		}
		List<Account> accountDbList = new ArrayList<Account>();
		accountDbList.add(account);
		response.setData(accountDbList);
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping(value = "/cashWithDrawal/{accountNumber}/{cashValue}")
	@ApiOperation(value = "Cash Withdrawal")
	private ResponseEntity<Response<AccountCashWithDrawDTO>> cashWithDrawal(
			@PathVariable("accountNumber") Long accountNumber, 
			@PathVariable("cashValue") BigDecimal cashValue){
		
		Response<AccountCashWithDrawDTO> response = new Response<AccountCashWithDrawDTO>();
		List<String> retornoWithDrawal = accountService.cashWithDrawalCheck(accountNumber, cashValue);
		boolean errorfound=false;
			
		if(!retornoWithDrawal.isEmpty()) {
			errorfound=true;
			for (String item : retornoWithDrawal) {
				response.getErrors().add(item.toString());
			}
		}
		
		if(errorfound) {
			return ResponseEntity.badRequest().body(response);
		}
		
		AccountCashWithDrawDTO accountCashWithDrawDTO = accountService.cashWithDrawal(accountNumber, cashValue);
		List<AccountCashWithDrawDTO> accountCashWithDrawDTOList = new ArrayList<AccountCashWithDrawDTO>();
		accountCashWithDrawDTOList.add(accountCashWithDrawDTO);
		response.setData(accountCashWithDrawDTOList);
		
		return ResponseEntity.ok(response);
		
	}

	private ResponseEntity<Response<Object>> validateBank(Bank bank) {
		String mensagem = "Bank does not exist.";
		Response<Object> response = new Response<Object>();
		response.getErrors().add(mensagem);
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler({BalanceInvalidException.class})
	public ResponseEntity<Response<Object>> handleBalanceInvalidException(BalanceInvalidException ex){
		String mensagem = "Balance must be greater than zero";
		Response<Object> response = new Response<Object>();
		response.getErrors().add(mensagem);
		return ResponseEntity.badRequest().body(response);
	}	
	
	@ExceptionHandler({AccountExistException.class})
	public ResponseEntity<Response<Object>> handleAccountExistException(AccountExistException ex){
		String mensagem = "Account number exist, please enter to another account number.";
		Response<Object> response = new Response<Object>();
		response.getErrors().add(mensagem);
		return ResponseEntity.badRequest().body(response);
	}	
}
