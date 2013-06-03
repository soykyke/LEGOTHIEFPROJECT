import lejos.nxt.*;

public class Scenario {
    public static void main(String[] args) {
        int[] key = {0,1,2};
        new Safe(key).start();
    }
}