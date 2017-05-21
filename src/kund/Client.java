package kund;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import fileHandler.ObjectFileHandler;
import konto.Sparkonto;
import konto.Transaktionskonto;

public class Client implements Serializable {
	private static final long serialVersionUID = 3460909333890415541L;
	/**
	 * 
	 */
	private String personNr, firstName, lastName, adress, zip, city, phoneNr, email;
	private int kundNr;
	private final long password;
	private String username;
	
	private ArrayList<Sparkonto> sk = new ArrayList<>();
	private ArrayList<Transaktionskonto> tk = new ArrayList<>();
	
	private final int clientNumber;
	
	public Client(String firstName, String lastName, String personNr, String adress, String zip, String city, String phoneNr, String email, final int clientNumber){
		this.firstName = firstName;
		this.lastName = lastName;
		this.personNr = personNr;
		this.adress = adress;
		this.zip = zip;
		this.city = city;
		this.phoneNr = phoneNr;
		this.email = email;	
		
		this.password = clientNumber;
		this.clientNumber = clientNumber;
		setUsername(firstName);
	}
	
	public long getTransaktionsNummer(int index){
		return tk.get(index).getKontoNummer();
	}
	
	public long getSparkontoNummer(int index){
		return sk.get(index).getKontoNummer();
	}
	
	public Transaktionskonto getTransaktionskonto(int kontoNummer){
		kontoNummer -= 1;
		return kontoNummer >= tk.size() ? null : tk.get(kontoNummer);
	}
	
	public Sparkonto getSparKonto(int kontoNummer){
		kontoNummer -= 1;
		return kontoNummer >= sk.size() ? null : sk.get(kontoNummer);
	}
	
	public void setKundNr(int kundNr){
		this.kundNr = kundNr;
	}
	
	public int getKundNr(){
		return kundNr;
	}
		
	public void addAccount(Sparkonto spar)
	{
		sk.add(spar);
		ObjectFileHandler obh = new ObjectFileHandler("kunder/k" + clientNumber + "/Sparkonto");
		try{
			obh.write(spar, "kunder/k" + clientNumber + "/Sparkonto/" + sk.size() + 1 + ".ser");
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void addAccount(Transaktionskonto tran)
	{
		tk.add(tran);
		ObjectFileHandler obh = new ObjectFileHandler("kunder/k" + clientNumber + "/Sparkonto");
		try{
			obh.write(tran, "kunder/k" + clientNumber + "/Transaktionskonto/" + tk.size() + 1 + ".ser");
		}catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 * returns the client with the given clientnumber (if there is no such customer return null)
	 * @param clientNumber
	 * @return
	 */
	public static Client getClient(String clientNumber)
	{
		Client result = null;
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("clients.ser"));
			Object o = stream.readObject();
			@SuppressWarnings("unchecked")
			ArrayList<Client> clients = (ArrayList<Client>)o;			
			stream.close();
			for(Client c : clients)
			{
				if(c == null)continue;
				if(c.getClientNumber() == Integer.parseInt(clientNumber))
				{
					result = c;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result == null)return result;
		return getClient(result.getUsername(), result.getPassword());
	}
	
	/**
	 * returns the customer with the given username and password (if there is no such customer return null)
	 * @param username
	 * @param password
	 * @return
	 */
	public static Client getClient(String username, String password){
		Client result = null;
		try {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream("clients.ser"));
			Object o = stream.readObject();
			@SuppressWarnings("unchecked")
			ArrayList<Client> clients = (ArrayList<Client>)o;			
			stream.close();
			for(Client c : clients)
			{
				System.out.println("loop");
				if(c == null)continue;
				System.out.println(c.getUsername());
				System.out.println(c.getPassword());
				if(c.getUsername().equals(username) && c.getPassword().equals(password))
				{
					result = c;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result == null)return result;
		
		try{
			//get all sparkonton
			ObjectFileHandler handler = new ObjectFileHandler("kunder/k" + result.getClientNumber() + "/Sparkonto");
			String[] files = new String[(int) Files.list(Paths.get("kunder/k" + result.getClientNumber() + "/Sparkonto")).count()];
			File file = new File("kunder/k" + result.getClientNumber() + "/Sparkonto");
			for(int i = 0; i < file.listFiles().length; i++)
			{
				files[i] = file.listFiles()[i].getName();
			}
			ArrayList<Sparkonto> sparkonton = new ArrayList<Sparkonto>();
 			for(String s : files)
			{
 				System.out.println(s);
				Sparkonto spar = (Sparkonto) handler.read("kunder/k" + result.getClientNumber() + "/Sparkonto/" + s);
				sparkonton.add(spar);
			}
 			result.setSparkonto(sparkonton);
 			
 			//get all transaktionskonton
 			handler = new ObjectFileHandler("kunder/k" + result.getClientNumber() + "/Transaktionskonto");
			files = new String[(int) Files.list(Paths.get("kunder/k" + result.getClientNumber() + "/Transaktionskonto")).count()];
			file = new File("kunder/k" + result.getClientNumber() + "/Transaktionskonto");
			for(int i = 0; i < file.listFiles().length; i++)
			{
				files[i] = file.listFiles()[i].getName();
			}
			ArrayList<Transaktionskonto> transaktionskonton = new ArrayList<Transaktionskonto>();
 			for(String s : files)
			{
				Transaktionskonto tran = (Transaktionskonto) handler.read("kunder/k" + result.getClientNumber() + "/Transaktionskonto/" + s);
				transaktionskonton.add(tran);
			}
 			result.setTransaktionskonto(transaktionskonton);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	private void setTransaktionskonto(ArrayList<Transaktionskonto> tk){
		this.tk = tk;
	}
	
	private void setSparkonto(ArrayList<Sparkonto> sk){
		this.sk = sk;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password +"";
	}

	public String getPersonNr() {
		return personNr;
	}

	public void setPersonNr(String personNr) {
		this.personNr = personNr;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getCity(){
		return city;
	}
	
	public void setCity(String city){
		this.city = city;
	}

	public String getPhoneNr() {
		return phoneNr;
	}

	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getClientNumber(){
		return clientNumber;
	}
}
