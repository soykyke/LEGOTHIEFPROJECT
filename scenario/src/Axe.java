
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.*;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class Axe  {
	TouchSensor touchSensor = new TouchSensor(SensorPort.S1);
	boolean wasPressed = true;
	NXTConnection btc;
	DataOutputStream dos;
    
    void setTurning() {
        Motor.A.setSpeed(90);
        Motor.A.forward();
    }

    public void rotateToBlockPosition() {
        Motor.A.rotateTo(90);
    }
    
    public void init() {
    	btc = Bluetooth.waitForConnection();
    	dos = btc.openDataOutputStream();
    	DataInputStream dis = btc.openDataInputStream();
    	try {
			if (dis.readInt() != 1)
				return;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	setTurning();
    	
    	new Thread() { 
			public void run () {
				try {
					while (true) {
						if (touchSensor.isPressed())
							wasPressed = true;
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		new Thread() { 
			public void run () {
				try {
					while (true) {
						LCD.drawString(wasPressed ? "true" : "false", 0, 0);
						if (!wasPressed) {
							try {
								dos.writeInt(1);
								dos.flush();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						wasPressed = false;
						Thread.sleep(3500);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
    }
    
    public static void main(String[] args) {
    	Axe axe = new Axe();
    	axe.init();
    	while (!Button.ESCAPE.isDown()) {
    	}
    }
}