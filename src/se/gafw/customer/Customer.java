package se.gafw.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import se.gafw.customer.account.Account;
import se.gafw.customer.account.SavingsAccount;
import se.gafw.customer.account.TransactionAccount;
import se.gafw.file.FileHandler;

public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Account> accounts;
	//maps are not serializable!!
	private transient HashMap<String, String> attributes;
	
	//when serializing and de-serializing use a string array instead of the map
	private String[] attributeArray;
	private final FileHandler handler;
	
	/**
	 * Only call constructor outside of this class if a new customer is created, otherwise use the method getCustomer(String).
	 * 
	 * @param customerNumber
	 * @param firstName
	 * @param lastName
	 * @param socialSecurity
	 * @param adress
	 * @param zip
	 * @param city
	 * @param phone
	 * @param mail
	 */
	public Customer(String customerNumber, String firstName, String lastName, String socialSecurity, String adress, String zip, String city, String phone, String mail)
	{
		//TODO error checking
		attributes = new HashMap<>();
		attributes.put("firstName", firstName);
		attributes.put("lastName", lastName);
		attributes.put("socialSecurity", socialSecurity);
		attributes.put("adress", adress);
		attributes.put("zip", zip);
		attributes.put("city", city);
		attributes.put("phone", phone);
		attributes.put("mail", mail);
		attributes.put("customerNumber", customerNumber);
		//Create a customer file on the server
		handler = new FileHandler("bank/customers/" + customerNumber + "/", customerNumber, ".ser");
		accounts = new ArrayList<>();
		//check if the customer file already exists, ONLY CALL CONSTRUCTOR FOR NEW CUSTOMERS!!
		//if(((Customer)handler.readObject()).equals(this))
		//	throw new RuntimeException("Error trying to registrer a customer that already exists!");
		
		updateFile();
	}
	
	/**
	 * used when loading a file from disk, where a customers attributes is stored in a string array
	 * instead of an map.
	 * @param data
	 */
	public void set(String[] data)
	{
		attributes = new HashMap<String, String>();
		for(int i = 0; i < data.length; i++)
		{
			String[] keysAndValues = data[i].split("&");
			attributes.put(keysAndValues[1], keysAndValues[0]);
		}
	}
	
	/**
	 * rewrite the file on disk.
	 */
	public void updateFile()
	{
		System.out.println("updating customer file, accounts: "+ getAccounts().length);
		handler.deleteFile();
		handler.set("bank/customers/" + getAttribute("customerNumber") + "/", getAttribute("customerNumber"), "ser");
	    	    
		List<String> values = new ArrayList<String>();
	    for(Map.Entry<String, String> entry : attributes.entrySet())
	    	values.add(entry.getValue() + "&" + entry.getKey());

	    attributeArray = new String[values.size()];
	    for(int i = 0; i < attributeArray.length; i++)
	    	attributeArray[i] = (String)values.get(i);
	    
		handler.writeObject(this);
	}
	
	private void updateAccounts()
	{
		// if the list already contains accounts then remove them, they will be added again
		//for(Account a : accounts)accounts.remove(a);
		//for(int i = 0; i < accounts.size();)accounts.remove(i);
		FileHandler tempHandler = new FileHandler("don't ","worry","about this");
		accounts.clear();
		
		for(int i = 1; true; i++) // should be while(true)
		{
			if(FileHandler.fileExists("bank/customers/" + getAttribute("customerNumber") + "/accounts/" + 
					(Long.parseLong(getAttribute("customerNumber")) + i) + ".ser"))
			{
				tempHandler.set("bank/customers/" + getAttribute("customerNumber") + "/accounts/", 
						(Long.parseLong(getAttribute("customerNumber")) + i) + "" ,"ser");
				Account a = (Account) tempHandler.readObject();
				if(a == null) continue; //if a is null, ignore it (it should NEVER be null ??!!?)
				accounts.add(a instanceof SavingsAccount ? (SavingsAccount) a : (TransactionAccount) a);
			}else{
				return;
			}
		}
	}
	
	public static Customer getCustomer(String customerNumber)
	{
		if(!FileHandler.fileExists("bank/customers/" + customerNumber + "/" + customerNumber + ".ser"))
		{
			// this does not need an exception.... what am I even doing??
			return null;
		}
		FileHandler handler = new FileHandler("bank/customers/" + customerNumber + "/", customerNumber, ".ser");
		Customer result = (Customer)handler.readObject();
		result.set(result.getAttributeArray());
		result.updateAccounts();
		return result;
	}
	
	public boolean addAccount(Account account)
	{
		//if(account instanceof Account)return false; //should do more error checking
		accounts.add(account);
		updateFile();
		//updateAccounts();
		//updateAccounts();
		return true;
	}

	public Account[] getAccounts() 
	{
		Account[] result = new Account[accounts.size()];
		for(int i = 0; i < result.length; i++)
			result[i] = accounts.get(i);
		return result;
	}
		
	public HashMap<String, String> getAttributes()
	{
		return attributes;
	}
	
	/**
	 * 
	 * @param attrib can be:
	 * 					firstName
	 * 					lastName
	 * 					socialSecurity
	 * 					adress
	 * 					zip
	 * 					city
	 * 					phone
	 * 					mail
	 * 					customerNumber
	 * @return
	 */
	public String getAttribute(String attrib)
	{
		if(!attributes.containsKey(attrib))return "attribute does not exist!";
		return attributes.get(attrib);
	}
	
	public String[] getAttributeArray()
	{
		return attributeArray;
	}
}
