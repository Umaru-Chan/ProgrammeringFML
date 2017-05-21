package konto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Transaktionskonto implements Serializable {
	/**
	 * 
	 */
	private boolean active;
	private int money;
	private long kontoNummer;
	
	public Transaktionskonto(long kontoNummer, int money){
		setActiveState(true);
		this.kontoNummer = kontoNummer;
		this.money = money;
	}
	
	public long getKontoNummer(){
		return kontoNummer;
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
	
	public void inputMoney(int money){
		this.money += money;
	}
	
	public int takeMoney(int munny){
		if(validate() && (money - munny) >= 0){
			money -= munny;
			return munny;
		}
		System.err.println("There is not enough money");
		return 0;
	}
	

}
