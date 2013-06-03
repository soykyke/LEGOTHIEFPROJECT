import lejos.nxt.*;
import java.util.*;


public class Safe extends Thread {

    int[] key;
    long timeoutLength = 30000;
    DataOutputStream axe;


    TouchSensor[] buttons = {new TouchSensor(SensorPort.S1), new TouchSensor(SensorPort.S2), new TouchSensor(SensorPort.S3)};

    public Safe(int[] key, DataOutputStream dos) {
        this.key = key;
        this.dos = dos;
    }

    public void run() {
        try {
            boolean opened = false;
            while (!opened) {
                int[] combination = waitForCombination();
                if (Arrays.equals(key, combination)) {
                    succeed();
                    opened = true;
                } else {
                    fail();
                }
            }
        } catch (InterruptedException e) {

        }
    }

    /* can press each button only once, to make detection easier
    */
    protected int[] waitForCombination() throws InterruptedException {
        List<Integer> unpressedButtons = new LinkedList<Integer>();
        unpressedButtons.add(0);
        unpressedButtons.add(1);
        unpressedButtons.add(2);
        int first = 0;
        int second = 0;
        boolean firstPressed = false;
        boolean secondPressed = false;
        long timeFirstButtonPressed = 0;
        int[] failure = {-1, -1, -1};
        
        LCD.drawString("Waiting for 1st", 0, 0);
        while (!firstPressed) {
            Thread.sleep(100);
            for (Integer button : unpressedButtons) {
                if (buttons[button].isPressed()) {
                    first = button;
                    firstPressed = true;
                    timeFirstButtonPressed = System.currentTimeMillis();
                    break;
                }
                
            }
            
        } 
        unpressedButtons.remove(new Integer(first));
        Thread.sleep(200);
        
        LCD.drawString("Waiting for 2nd", 0, 0);
        while (!secondPressed) {
            Thread.sleep(100);
            if (System.currentTimeMillis() > timeFirstButtonPressed + timeoutLength) {
                timeout();
                return failure;
            }
            for (Integer button : unpressedButtons) {
                if (buttons[button].isPressed()) {
                    second = button;
                    secondPressed = true;
                    break;
                }
            }
            
        }
        unpressedButtons.remove(new Integer(second)); 
        Thread.sleep(200);
        
        Integer thirdButton = unpressedButtons.get(0);
        LCD.drawString("Waiting for 3rd", 0, 0);       
        while (!buttons[thirdButton].isPressed()) {
            if (System.currentTimeMillis() > timeFirstButtonPressed + timeoutLength) {
                timeout();
                return failure;
            }
            Thread.sleep(100);
        } 

        int[] combination = new int[3];
        combination[0] = first;
        combination[1] = second;
        combination[2] = thirdButton;
        return combination;
    }    

    void printList (List<Integer> list) {
        LCD.drawString("              ", 0,1);
        for (int i = 0; i < list.size(); i++)
            LCD.drawInt(list.get(i), i*2, 1);
    }

    void timeout() throws InterruptedException{
        LCD.clearDisplay();
        LCD.drawString("Timeout    ", 0, 0);
        Sound.buzz();
        Thread.sleep(1500);
    }

    void succeed() throws InterruptedException {
        LCD.clearDisplay();
        LCD.drawString("Succeeded   ", 0, 0);
        Sound.beepSequenceUp();
        sendStolenMessage();
        Axe.setTurning();
        Thread.sleep(1500);
    }

    void fail () throws InterruptedException {
        LCD.clearDisplay();
        LCD.drawString("Failed       ", 0, 0);
        Sound.beepSequence();
        Thread.sleep(1500);
    }

    void sendStolenMessage() throws InterruptedException    {
        LCD.clearDisplay();
        LCD.drawString("Sending stolen Msg", 0, 1);
        dos.writeChars("Stolen");
        dos.flush();
        Thread.sleep(1500);
    }
}