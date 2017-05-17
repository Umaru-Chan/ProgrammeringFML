package se.gafw.customer.account;

import se.gafw.customer.Customer;

public class SavingsAccount extends Account {
	private static final long serialVersionUID = 1l;
	
	private static final double INTEREST = .005D;
	
	public SavingsAccount(Customer owner, int accountNumber, boolean active)
	{
		super(owner, accountNumber, active);
	}
	
	public void addInterest()
	{
		deposit(getFunds() * INTEREST, "Yearly interest of " + (INTEREST * 100) + "%"); 
	}
}
