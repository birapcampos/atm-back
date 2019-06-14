package com.arm.atm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="Account")
@NamedQuery(
		name = "Account.accountValidation", 
		query = "SELECT acc FROM Account acc"
				+ " WHERE acc.bankname=:bank"
				+ " and acc.accountnumber=:number"
			)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Account implements Serializable {
	
	private static final long serialVersionUID = -2320707780494933936L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@ApiModelProperty(value = "Account Number", dataType = "long", required = true, allowEmptyValue = false)
	@NotNull(message="Account number is required.")
 	private Long accountnumber;
	
	@NotBlank(message="Owner is required.")
	@ApiModelProperty(value = "Account owner", dataType = "string", required = true, allowEmptyValue = false)
	private String owner;
	
	@ApiModelProperty(value = "Account Balance", dataType = "long", required = true, allowEmptyValue = false)
	@NotNull(message="Balance is required.")   
	@PositiveOrZero
	private BigDecimal balance;
	
	@NotBlank(message="Bank is required.")
	@ApiModelProperty(value = "Bank", dataType = "string", required = true, allowEmptyValue = false)
	private String bankname;
	
	@NotBlank(message="Password is required.")
	@ApiModelProperty(value = "Account Password", dataType = "string", required = true, allowEmptyValue = false)
	private String password;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
		
	public Long getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(Long accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
			
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	 	
}