/**
 * Klass för att skapa kryss och ringar i ett kvadratiskt luffarschack
 * Två klassvariabler w och sq
 *
 * @author André Jaoui
 * @version 1.00 2011/1/28
 */
import javax.swing.*;
import java.util.ArrayList;

public class Kryss {
	SimpleWindow w;
    ArrayList<Square> sq;

    public Kryss(SimpleWindow w, ArrayList<Square> sqIn) {
    	this.w = w;
        sq = sqIn;
    }

	/**
	 * spelar spelet genom att rita kryss och ringar
	 * @param size
	 * @return
     */
	public void play(int size ){
        w.setLineWidth(100/size);
        ArrayList<Integer> redBlack = new ArrayList<>();
        for(int i = 0; i < size*size; i++)
            redBlack.add(0);
        int index, xc, yc, option;

        Vinst win = new Vinst(redBlack, size);
        ComputerPlayer cp = new ComputerPlayer(size);

        do{
            w.waitForEvent();
            index = w.getMouseX()/sq.get(0).getSide() + size*(w.getMouseY()/sq.get(0).getSide());
            if(redBlack.get(index) == 0){
                w.setLineColor(java.awt.Color.BLACK);
                redBlack.set(index, 1);

                Square tempSquare = sq.get(index);
                w.moveTo(tempSquare.getX()	 					, tempSquare.getY());
                w.lineTo(tempSquare.getX() + tempSquare.getSide()	, tempSquare.getY() + tempSquare.getSide());
                w.moveTo(tempSquare.getX() + tempSquare.getSide()	, tempSquare.getY());
                w.lineTo(tempSquare.getX()	 					, tempSquare.getY() + tempSquare.getSide());

                if(!win.treIRad(sq)){								//Rött drag om inte svart vunnit
                    w.setLineColor(java.awt.Color.RED);

                    do{														//Kollar att valt index är ledig
                        index = cp.getMove();
                    }while(redBlack.get(index) != 0);

                    redBlack.set(index, -1);
                    xc = sq.get(index).getX() + sq.get(index).getSide()/2;			//x-centrum i kvadraten
                    yc = sq.get(index).getY() + sq.get(index).getSide()/2;			//y-centrum i kvadraten
                    w.moveTo (xc+(int)(0.4* w.getHeight()/size), yc);		//Flyttar pennan till h�ger
                    for	(double v = 0.0; v <= 2*Math.PI; v += Math.toRadians(10))
                        w.lineTo( xc + (int)(Math.cos(v)*sq.get(index).getSide()*0.4) , yc + (int)(Math.sin(v)*sq.get(index).getSide()*0.4));
                }//nästlad if
            }//if
        }while (!win.treIRad(sq));

		/* Meddelar vem som vunnit och om du vill spela igen */
        if(redBlack.get(index) == -1){
            JOptionPane.showMessageDialog(null, "Röd spelare vann");
            option = JOptionPane.showConfirmDialog(null, "Spela igen?", "Luffarshack", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION)
                new Rita(w).rutor();
        }
        else{
            JOptionPane.showMessageDialog(null, "Svart spelare vann");
            option = JOptionPane.showConfirmDialog(null, "Spela igen?", "Luffarshack", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION)
                new Rita(w).rutor();
        }

        JOptionPane.showMessageDialog(null,"Tack för att du ville spela med mig");
        System.exit(0);
 	}//play

}//class
