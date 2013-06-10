package communication;

import model.Controller;
import scenario.Safe.Messages;
import view.Sound;

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
			try {
				handleMessage(msg);
			} catch (Exception e) {
				System.err.println("Error on received message: " + msg + "\n");
				e.printStackTrace();
			}
		}
	}

	private void handleMessage(int msg) throws Exception {
		Messages message = Messages.values()[msg];
		switch (message) {
		case SUCCESS:
			Controller.diamondStolen();
			break;
		case FAIL:
			Sound.safeFail.play();
			break;
		case KEY_PRESS:
			Sound.pressKey.play();
			break;
		}
	}
}
