package com.arm.atm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arm.atm.dto.AccountCashWithDrawDTO;
import com.arm.atm.model.Account;
import com.arm.atm.model.CashWithDrawal;
import com.arm.atm.repository.AccountRepository;
import com.arm.atm.repository.CashWithDrawalRepository;
import com.arm.atm.service.AccountService;
import com.arm.atm.service.exception.AccountExistException;
import com.arm.atm.service.exception.BalanceInvalidException;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CashWithDrawalRepository cashWithDrawalRepository;
	
	@Override
	public Account saveAccount(Account account) {
		
		if(account.getBalance().doubleValue()<0) {
			throw new BalanceInvalidException();
		}
		
		Account accountdb = findByaccountnumber(account.getAccountnumber());
		if(null!=accountdb) {
			throw new AccountExistException();
		}
		
		return accountRepository.saveAndFlush(account);
	}

	@Override
	public Account findByaccountnumber(Long number) {
		return accountRepository.findByaccountnumber(number);
	}

	@Override
	public Account findById(Long id) {
		return accountRepository.findByid(id);
	}

	@Override
	public Account accountValidation(String bank, Long number) {
		return accountRepository.accountValidation(bank, number);
	}

	@Override
	public AccountCashWithDrawDTO cashWithDrawal(Long accuntNumber, BigDecimal cashValue) {
						
		AccountCashWithDrawDTO accountCashWithDrawDTO = new AccountCashWithDrawDTO();
		List<String> retorno = new ArrayList<String>();
		
		if(null!=accuntNumber && null!=cashValue) {
						
			// Localiza conta
			Account accountdb = findByaccountnumber(accuntNumber);
			
			if(null!=accountdb) {
				double result =	cashValue.doubleValue();
				if((retorno=verificavalor(result,accountdb.getBalance().doubleValue())).isEmpty()) {
					
					//Registra saque
					CashWithDrawal cashWithDrawal = new CashWithDrawal();
					cashWithDrawal.setAccount(accountdb.getAccountnumber());
					cashWithDrawal.setCashValue(cashValue);
					cashWithDrawal.setDateOperation(new Date());
					cashWithDrawalRepository.saveAndFlush(cashWithDrawal);
					
					// Atualiza saldo
					BigDecimal saldo=BigDecimal.valueOf(accountdb.getBalance().doubleValue()-cashValue.doubleValue());
					accountdb.setBalance(saldo);
					accountRepository.saveAndFlush(accountdb);
					
					// Retorna Notas
					String retornoNotas=ContagemNotas(cashValue.doubleValue());
					
					// Seta retorno
					accountCashWithDrawDTO.setNumber(accountdb.getAccountnumber());
					accountCashWithDrawDTO.setCashWithDraw(cashValue);
					accountCashWithDrawDTO.setBalance(saldo);
					accountCashWithDrawDTO.setNotas(retornoNotas);
				
				}
			}
			
		}
		
		return accountCashWithDrawDTO;
	}

	private List<String> verificavalor(double cashValue,double balance) {
		
		List<String> resultados = new ArrayList<String>();
		String resultadoNotas="";
		
		if(cashValue<=0) {
			resultados.add("O Valor do saque deve ser maior do que zero.");
		}
		else if(balance<cashValue) {
			resultados.add("Valor do saque superior ao saldo disponível.");
		}
		else if(cashValue<10) {
			resultados.add("O Valor deve ser de no mínimo 10.");
		}
		else if((cashValue%2)!=0) {
			resultados.add("O Valor deve ser múltimo de 100,50,20 ou 10.");
		}
		
		return resultados;
				
	}
	
	private String ContagemNotas(double valorSaque) {
		
		int[][] notasValidas = new int[4][2];  
		notasValidas[0][0]=100;
		notasValidas[1][0]=50;
		notasValidas[2][0]=20;
		notasValidas[3][0]=10;
				
		List<Integer> mapResult = new ArrayList<Integer>();
		
		int qtd=01;
		int cont=0;
		int indice=0;
		String resultadoNotas="";
		int valorRetorno=0;
		boolean sair=false;
	
		while(!sair){
			valorRetorno+=notasValidas[cont][indice];
					
			if(valorRetorno>valorSaque){
				valorRetorno-=notasValidas[cont][indice];
				cont++;
				qtd=(qtd--)<1 ? 1 : qtd--;
			}
			else if(valorRetorno<valorSaque){
				mapResult.add(notasValidas[cont][indice]);
			}
						
			if(valorRetorno==valorSaque){
				mapResult.add(notasValidas[cont][indice]);
				sair=true;	
			}
			qtd++;
						
		}
		
		cont=0;
		for(int i=0; i < notasValidas.length; i++) {
			for (Integer item : mapResult) {
				String s = item.toString();
				if(s.equals(String.valueOf(notasValidas[i][0]))){
					cont++;
				}		
			}	
			if(cont>0)
				resultadoNotas+=!resultadoNotas.isEmpty() ? " - " + String.valueOf(cont) + " de " + String.valueOf(notasValidas[i][0])  : "" + String.valueOf(cont) + " de " + String.valueOf(notasValidas[i][0]);
							
			cont=0;
		}	
		
		return resultadoNotas;
			
	}

	@Override
	public List<String> cashWithDrawalCheck(Long accuntNumber, BigDecimal cashValue) {
		List<String> retorno = new ArrayList<String>();
		if(null!=accuntNumber && null!=cashValue) {
			// Localiza conta
			Account accountdb = findByaccountnumber(accuntNumber);
			if(null!=accountdb) {
				double result =	cashValue.doubleValue();
				retorno=verificavalor(result,accountdb.getBalance().doubleValue());
			}	
			else {
				retorno.add("Account not found.");
			}
		}
		else {
			retorno.add("Account number and Cash value must be informed.");
		}
		return retorno;
	 }

	
}
