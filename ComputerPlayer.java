/**
 * @(#)ComputerPlayer.java
 *
 * @author Andr� Jaoui
 * @version 1.00 2010/6/13
 *
 * Agerar spelare i ett luffarschackparti
 */


public class ComputerPlayer {
	private int size;

	/**
	 * Konstruktor
	 * @param s
     */
    protected ComputerPlayer(int s){
    	size = s;
    }

	/**
	 * Genererar en slumpmässig position för spelaren
	 * Borde byggas ut med mer AI
	 * @return
	 * 		Det slumpmässiga draget returneras
     */
	protected int getMove(){
		return (int)(Math.random()*size*size);
	}



}
