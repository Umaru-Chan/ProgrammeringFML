import javax.swing.*;
import java.util.Scanner;
import java.io.*;

/**
 * Klassen Läser från en given fil in ett antal heltalsvärden som flyttas över till en vektor/array
 * När det är klart avläses systemklockan och metoden find anropas ett antal gånger.
 * Slutligen hämtas tiden igen från systemklockan och tidsåtgången skrivs ut.
 */
public class ScannerFileReader {
	public static void main(String[] args) throws FileNotFoundException{
		/* Skapar ett Scannerobjekt för läsning av fil */
		Scanner scan = new Scanner(new File("src/Random10k.txt"));

		/* Läser tiden från systemklockan */
		long t0 = System.currentTimeMillis();

		/* Läser från fil och hittar datumet */
		int number = Integer.parseInt(JOptionPane.showInputDialog(null, "nummer ??????"));
		int index = -1, i = 0;
		while (scan.hasNextInt()) {
			if (scan.nextInt() == number) {
				index = i;
				break;
			}
			i++;
		}
		System.out.println(index == -1 ? "gick inte att hitta ditt nummer" : index);

		/* Läser tiden från systemklockan och skriver ut tidsåtgången */
		System.out.println("Tid i ms: " + (System.currentTimeMillis() - t0));

		
	}

}
