/**
 * Spel för luffarschack med tre i rad för vinst
 * Datorspelaren svarar alltid med ett slumpmässigt drag
 * @author André Jaoui
 * @version 1.0
 */
public class Luffarschack13 {
    public static void main(String[] args){
        SimpleWindow w = new SimpleWindow(700, 700, "Luffarschack");
        new Welcome(w).info();
        new Rita(w).rutor();
    }
}
