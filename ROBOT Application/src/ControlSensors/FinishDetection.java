package ControlSensors;

import lejos.nxt.*;
import lejos.robotics.Color;
import model.Controller;

public class FinishDetection {
	ColorSensor sensor;
	Controller controller;
	
	public FinishDetection(Controller c) {
		this.controller = c;
		sensor = new ColorSensor(SensorPort.S1);
		sensor.setFloodlight(true);
		
		new Thread() { 
			public void run () {
				try {
					while (true) {
						int colorId = sensor.getColorID();
						LCD.drawString("Color " + Integer.toString(colorId), 0, 0);
						if (colorId == Color.GREEN)
							controller.sendFinishMessage();
						Thread.sleep(10);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
