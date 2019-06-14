package com.arm.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.arm.atm.model.Account;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long>{
	
	Account save(Account newAccount);
		
	Account findByid(Long id);
	
	Account accountValidation(@Param("bank") String bank,@Param("number") Long number);

	Account findByaccountnumber(Long number);
}
