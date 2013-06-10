package communication;

import model.Controller;

public class ScenarioCommunicator extends BluetoothCommunicator {
	public ScenarioCommunicator() {
		super("fedor", "00:16:53:15:C2:59");
	}
	
	public void readMessages() {
		new Thread() { 
			public void run () {
				listenForMessages();
			}
		}.start();
	}
	
	public void listenForMessages() {
		while (true) {
			int msg = readInt();
			switch (msg) {
			case 1:
				Controller.diamondStolen();
				break;
			}
		}
	}
}
