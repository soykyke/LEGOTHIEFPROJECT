package communication;

import model.Controller;

public class ScenarioCommunicator extends BluetoothCommunicator {
	public ScenarioCommunicator() {
		super("NXT", "00:16:53:18:09:06");
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
			String s = readString();
			switch (s) {
			case "Stolen":
				Controller.diamondStolen();
				break;
			}
		}
	}
}
