package model;
import javax.swing.SwingUtilities;

import view.GUI;
import view.Play;
import communication.ScenarioCommunicator;
import communication.ThiefCommunicator;




public class Controller {

	public enum DiamondState {
		InSafe,
		Stolen
	}
	
	private ThiefCommunicator thiefCommunicator;
	private ScenarioCommunicator scenarioCommunicator;
	public static DiamondState diamondState;

	public Controller(){
		diamondState = DiamondState.Stolen;
		thiefCommunicator = new ThiefCommunicator();
		scenarioCommunicator = new ScenarioCommunicator();
		connect();
		scenarioCommunicator.readMessages();
	}

	public void connect() {
		thiefCommunicator.connect();
		scenarioCommunicator.connect();
	}

	public void controlButton(int directionCar) {
		thiefCommunicator.controlButton(directionCar);
	}

    public static void diamondStolen() {
    	diamondState = DiamondState.Stolen;
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


