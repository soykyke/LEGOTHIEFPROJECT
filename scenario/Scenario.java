import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class Scenario {
    public static void main(String[] args) {
        DataOutputStream dos = null, dis = null;
        NXTConnection btc = Bluetooth.waitForConnection();
		
		dos = btc.openDataOutputStream();   
		dis = btc.openDataOutputStream();

        int[] key = {0,1,2};
        new Safe(key, dos).start();
        
        boolean timeout = false;
        while (!timeout) {
            checkForTimeout(dis);
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        Axe.rotateToBlockPosition();

        try {
			dis.close();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

    static void checkForTimeout(DataOutputStream dis) {
        //TODO:
    }
}