/**
 * @(#)Welcome.java
 *
 * @author André Jaoui
 * @version 1.00 2011/1/28
 */

public class Welcome {
	SimpleWindow w;

    /**
     * Konstruktor för med en tvingande parameter
     * @param win
     */
    public Welcome(SimpleWindow win) {
    	w = win;
    }

    /**
     * Skriver en välkomsthälsning i SimpleWindow fönstret w
     */
    public void info(){
       	w.moveTo(150,240);  w.writeText("Välkommen till Luffarschack/3 i rad. ");
    	w.moveTo(150,260); 	w.writeText("Du spelar svart, datorn rött");
    	w.moveTo(150,280);	w.writeText("Starta spelet genom att klicka valfri storleksruta");
	}//info
}
