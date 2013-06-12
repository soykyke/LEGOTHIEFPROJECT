
package communication;

import model.Controller;

public class WatchDogCommunicator extends BluetoothCommunicator {

	public WatchDogCommunicator() {
		super("aulegoproject", "00:16:53:10:C7:9F");
	}

	public void readMessages() {
		new Thread() { 
			public void run () {
				listenForMessages();
			}
		}.start();
		new Thread() { 
			public void run () {
				sendInWatchDogAreaMessages();
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
		case 73: // dog is eating
			break;
			
		case 591: // dog is sleeping
			break;
			
		case 317: // thief was bitten
			Controller.bittenByDog();
			break;
		}
	}

	public void sendInWatchDogAreaMessages() {
		while (true) {
			if (Controller.gameState == Controller.GameState.GameOver ||
					Controller.gameState == Controller.GameState.Won)
				writeInt(2);
			else if (Controller.isInWatchDogArea)
				writeInt(1);
			else
				writeInt(0);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
