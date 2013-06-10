import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.IRSeeker;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;


public class WatchDog {
	/*public static void main(String[] args) {
		IRSeeker seeker = new IRSeeker(SensorPort.S4);
		LightSensor light = new LightSensor(SensorPort.S1);
		seeker.setAddress(0x10);
	    Motor.A.setSpeed(400);
	    Motor.C.setSpeed(400);
		while (!Button.ESCAPE.isPressed()) {
			int direction = seeker.getDirection();
	        LCD.clear();
	        LCD.drawInt(direction, 2,2);
	        LCD.drawInt(seeker.getSensorValue(direction), 3,3);
	        LCD.refresh();
			Motor.A.forward();
			Motor.C.forward();
			int now = (int)System.currentTimeMillis();
			while (((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
				Thread.yield(); //don't exit till suppressed
			}
	    }
	}	*/
	
	public static void main(String[] args) {
	    Motor.A.setSpeed(400);
	    Motor.C.setSpeed(400);
	    DifferentialPilot pilot = new DifferentialPilot(5.6f, 17.5f, Motor.C, Motor.A, true);
	    Navigator navigator = new Navigator(pilot);
	    Behavior b1 = new ChaseThief(pilot, navigator);
	    Behavior b2 = new Watch(pilot, navigator);
	    Behavior b3 = new Walk(pilot, navigator);
	    Behavior b4 = new Eate(pilot, navigator);
	    Behavior b5 = new Sleep(pilot, navigator);
	    Behavior b6 = new DetectGreenZone(pilot, navigator);
	    Behavior b7 = new DetectThief(pilot, navigator);
	    Behavior b8 = new Exit();
	    Behavior[] behaviorList =
	    {
	      b1, b2, b3, b4, b5, b6, b7, b8
	    };
	    Arbitrator arbitrator = new Arbitrator(behaviorList);
	    Button.waitForAnyPress();
	    arbitrator.start();
	}
}

class ChaseThief implements Behavior {
	private boolean _suppressed = false;
	private IRSeeker seeker;
	private DifferentialPilot pilot;
	private Navigator navigator;
	
	public ChaseThief(DifferentialPilot pilot, Navigator navigator) {
		seeker = new IRSeeker(SensorPort.S4);
		seeker.setAddress(0x10);
		this.pilot = pilot;
		this.navigator = navigator;
		//this.setDaemon(true);
		//this.start();
	}
	
	/*public void run() {
		while ( true )  { 
			LCD.drawString(Integer.toString(seeker.getDirection()),0,6);
			LCD.drawString(Integer.toString(seeker.getSensorValue(seeker.getDirection())),4,6);
		}
	}*/
	
	public int takeControl() {
		if (seeker.getDirection() >= 3 && seeker.getDirection() <= 7)
			return 90;
	    return 0;  // this behavior always wants control.
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		LCD.drawString("Chase",0,5);
		LCD.drawInt(seeker.getDirection(),0,6);
		Motor.A.setSpeed(850);
	    Motor.C.setSpeed(850);
		pilot.rotate((5 - seeker.getDirection()) * 30);
		Motor.A.forward();
		Motor.C.forward();
		int now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + 500) ) {
			Thread.yield(); //don't exit till suppressed
		}
		 
		//Motor.C.stop();
		//Motor.A.stop();
	}
}

class Watch implements Behavior {
	private boolean _suppressed = false;
	private int need;
	private DifferentialPilot pilot;
	private Navigator navigator;
	
	public Watch(DifferentialPilot pilot, Navigator navigator) {
		need = 8000;
		this.pilot = pilot;
		this.navigator = navigator;
	}
	
	public int takeControl() {
	    if (need >= 10000) {
	    	return 40;
	    } else {
	    	need += 2;
	    }
	    return 0;
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		LCD.drawString("Watch",0,5);
		while (need > 0) {
		    Motor.A.setSpeed(500 + (int)(Math.random() * ((700 - 500) + 1)));
		    Motor.C.setSpeed(500 + (int)(Math.random() * ((700 - 500) + 1)));
			Motor.A.forward();
			Motor.C.forward();
			int now = (int)System.currentTimeMillis();
			while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
				Thread.yield(); //don't exit till suppressed
			}
		    
			// Stop for 1000 msec
			LCD.drawString("Stopped       ",0,3);
			Motor.A.stop(); 
			Motor.C.stop();
			now = (int)System.currentTimeMillis();
			while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
				Thread.yield(); //don't exit till suppressed
			}
		    
			// Turn
			LCD.drawString("Turn          ",0,3);
			Motor.A.rotate((-180 + (int)(Math.random() * ((-100 + 180) + 1))), true);// start Motor.A rotating backward
			Motor.C.rotate((-360 + (int)(Math.random() * ((-260 + 360) + 1))), true);  // rotate C farther to make the turn
			while (!_suppressed && Motor.C.isMoving()) {
				Thread.yield(); //don't exit till suppressed
			}
			Motor.A.stop(); 
			Motor.C.stop();
			need -= 2000;
			if (_suppressed)
				break;
		}
	}
}

class Walk implements Behavior {
	private boolean _suppressed = false;
	private DifferentialPilot pilot;
	private Navigator navigator;
	
	public Walk(DifferentialPilot pilot, Navigator navigator) {
		this.pilot = pilot;
	    this.navigator = navigator;
	}
	
	public int takeControl() {
	    return 10;  // this behavior always wants control.
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		LCD.drawString("Walk",0,5);
	    Motor.A.setSpeed(300 + (int)(Math.random() * ((500 - 300) + 1)));
	    Motor.C.setSpeed(300 + (int)(Math.random() * ((500 - 300) + 1)));
		Motor.A.forward();
		Motor.C.forward();
		int now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
			Thread.yield(); //don't exit till suppressed
		}
	    
