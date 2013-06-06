package ControlSensors;

public class MovingCar extends Thread{
	
	private int leftPower;
	private int rightPower;
	private boolean forward;
	
	public void leftPower(int i){
		leftPower = i;
	}
	
	public void rightPower(int i){
		rightPower = i;
	}
	
	public void forward(boolean i){
		forward = i;
	}
	
	private void moving(){
		if (forward){
			Car.forward(leftPower, rightPower);
		}else{
			Car.backward(leftPower, rightPower);
		}
	}
	
	public void run() {
		while (true) {
			moving();
		}
	}

}
