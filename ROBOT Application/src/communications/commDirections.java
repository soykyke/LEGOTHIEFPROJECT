package communications;

import java.io.IOException;

import ControlSensors.MovingCar;


public class commDirections extends Thread{

    private CommBT btc;
    
    private static final int UP_VALUE = -1;
    private static final int DOWN_VALUE = -2;
    private static final int LEFT_VALUE = -3;
    private static final int RIGHT_VALUE = -4;
    private static final int NONE_VALUE = -5;

    private MovingCar moving;
    
	public commDirections (CommBT btcA){
		btc = btcA;
		moving = new MovingCar();
		moving.setDaemon(true);	
		moving.start();
	}
	
	public void modifyDirections() throws IOException{
		int value = btc.readInt();
	
		if (value == UP_VALUE){
			moving.forward(true);
		}
		else if (value == DOWN_VALUE){
			moving.forward(false);
		}
		else if (value == LEFT_VALUE){
			moving.leftPower(80);
		}
		else if (value == RIGHT_VALUE){
			moving.rightPower(80);
		}
		else if (value == NONE_VALUE){
			moving.leftPower(0);
			moving.rightPower(0);
		}

	}
	public void run() {
		while (true) {
			try {
				modifyDirections();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}