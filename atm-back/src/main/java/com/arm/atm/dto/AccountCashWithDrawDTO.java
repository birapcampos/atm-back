package com.arm.atm.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Account Cash WithDraw ")
public class AccountCashWithDrawDTO {

	@ApiModelProperty(value = "Account Number")
	private Long number;
	
	@ApiModelProperty(value = "Balance")
	private BigDecimal balance;
	
	@ApiModelProperty(value = "Cash WithDraw Value")
	private BigDecimal cashWithDraw;
	
	@ApiModelProperty(value = "Notas")
	private String notas;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCashWithDraw() {
		return cashWithDraw;
	}

	public void setCashWithDraw(BigDecimal cashWithDraw) {
		this.cashWithDraw = cashWithDraw;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	@Override
	public String toString() {
		return "AccountCashWithDrawDTO [number=" + number + ", balance=" + balance + ", cashWithDraw=" + cashWithDraw
				+ ", notas=" + notas + "]";
	}
		
}