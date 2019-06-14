package com.arm.atm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.arm.atm.model.Bank;

@Transactional(readOnly = true)
public interface BankRepository extends JpaRepository<Bank, Long> {

	Bank findByName(String name);
		
	Bank save(Bank bank);
	
	List<Bank> findAll();
	
	Bank findByid(Long id);

}
