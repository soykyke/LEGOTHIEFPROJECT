package ControlSensors;
import lejos.nxt.*;


public class Car 
{
    // Commands for the motors
    private final static int forward  = 1,
                             backward = 2,
                             stop     = 3;
	                         
    private static MotorPort leftMotor = MotorPort.C;
    private static MotorPort rightMotor= MotorPort.B;
	
    private Car()
    {	   
    } 
   
    public static void stop() 
    {
	    leftMotor.controlMotor(0,stop);
	    rightMotor.controlMotor(0,stop);
    }
   
    public static void forward(int leftPower, int rightPower)
    {
	    leftMotor.controlMotor(leftPower,forward);
	    rightMotor.controlMotor(rightPower,forward);
    }
   
    public static void backward(int leftPower, int rightPower)
    {
	    leftMotor.controlMotor(leftPower,backward);
	    rightMotor.controlMotor(rightPower,backward);
    }
    public static void turnLeft(int leftPower, int rightPower){
    	 leftMotor.controlMotor(leftPower,backward);
    	 rightMotor.controlMotor(rightPower,forward);
    }
    public static void turnRight(int leftPower, int rightPower){
   	 leftMotor.controlMotor(leftPower,forward);
   	 rightMotor.controlMotor(rightPower,backward);
   }

	public static void closeDirections() {
		Motor.B.flt();
        Motor.C.flt();
	}
}
