import lejos.nxt.*;

public class Scenario {
    public static void main(String[] args) {
        DataOutputStream dos = null, dis = null;
        try {
            NXTConnection btc = Bluetooth.waitForConnection();
            DataOutputStream dos;
            dos = btc.openDataOutputStream();   
            dis = btc.openDataInputStream();   
        } catch (IOException e) {
            LCD.drawString("Error BT connection", 0, 0);
            return;
        }

        int[] key = {0,1,2};
        new Safe(key, dos).start();
        
        boolean timeout = false;
        while (!timeout) {
            checkForTimeout(dis);
            Thread.sleep(100);
        }
        Axe.rotateToBlockPosition();

        dis.close();
        dos.close();
    }

    static void checkForTimeout(DataInputStream dis) {
        //TODO:
    }
}