package communication;

import model.Controller;

public class ThiefCommunicator extends BluetoothCommunicator {

	public ThiefCommunicator() {
		super("NXT", "00:16:53:18:09:06");
	}
	
	public void controlButton(int directionCar){
		writeInt(directionCar);
	}
	
	public void readMessages() {
		new Thread() { 
			public void run () {
				try {
					while (true) {
						int m = readInt();
						
						switch (m) {
						case lejos.robotics.Color.GREEN:
							Controller.gotOutOfBank();
							break;
							
						case lejos.robotics.Color.WHITE:
							Controller.setInWatchDogArea(true);
							break;
							
						default:
							Controller.setInWatchDogArea(false);
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
