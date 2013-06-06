package communication;

public class ThiefCommunicator extends BluetoothCommunicator {

	public ThiefCommunicator() {
		super("NXT", "00:16:53:18:09:06");
	}
	
	public void controlButton(int directionCar){
		writeInt(directionCar);
	}
	
}
