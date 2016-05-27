import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Alexander on 5/27/2016.
 */
public class VacuumCleaner {
    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> lmao = new ArrayList<>();
        System.out.println("hur många tal vill du mata in?");
        int length = scanner.nextInt();
        for(int i = 0; i < length; i++){
            System.out.println("var vänlig att skriv in de tal som du vill flippa, du har nu skrivit in: "+i+" av "+ length+" tal.");
            lmao.add(scanner.nextInt());
        }
        System.out.println("dina tal baklänges är : ");
        for(int i = lmao.size()-1; i > -1; i--)
            System.out.println(lmao.get(i));

    }
}
