package model;
import java.io.IOException;

import lejos.nxt.Button;
import ControlSensors.Car;
import ControlSensors.FinishDetection;

import communications.CommBT;
import communications.commDirections;


public class Controller 
{
    private CommBT commBT = new CommBT();
    private FinishDetection finishDetection;

    public Controller() 
    {
    	commBT.openConnection();
    }
	
    public void controlDirecions() 
    {
    	commDirections directions = new commDirections(commBT);
    	directions.setDaemon(true);	
    	directions.start();
    	
    	finishDetection = new FinishDetection(this);
    }
	
    public void shutDown()
    {
    	while (!Button.ESCAPE.isDown()) {};
    	commBT.closeConnection();
        // Shut down light sensor, motors
        Car.closeDirections();
    }
    
    public static void main(String[] args) 
    {
        Controller controller = new Controller();
        
        controller.controlDirecions();
        
        controller.shutDown();
    }

	public void sendFinishMessage() {
		try {
			commBT.writeInt(1337);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}