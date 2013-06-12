package communication;

import model.Controller;

public class AxeCommunicator extends BluetoothCommunicator {
	public AxeCommunicator() {
		super("maria", "00:16:53:0A:46:7D");
	}

	public void readMessages() {
		new Thread() { 
			public void run () {
				listenForMessages();
			}
		}.start();
	}
	
	void listenForMessages() {
		while (!Controller.gameState.equals(Controller.GameState.GameOver) &&
				!Controller.gameState.equals(Controller.GameState.Won)) {
			int msg = readInt();
			try {
				handleMessage(msg);
			} catch (Exception e) {
				System.err.println("Error on received message: " + msg + "\n");
				e.printStackTrace();
			}
		}
	}
	
	void handleMessage(int msg) {
		switch (msg) {
		case 1: // thief was caught
			Controller.thiefWasCaughtByAxe();
			break;
		}
	}
}