		// Stop for 1000 msec
		LCD.drawString("Stopped       ",0,3);
		Motor.A.stop(); 
		Motor.C.stop();
		now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
			Thread.yield(); //don't exit till suppressed
		}
	    
		// Turn
		LCD.drawString("Turn          ",0,3);
		Motor.A.rotate((-180 + (int)(Math.random() * ((-100 + 180) + 1))), true);// start Motor.A rotating backward
		Motor.C.rotate((-360 + (int)(Math.random() * ((-260 + 360) + 1))), true);  // rotate C farther to make the turn
		while (!_suppressed && Motor.C.isMoving()) {
			Thread.yield(); //don't exit till suppressed
		}
		Motor.A.stop(); 
		Motor.C.stop();
	}
}

class Eate implements Behavior {
	private boolean _suppressed = false;
	private double need;
	private DifferentialPilot pilot;
	private Navigator navigator;
	private boolean active = false;
	
	public Eate(DifferentialPilot pilot, Navigator navigator) {
		this.pilot = pilot;
	    this.navigator = navigator;
		need = 0;
	}
	
	public int takeControl() {
	    if (need >= 10000) {
	    	return 60;
	    } else {
	    	need += 1.5;
	    }
	    return 0;
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		LCD.drawString("Eate",0,5);
	    Motor.A.setSpeed(400);
	    Motor.C.setSpeed(400);
		navigator.goTo(50, 50);
	    active = true;
	    int now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + 10000) ) {
			Thread.yield(); //don't exit till suppressed
		}
		active = false;
	    need = 0;
	}
}

class Sleep implements Behavior {
	private boolean _suppressed = false;
	private double need;
	private DifferentialPilot pilot;
	private Navigator navigator;
	private boolean active = false;
	
	public Sleep(DifferentialPilot pilot, Navigator navigator) {
		this.pilot = pilot;
	    this.navigator = navigator;
		need = -50;
	}
	
	public int takeControl() {
	    if (need >= 10000 && active == false) {
	    	return 80;
	    } else if (active) {
	    	return 95;
	    } else {
	    	need += 1;
	    }
	    return 0;
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		LCD.drawString("Sleep",0,5);
	    Motor.A.setSpeed(400);
	    Motor.C.setSpeed(400);
		navigator.goTo(0, 0);
		if (active != true) {
			active = true;
		}
	    int now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + 10000) ) {
			Thread.yield(); //don't exit till suppressed
		}
		if (!_suppressed) {
			active = false;
		    need = 0;
		}
	}
}

class DetectGreenZone extends Thread implements Behavior {
	private boolean _suppressed = false;
	private boolean active = false;
	private DifferentialPilot pilot;
	private Navigator navigator;
	private LightSensor lightSens;
	private int light = 20;

	public DetectGreenZone(DifferentialPilot pilot, Navigator navigator) {
		this.pilot = pilot;
	    this.navigator = navigator;
		lightSens = new LightSensor(SensorPort.S1);
		this.setDaemon(true);
		this.start();
	}
  
	public void run() {
		while ( true )  { 
			light = lightSens.readValue();
			LCD.drawString(Integer.toString(light),0,4); 
		}
	}

	public int takeControl() {
		if (light > 50)
			return 100;
		return 0;
	}

	public void suppress() {
	  _suppressed = true;// standard practice for suppress methods  
	}

	public void action() {
	  _suppressed = false;
	  active = true;
	  LCD.drawString("DGZ",0,5);
	  Sound.beepSequenceUp();
	
	  // Backward for 1000 msec
	  LCD.drawString("Drive backward",0,3);
	  Motor.A.backward();
	  Motor.C.backward();
	  int now = (int)System.currentTimeMillis();
	  while (!_suppressed && ((int)System.currentTimeMillis()< now + 1000) ) {
		  Thread.yield(); //don't exit till suppressed
	  }
    
	  // Stop for 1000 msec
	  LCD.drawString("Stopped       ",0,3);
	  Motor.A.stop(); 
	  Motor.C.stop();
	  now = (int)System.currentTimeMillis();
	  while (!_suppressed && ((int)System.currentTimeMillis()< now + 1000) ) {
		  Thread.yield(); //don't exit till suppressed
	  }
    
	  // Turn
	  LCD.drawString("Turn          ",0,3);
	  Motor.A.rotate(-180, true);// start Motor.A rotating backward
	  Motor.C.rotate(-360, true);  // rotate C farther to make the turn
	  while (!_suppressed && Motor.C.isMoving()) {
		  Thread.yield(); //don't exit till suppressed
	  }
	  Motor.A.stop(); 
	  Motor.C.stop();
	  LCD.drawString("Stopped       ",0,3);
	  Sound.beepSequence();
	  active = false;  
	}

}

class DetectThief implements Behavior {
	private boolean _suppressed = false;
	private TouchSensor touch1, touch2;
	private DifferentialPilot pilot;
	private Navigator navigator;

	public DetectThief(DifferentialPilot pilot, Navigator navigator) {
		touch1 = new TouchSensor(SensorPort.S2);
		touch2 = new TouchSensor(SensorPort.S3);
		this.pilot = pilot;
	    this.navigator = navigator;
	}
	
	public int takeControl() {
		if (touch1.isPressed() && touch2.isPressed())
			return 200;
		return 0;
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods  
	}

	public void action() {
		LCD.drawString("DT",0,5);
		System.exit(0);
	}
}

class Exit implements Behavior {
	private boolean _suppressed = false;

	public int takeControl() {
		if ( Button.ESCAPE.isPressed() )
			return 300;
		return 0;
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods  
	}

	public void action() {
		LCD.drawString("E",0,5);
		System.exit(0);
	}
}
