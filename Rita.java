/**
 * @(#)Rita.java
 *
 *
 * @author André Jaoui
 * @version 1.00 2011/1/28
 */

import javax.swing.*;
import java.util.ArrayList;

public class Rita {
	SimpleWindow w;

    /**
     * Konstruktor för ritaklassen
     * @param w
     */
    public Rita(SimpleWindow w) {
    	this.w = w;
    }

    /**
     * Ritar upp ett rutnät
     */
    public void rutor() {
        /* Välkommnar spelaren och ritar en startruta */
		String storlek = JOptionPane.showInputDialog("Välj bredd/längd på spelplan!");
		if(storlek == null)System.exit(0);
		int size = Integer.parseInt(storlek);

        ArrayList<Square> sq = new ArrayList<>();
    	w.setLineWidth(1);
		w.clear();
		w.setLineColor(java.awt.Color.BLACK);

    	for(int i = 0; i<size*size; i++){
    		sq.add(new Square(i%size*w.getWidth()/size, (i/size)*w.getHeight()/size, w.getHeight()/size));
			sq.get(i).draw(w);
		}
        Kryss kr = new Kryss(w, sq);
        kr.play(size);
    }
}
