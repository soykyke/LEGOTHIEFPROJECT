import lejos.nxt.*;

public class Axe  {
    
    public static void setTurning() {
        Motor.A.setSpeed(90);
        Motor.A.forward();
    }

    public static void rotateToBlockPosition() {
        Motor.A.rotateTo(90);
    }
}