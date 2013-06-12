package model;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.robotics.Color;
import ControlSensors.Car;

import communications.CommBT;
import communications.commDirections;


public class Controller 
{
    private CommBT commBT = new CommBT();
	ColorSensor sensor;

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
    	sensor = new ColorSensor(SensorPort.S1);
		sensor.setFloodlight(true);
		try {
	    	while (!Button.ESCAPE.isDown()) {
	    		readColorSensor();
				Thread.sleep(10);
	    	}
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	commBT.closeConnection();
        // Shut down light sensor, motors
        Car.closeDirections();
    }
    
    void readColorSensor() {
    	int colorId = sensor.getColorID();
		LCD.drawString("Color " + Integer.toString(colorId), 0, 0);
		try {
			commBT.writeInt(colorId);
		} catch (IOException e) {
			LCD.drawString("I AM IN EXCEPTION", 5, 5);
			e.printStackTrace();
		}
    }
	public static void main(String[] args) 
    {
        Controller controller = new Controller();
        
        controller.controlDirecions();
        
        controller.shutDown();
    }
}