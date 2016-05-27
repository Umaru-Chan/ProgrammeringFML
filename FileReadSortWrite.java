

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;

public class FileReadSortWrite {
    public static void main(String[] args) throws FileNotFoundException{
        ArrayList<Integer> lmao = new ArrayList<>();

        //Skapar ett Scannerobjekt för läsning av fil
        Scanner scan = new Scanner(new File("src/Random10k.txt"));

        //Läser från fil och fyller vektorn v
        while (scan.hasNextInt())
            lmao.add(scan.nextInt());

        //Läser tiden från systemklockan
        long t0 = System.currentTimeMillis();
        System.out.println("Tidtagning börjar");


        selectSort(lmao);

        //Läser tiden från systemklockan och skriver ut tidsåtgången
        long t1 = System.currentTimeMillis();
        System.out.println("\tTid i ms: " + (t1-t0));

        //Dirigerar om variabeln out till en fil på hårddisken
        PrintWriter out = new PrintWriter(new File("src/Sorterad.txt"));

        //Skriver inneh�llet i vektorn v till filen
        for(int j = 0; j < lmao.size(); j++)
            out.println(lmao.get(j));
        out.close();            //stänger filen
    }//main

    protected static void selectSort(ArrayList<Integer> osorteradLista){
        for(int k = 0; k < osorteradLista.size(); k++){
            for(int i = k; i < osorteradLista.size(); i++){
                if(osorteradLista.get(i) < osorteradLista.get(k)){
                    int prev = osorteradLista.get(i);
                    osorteradLista.set(i, osorteradLista.get(k));
                    osorteradLista.set(k, prev);
                }
            }
        }
    }
}
