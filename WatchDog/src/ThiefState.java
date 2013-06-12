import java.io.IOException;

public class ThiefState extends Thread{
	private int state = 0;

    private CommBT btc;
    
	public ThiefState (CommBT btcA){
		btc = btcA;
	}

	public void run() {
		while (true) {
			try {
				state = btc.readInt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean isInRoom() {
		return state == 1;
	}
	
	public boolean isDead() {
		return state == 2;
	}
}
