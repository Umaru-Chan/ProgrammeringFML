package konto;

import java.io.Serializable;

public class Sparkonto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean active;
	private int money;
	private long kontoNummer;
	private double rent;
	
	public Sparkonto(long kontoNummer, int money, double rent){
		setActiveState(true);
		this.kontoNummer = kontoNummer;
		this.money = money;
		this.rent = rent;
	}
	
	public long getKontoNummer(){
		return kontoNummer;
	}
	
	
	public double getRent(){
		return rent;
	}
	
	public int getCurrentAmount(){
		return money; 
	}
	
	public void setActiveState(boolean active){
		this.active = active;
	}
	
	public boolean validate(){
		if(money <= 0){
			setActiveState(false);
		}
		return true;
	}
	
	public void setRent(double rent){
		this.rent = rent;
	}
	
	public void addMoney(int money){
		this.money += money;
	}
	
	public void monthlyRent(){
		this.money += money*rent;
	}
	
	public int takeMoney(int munny){
		if(validate() && (money - munny) >= 0){
			money -= munny;
			return munny;
		} else {
			System.err.println("There is not enough money");
			return 0;
		}
	}
}
