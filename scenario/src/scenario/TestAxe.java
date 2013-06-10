package scenario;
import lejos.nxt.*;
public class TestAxe {
    
    public static void main (String[] aArg)
    throws Exception
    {
        Motor.A.setSpeed(90);
        Motor.A.forward();
        Thread.sleep(2000);
        Motor.A.rotateTo(90);
    }
}

