package communication;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;


public class CommBT {

	NXTComm nxtComm;
	NXTInfo nxtInfo;
	
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	
	public void connect(){
		try {
			//"00:16:53:18:09:06
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", "00:16:53:18:09:06");
			nxtComm.open(nxtInfo);
			is = ((lejos.pc.comm.NXTComm) nxtComm).getInputStream();
			os = ((lejos.pc.comm.NXTComm) nxtComm).getOutputStream();
            
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
			System.out.println("I am connected");
		} catch (NXTCommException e) {
			e.printStackTrace();
		}
	}
	
	public void controlButton(int directionCar){
		try {
			dos.writeInt(directionCar);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}