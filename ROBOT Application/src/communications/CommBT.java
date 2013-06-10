package communications;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;

/**
 * Receive data from a PC, a phone, 
 * or another Bluetooth device.
 * 
 * Waits for a Bluetooth connection, receives two integers that are
 * interpreted as power and duration for a forward command to a
 * differential driven car. The resulting tacho counter
 * value is send to the initiator of the connection.
 * 
 * Based on Lawrie Griffiths BTSend
 * 
 * @author Ole Caprani
 * @version 26-2-13
 */
public class CommBT
{
    private String connected = "Connected";
    private String waiting = "Waiting...";
    private String closing = "Closing...";

    BTConnection btc;
    private DataInputStream dis;
    private DataOutputStream dos;
	
    public void openConnection() 
    {		
		
        LCD.drawString(waiting,0,0);

        btc = Bluetooth.waitForConnection();
        
        LCD.clear();
        LCD.drawString(connected,0,0);	

        dis = btc.openDataInputStream();
        dos = btc.openDataOutputStream();
      
    }
	
    public int readInt() throws IOException{
        return dis.readInt();
    }
    
    public boolean readBoolean() throws IOException{
    	return dis.readBoolean();
    }
    
    public void writeInt(int value) throws IOException{
    	dos.writeInt(value);
        dos.flush();
    }
		   
    public void closeConnection() 
    {
        LCD.clear();
        LCD.drawString(closing,0,0);
    	try 
    	{
    	    dis.close();
            dos.close();
            Thread.sleep(100); // wait for data to drain
            btc.close();    	
    	}
        catch (Exception e)
        {}
        try {Thread.sleep(1000);}catch (Exception e){}
        System.exit(0);
    }
    

}

