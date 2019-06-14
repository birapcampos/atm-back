package com.arm.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arm.atm.model.Bank;
import com.arm.atm.repository.BankRepository;
import com.arm.atm.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public Bank saveBank(Bank bank) {
		return bankRepository.save(bank);
	}

	@Override
	public Bank findById(Long id) {
		return bankRepository.findByid(id);
	}

}
