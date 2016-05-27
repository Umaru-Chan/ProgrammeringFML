/**
 * @(#)Vinst.java
 *
 *
 * @author André Jaoui
 * @version 1.00 2011/1/28
 */

import java.util.ArrayList;

public class Vinst {
	ArrayList<Integer> rb;
	int size;

	/**
	 * Konstruktor ger klassvariablerna ett initialvärde
	 * @param redBlack
	 * 			Heltalsvektor med 1 resp -1 för Svart resp Röd pjäs på
	 * @param size
	 * 			Storleken på spelplan i bredd resp längd
     */
    public Vinst(ArrayList<Integer> redBlack, int size) {
    	rb = redBlack;
    	this.size = size;
    }

	/**
	 * Avgör om någon fått tre i rad och därmed vunnit
	 * @param sq
	 *          Vektor med alla kvadrater
	 * @return
	 *          Returnerar true eller false beroende på vinst eller ej
	 */
	protected boolean treIRad(ArrayList<Square> sq){
		/* Vågrätt tre i rad */
		for(int i = 0; i < rb.size()-2; i++)
			if(Math.abs(rb.get(i)+rb.get(i+1)+rb.get(i+2)) == 3 && sq.get(i).getY() == sq.get(i+2).getY())
				return true;

		/* Lodrätt tre i rad */
		for(int i = 0; i < rb.size()-size*2; i++)
			if(Math.abs(rb.get(i)+rb.get(i+size)+rb.get(i+size*2)) == 3 )
				return true;

		/* Diagonalt \ tre i rad */
		for(int i = 0; i < rb.size()-size*2-2; i++)
			if(Math.abs(rb.get(i)+rb.get(i+size+1)+rb.get(i+size*2+2)) == 3 && sq.get(i).getX() == sq.get(i+size*2+2).getX()-sq.get(0).getSide()*2)
				return true;

		/* Diagonalt / tre i rad */
		for(int i = 2; i < rb.size()-size*2; i++)
			if(Math.abs(rb.get(i)+rb.get(i+size-1)+rb.get(i+size*2-2)) == 3 && sq.get(i).getX() == sq.get(i+size*2-2).getX()+sq.get(0).getSide()*2)
				return true;

		return false;		//Ingen vinst
	}//vinst
}
