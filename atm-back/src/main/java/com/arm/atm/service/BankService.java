package com.arm.atm.service;

import com.arm.atm.model.Bank;


public interface BankService {

	Bank saveBank(Bank bank);
		
	Bank findById(Long id);
	
}
