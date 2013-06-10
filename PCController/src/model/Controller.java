package model;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import view.GUI;
import view.Play;
import view.Sound;
import communication.ScenarioCommunicator;
import communication.ThiefCommunicator;




public class Controller {

	public enum GameState {
		DiamondInSafe,
		DiamondStolen,
		GameOver,
		Won
	}

	private ThiefCommunicator thiefCommunicator;
	private ScenarioCommunicator scenarioCommunicator;
	private static Timer countdownTimer;
	public static GameState gameState;
	public static int countdownSecs = 180;
	private static int energy = 100;

	public Controller(){
		gameState = GameState.DiamondInSafe;
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
    	gameState = GameState.DiamondStolen;
    	Sound.safeOpen.play();
    	
    	Controller.countdownTimer = new Timer();
    	Controller.countdownTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	countdownSecs--;
                if (countdownSecs == 0) {
                	Controller.gameState = Controller.GameState.GameOver;
                	Controller.countdownTimer.cancel();
                }
            }
        }, 1000, 1000);
    }
    
    public static void bittenByDog() {
    	energy -= 20;
    	if (energy <= 0)
    		gameState = GameState.GameOver;
    }
    
    public static void gotOutOfBank() {
    	if (gameState == GameState.DiamondStolen)
    		gameState = GameState.Won;
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


