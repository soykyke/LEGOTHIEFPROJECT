package model;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;

import javax.swing.SwingUtilities;

import view.GUI;
import view.Play;
import view.Sound;
import communication.AxeCommunicator;
import communication.ScenarioCommunicator;
import communication.ThiefCommunicator;
import communication.WatchDogCommunicator;




public class Controller {

	public enum GameState {
		DiamondInSafe,
		DiamondStolen,
		GameOver,
		Won
	}

	private static ThiefCommunicator thiefCommunicator;
	private static ScenarioCommunicator scenarioCommunicator;
	private static WatchDogCommunicator watchDogCommunicator;
	private static AxeCommunicator axeCommunicator;
	private static Timer countdownTimer;
	public static GameState gameState = GameState.DiamondInSafe;
	public static int countdownSecs = 180;
	public static int energy = 100;

	public Controller(){
		initCommunicators();
		diamondStolen();
	}

	public void initCommunicators() {
		thiefCommunicator = new ThiefCommunicator();
		scenarioCommunicator = new ScenarioCommunicator();
		watchDogCommunicator = new WatchDogCommunicator();
		axeCommunicator = new AxeCommunicator();
		thiefCommunicator.connect();
		scenarioCommunicator.connect();
		watchDogCommunicator.connect();
		axeCommunicator.connect();
		scenarioCommunicator.readMessages();
		thiefCommunicator.readMessages();
		watchDogCommunicator.readMessages();
		axeCommunicator.readMessages();
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
    	
    	axeCommunicator.sendStartMessage();
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

	public static void thiefWasCaughtByAxe() {
		gameState = GameState.GameOver;
	}

	public static void inWatchDogArea() {
		watchDogCommunicator.sendInWatchDogAreaMessage();
	}
}
