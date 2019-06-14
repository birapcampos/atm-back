package com.arm.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.arm.atm.model.CashWithDrawal;

@Transactional(readOnly = true)
public interface CashWithDrawalRepository extends JpaRepository<CashWithDrawal, Long>  {
	CashWithDrawal save(CashWithDrawal cashWithDrawal);
}
