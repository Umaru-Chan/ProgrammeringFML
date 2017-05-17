package se.gafw.customer.account;

import se.gafw.customer.Customer;

public class TransactionAccount extends Account{
	private static final long serialVersionUID = 1L;
	
	public TransactionAccount(Customer owner, boolean active) 
	{
		super(owner, Long.parseLong(owner.getAttribute("customerNumber")) + owner.getAccounts().length + 1, active);
	}

}
