package se.gafw;

import java.util.Random;

import se.gafw.customer.Customer;
import se.gafw.customer.account.SavingsAccount;
import se.gafw.customer.account.TransactionAccount;

public class Test {
	
	public static void main(String ...args) /*lol*/ throws Exception
	{
		//TODO error checking
		
		String ssc = "199808065991";
		
		// ssc -> customerNumber
		StringBuilder customerN = new StringBuilder();
		for(char c : ssc.toCharArray())
			customerN.append("" + (Integer.parseInt(c + "") + 4) % 10);
		
		System.out.println(customerN);
		String customerNumber = customerN.toString();
		
		Customer tempCustomer = Customer.getCustomer(customerNumber);//new Customer(generateAccountNumber(), "Alex", "efternamn", "???9238046565", "uWot-gatan 3", "19284.zip", "inte stockholm", "070112911", "temp@temp.se");
		SavingsAccount ta = new SavingsAccount(tempCustomer, true);
		ta.deposit(100000, "LOLSLUMP EN MILJON KRONOR WOOOO!!!");
		ta.withdrawFunds(400.23, "lamo");
		//ta.withdrawFunds(150000, "RIP, banken är rånad och vi snor lite pengar...");
		System.out.println(ta.getFunds());
		tempCustomer.updateFile();
		System.out.println(tempCustomer.getAccounts().length);
		
		//199808065992
		//EhQz7yOywEz)h!*
		/*Customer lmaoo = Customer.getCustomer(lmao.getAttribute("customerNumber"));
		
		System.out.println("DATA FOR LMAO\t\t\t" + lmao.getAttributes());
		System.out.println("DATA FOR LMAOO\t\t\t" + lmaoo.getAttributes());
		lmao = Customer.getCustomer(lmaoo.getAttribute("customerNumber"));
		System.out.println("DATA FOR LMAO AGAIN\t\t" + lmao.getAttributes());
		System.out.println(lmao.getAccounts().length);
		*/
	}
	
	/*private static String generateAccountNumber()
	{
		int length = 6;
		int[] result = new int[length];
		Random random = new Random();
		//se till att de 2 första inte är 9, detta kan leda till
		//att numret blir längre än vad det ska vara när man lägger till konton
		result[0] = random.nextInt(8);
		result[1] = random.nextInt(8);
		
		for(int i = 2; i < result.length; i++)
			result[i] = random.nextInt(9);
		
		StringBuilder builder = new StringBuilder();
		for(int i : result)builder.append(i + "");
		
		return builder.toString();
	}*/

}
