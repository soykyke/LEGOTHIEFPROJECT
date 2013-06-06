package model;
import javax.swing.SwingUtilities;

import view.GUI;
import view.Play;
import communication.ThiefCommunicator;




public class Controller {

	private ThiefCommunicator commBT;

	public Controller(){
		commBT = new ThiefCommunicator();
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


