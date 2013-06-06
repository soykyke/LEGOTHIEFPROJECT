package communications;

import java.io.IOException;

import ControlSensors.MovingCar;


public class commDirections extends Thread{

    private CommBT btc;
    private boolean isUP;
    private boolean isDOWN;
    private boolean isLEFT;
    private boolean isRIGHT;
    private boolean isNONE;
    
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
	public boolean isUP(){
		return isUP;
	}
	public boolean isDOWN(){
		return isDOWN;
	}
	public boolean isLEFT(){
		return isLEFT;
	}
	public boolean isRIGHT(){
		return isRIGHT;
	}
	public void modifyDirections() throws IOException{
		int value = btc.readInt();
		isUP = false;
		isDOWN = false;
		isLEFT = false;
		isRIGHT = false;
		if (value == UP_VALUE){
			isUP = true;
			moving.forward(true);
		}
		else if (value == DOWN_VALUE){
			isDOWN = true;
			moving.forward(false);
		}
		else if (value == LEFT_VALUE){
			isLEFT = true;
			moving.leftPower(100);
		}
		else if (value == RIGHT_VALUE){
			isRIGHT = true;
			moving.rightPower(100);
		}
		else if (value == NONE_VALUE){
			isNONE = true;
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