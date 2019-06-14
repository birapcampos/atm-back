package com.arm.atm.service;

import java.math.BigDecimal;
import java.util.List;

import com.arm.atm.dto.AccountCashWithDrawDTO;
import com.arm.atm.model.Account;

public interface AccountService {

	Account saveAccount(Account account);
	
	Account findByaccountnumber(Long number);
	
	Account findById(Long id);
	
	AccountCashWithDrawDTO cashWithDrawal(Long accuntNumber,BigDecimal cashValue);
	
	List<String> cashWithDrawalCheck(Long accuntNumber,BigDecimal cashValue);

	Account accountValidation(String bank, Long number);
		
}
