package model;
import javax.swing.SwingUtilities;

import view.GUI;
import view.Play;
import communication.CommBT;




public class Controller {

	private CommBT commBT;

	public Controller(){
		commBT = new CommBT();
		connect();
	}

	public void connect() {
		commBT.connect();
	}

	public void controlButton(int directionCar) {
		commBT.controlButton(directionCar);
	}
	
	public static void main(String[] args) {
		final Controller controller = new Controller();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Play ex = new Play(controller);
				ex.setVisible(true);
			}
		});
	
	}
}


