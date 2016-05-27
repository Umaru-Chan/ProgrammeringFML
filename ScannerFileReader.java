import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * Klassen Läser från en given fil in ett antal heltalsvärden som flyttas över till en vektor/array
 * När det är klart avläses systemklockan och metoden find anropas ett antal gånger.
 * Slutligen hämtas tiden igen från systemklockan och tidsåtgången skrivs ut.
 */
public class ScannerFileReader {
	public static void main(String[] args) throws FileNotFoundException{
		ArrayList<Integer> list = new ArrayList<>();
        int i = 0;

		/* Skapar ett Scannerobjekt för läsning av fil */
		Scanner scan = new Scanner(new File("src/Random10k.txt"));

		/* Läser från fil och fyller vektorn v */
		while (scan.hasNextInt())
			list.add(scan.nextInt());

		/* Läser tiden från systemklockan */
		long t0 = System.currentTimeMillis();

		/* Anrop av metoden find som returnerar indexet för sökt tal */
		int result = find(list, Integer.parseInt(JOptionPane.showInputDialog("vilket nummer vill du hitta ??")));

		/* Läser tiden från systemklockan och skriver ut tidsåtgången */
		long t1 = System.currentTimeMillis();
		System.out.println("\tTid i ms: " + (t1-t0));
		System.out.println("ditt nummer hittades : "+result+" gånger!");
	}

    /**
     *
     * @param birthdate
     * @return antal gånger födelsedatumet hittats i filen
     */
    private static int find(ArrayList<Integer> list, int birthdate){
        int nbr = 0;
		for(int i = 0; i < list.size(); i++)
			if(list.get(i) == birthdate)
				nbr++;
        return nbr;
    }
}
