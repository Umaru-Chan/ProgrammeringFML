package se.gafw.file;

import java.io.Serializable;

public class PasswordHandler implements Serializable{
	private static final long serialVersionUID = 1L;

	private final FileHandler handler;
	private String[] data;
	
	public PasswordHandler() {
		if(!FileHandler.fileExists("bank/secretStuff/notPasswords.ser"))
		{
			handler = new FileHandler("bank/secretStuff/", "notPasswords", ".ser");
			data = new String[0];
			handler.writeObject(this);
		} else {
			handler = new FileHandler("bank/secretStuff/", "notPasswords", ".ser");
			data = ((PasswordHandler) handler.readObject()).getData();
			if(data == null)
			{
				data = new String[1];
				data[0] = "";
				handler.writeObject(this);
			}
		}
	}
	
	
	public boolean isUser(String ssc, String pw)
	{
		for(int i = 0; i < data.length; i++)
		{
			//if(i%2==0)System.out.println("-------------\nuser:\t"+data[i]);
			//else System.out.println("pass:\t"+data[i]); 
		}
		for(int i = 0; i < data.length; i+=2)
		{
			System.out.println(data[i].equals(ssc));
			if(data[i].equals(ssc) && data[i+1].equals(pw))return true;
		}
		return false;
	}
	
	public boolean addUser(String ssc, String pw)
	{
		//TODO more error checking
		if(isUser(ssc, pw))return false;
		
		String[] newData = new String[data.length + 2];
		for(int i = 0; i < data.length - 1; i+=2)
		{
			newData[i] = data[i];
			newData[i+1] = data[i+1];
		}
		newData[newData.length-2] = ssc;
		newData[newData.length-1] = pw;
		
		data = newData;
		handler.writeObject(this);
		return true;
	}
	
	private String[] getData()
	{
		return data;
	}
}
