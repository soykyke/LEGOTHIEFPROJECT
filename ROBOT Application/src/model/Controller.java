package model;
import lejos.nxt.Button;
import ControlSensors.Car;

import communications.CommBT;
import communications.commDirections;


public class Controller 
{
    private CommBT commBT = new CommBT();

    public Controller() 
    {
    	commBT.openConnection();
    }
	
    public void controlDirecions() 
    {
    	commDirections directions = new commDirections(commBT);
    	directions.setDaemon(true);	
    	directions.start();
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
}