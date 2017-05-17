package se.gafw.customer.account;

import java.io.Serializable;
import java.util.Date;

import se.gafw.customer.Customer;
import se.gafw.file.FileHandler;

public abstract class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private final Date openingDate;
	private double funds;
	private final long accountNumber;
	private boolean active;
	private transient Customer owner;
	//private FTPHandler handler;
	private final FileHandler handler;
	
	protected Account(Customer owner, long accountNumber, boolean active/*, FTPHandler handler*/)
	{
		openingDate = new Date();
		this.accountNumber = accountNumber;
		this.active = active;
		//this.handler = handler;
		handler = new FileHandler("bank/customers/" + owner.getAttribute("customerNumber") + "/accounts/", accountNumber + "", ".ser");
		handler.writeObject(this);
		this.owner = owner;
		
		//make sure that there is an account file for every account
		//if(handler.readObject() != null)
		//throw new RuntimeException("Error trying to write account file that already exists for \n" + owner.getAttributes().toString());
			
		//handler.writeObject(this);
		owner.addAccount(this);
	}
	
	public boolean withdrawFunds(double ammount, String message)
	{
		if(!withdrawFunds(ammount, false))
		{
			System.err.println("ERROR WITHDRAWING FUNDS");
			return false;
		}
		logAction(-ammount, message);
		return true;
	}
	
	public boolean withdrawFunds(double ammount, boolean log)
	{
		if(ammount > funds || ammount <= 0) //TODO error om ammount < 0
		{
			return false; //TODO returnera ett error
		}
		
		funds -= ammount;
		if(log)logAction(-ammount, "");//don't know if i should have a message for withdrawing funds
		return true;
	}
	
	public void deposit(double ammount, String message)
	{
		funds += ammount;
		logAction(ammount, message);
	}
	
	protected void logAction(double change, String message)
	{
		FileHandler handler = new FileHandler("bank/customers/" + owner.getAttribute("customerNumber") + "/accounts/", accountNumber + "_LOG", ".txt");
		String newLog = handler.read() + "\n" + (new Date().toString()) + "\t\tcurrent: " + funds + "\t\tchange: " + change + "\n" + message + "\n";
		// Out with the old and in with the new
		if(!handler.deleteFile())System.err.println("error removing old account log!");
		handler.write(newLog);
		System.out.println("updating file: " + this.handler.getCurrentDirectory());
		this.handler.deleteFile();
		this.handler.writeObject(this);
	}
	
	public long getAccountNumber()
	{
		return accountNumber;
	}
	
	public Date getOpeningDate()
	{
		return openingDate;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public double getFunds()
	{
		return funds;
	}
	
	public String toString()
	{
		return (this instanceof SavingsAccount ? "Savings" : "Transaction") + "  [" + accountNumber + "]";
	}
}
