import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.addon.IRSeekerV2;
import lejos.nxt.addon.IRSeekerV2.Mode;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;


public class WatchDog {
	/*public static void main(String[] args) {
		LightSensor light = new LightSensor(SensorPort.S1);
		while (!Button.ESCAPE.isPressed()) {
	        LCD.clear();
	        LCD.drawInt(light.getLightValue(), 2,2);
			}
	    }
	}*/	
	
	public static void main(String[] args) {
	    DifferentialPilot pilot = new DifferentialPilot(5.6f, 15.3f, Motor.A, Motor.C, false);
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
	private IRSeekerV2 seeker;
	private DifferentialPilot pilot;
	private Navigator navigator;
	
	public ChaseThief(DifferentialPilot pilot, Navigator navigator) {
		seeker = new IRSeekerV2(SensorPort.S4, Mode.AC);
		//seeker.setAddress(0x10);
		this.pilot = pilot;
		this.navigator = navigator;
		//this.setDaemon(true);
		//this.start();
	}
	
	public int takeControl() {
		//if (seeker.getDirection() >= 3 && seeker.getDirection() <= 7)
			return 90;
	    //return 0;  // this behavior always wants control.
	}

	public void suppress() {
		_suppressed = true;// standard practice for suppress methods
	}

	public void action() {
		_suppressed = false;
		LCD.drawString("Chase",0,5);
		int direction = seeker.getDirection();
		int distances[] = seeker.getSensorValues();
		int distance = 0;
		double angle = seeker.getAngle() * (-1);
		LCD.drawInt(direction,0,6);
		if (direction == 1) {
			distance = distances[0];
		}
		if (direction == 2) {
			distance = (distances[0] + distances[1]) / 2;
		}
		if (direction == 3) {
			distance = distances[1];
		}
		if (direction == 4) {
			distance = (distances[1] + distances[2]) / 2;
		}
		if (direction == 5) {
			distance = distances[2];
		}
		if (direction == 6) {
			distance = (distances[2] + distances[3]) / 2;
		}
		if (direction == 7) {
			distance = distances[3];
		}
		if (direction == 8) {
			distance = (distances[3] + distances[4]) / 2;
		}
		if (direction == 9) {
			distance = distances[4];
		}
		LCD.drawInt(distance,4,6);
		pilot.setTravelSpeed(1000);
		pilot.setRotateSpeed(1000);
		pilot.rotate(angle);
		while (!_suppressed && pilot.isMoving()) {
			Thread.yield(); //don't exit till suppressed
		}
		pilot.travel(distance / 10);
		while (!_suppressed && pilot.isMoving()) {
			Thread.yield(); //don't exit till suppressed
		}
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
		pilot.setTravelSpeed(500 + Math.random() * 100);
		pilot.setRotateSpeed(500 + Math.random() * 100);
		while (need > 0) {
			pilot.forward();
			int now = (int)System.currentTimeMillis();
			while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
				Thread.yield(); //don't exit till suppressed
			}
			pilot.stop();
			need -= 700;
			if (_suppressed)
				break;
			pilot.rotate(Math.random() * 720 - 360);
			now = (int)System.currentTimeMillis();
			while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
				Thread.yield(); //don't exit till suppressed
			}
			pilot.stop();
			need -= 700;
			if (_suppressed)
				break;
			pilot.forward();
			now = (int)System.currentTimeMillis();
			while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
				Thread.yield(); //don't exit till suppressed
			}
			pilot.stop();
			need -= 700;
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
		pilot.setTravelSpeed(400 + Math.random() * 100);
		pilot.setRotateSpeed(400 + Math.random() * 100);
		pilot.forward();
		int now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
			Thread.yield(); //don't exit till suppressed
		}
		pilot.stop();
		pilot.rotate(Math.random() * 720 - 360);
		now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
			Thread.yield(); //don't exit till suppressed
		}
		pilot.stop();
		pilot.forward();
		now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + (800 + (int)(Math.random() * ((1500 - 800) + 1)))) ) {
			Thread.yield(); //don't exit till suppressed
		}
		pilot.stop();
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
		active = false;
		LCD.drawString("Eate",0,5);
		pilot.setTravelSpeed(500);
		pilot.setRotateSpeed(500);
		navigator.goTo(50, 50);
		while (!_suppressed && navigator.isMoving() ) {
			Thread.yield(); //don't exit till suppressed
		}
	    int now = (int)System.currentTimeMillis();
		while (!_suppressed && ((int)System.currentTimeMillis()< now + 10000) ) {
			active = true;
			Thread.yield(); //don't exit till suppressed
		}
		if (active == true) {
			need = need - ((int)System.currentTimeMillis() - now);
		}
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
	  pilot.setTravelSpeed(400 + Math.random() * 100);
	  pilot.setRotateSpeed(400 + Math.random() * 100);
	  pilot.backward();
	  int now = (int)System.currentTimeMillis();
	  while (!_suppressed && ((int)System.currentTimeMillis()< now + 1000) ) {
	  	Thread.yield(); //don't exit till suppressed
	  }
	  pilot.stop();
	  pilot.rotate(180);
	  now = (int)System.currentTimeMillis();
	  while (!_suppressed && ((int)System.currentTimeMillis()< now + 1000) ) {
	  	Thread.yield(); //don't exit till suppressed
	  }
	  pilot.stop();
	  pilot.forward();
	  now = (int)System.currentTimeMillis();
	  while (!_suppressed && ((int)System.currentTimeMillis()< now + 1000) ) {
	  	Thread.yield(); //don't exit till suppressed
	  }
	  pilot.stop();
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
