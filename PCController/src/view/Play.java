package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.GrabberShow;

import model.Controller;

public class Play extends JFrame implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int SCREEN_WIDTH = 1280;
	int SCREEN_HEIGHT = 768;
	int VIDEO_WIDTH = 1024;
	int VIDEO_HEIGHT = 768;
	
	private final int UP_VALUE = -1;
	private final int DOWN_VALUE = -2;
	private final int LEFT_VALUE = -3;
	private final int RIGHT_VALUE = -4;
	private final int NONE_VALUE = -5;
	
	Controller controller;
	JPanel hud;
	public Play(Controller c)  {
		controller = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Thief Game");
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		pack();
		getContentPane().setLayout(null);
		setVisible(true);
		
		hud = new JPanel();
		hud.setBackground(Color.BLACK);
		hud.setLocation(getHPosition(0), getVPosition(0));
		hud.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		hud.setLayout(null);
		
		GrabberShow gs = new GrabberShow(VIDEO_WIDTH, VIDEO_HEIGHT);
        Thread th = new Thread(gs);
        th.start();
		
        gs.setSize(VIDEO_WIDTH, VIDEO_HEIGHT);
        gs.setLocation(getHPosition(0), getVPosition(0));
        gs.setBackground(Color.BLACK);
        gs.setOpaque(false);
        hud.add(gs);
        
		/*JPanel controlPanel = new JPanel();
		controlPanel.setSize(SCREEN_WIDTH - VIDEO_WIDTH, SCREEN_HEIGHT);
		controlPanel.setLocation(VIDEO_WIDTH, 0);
		controlPanel.add(new JButton("hi"));
		controlPanel.add(new JButton("hi"));
		controlPanel.add(new JButton("hi"));
		controlPanel.setVisible(true);
		controlPanel.setBackground(Color.YELLOW);*/
		
		JButton btn = new JButton("hey");
		btn.setSize(100, 100);
		btn.setLocation(getHPosition(VIDEO_WIDTH), getVPosition(0));
		btn.setLayout(null);
		hud.add(btn);
		
		hud.requestFocus();
		hud.requestFocusInWindow();
		hud.setFocusable(true);
		hud.addKeyListener(this);
		
		getContentPane().add(hud);
		getContentPane().setBackground(Color.GRAY);
	}
	
	int getHPosition(int relative) {
		int w = this.getBounds().width;
		if (w > SCREEN_WIDTH)
			return (w - SCREEN_WIDTH) / 2 + relative;
		return relative;
	}
	
	int getVPosition(int relative) {
		return relative;
	}

	private void doDirectionConversion(int keyCode){
		switch (keyCode){
			case KeyEvent.VK_UP:
				controller.controlButton(UP_VALUE);
				break;
			case KeyEvent.VK_LEFT:
				controller.controlButton(LEFT_VALUE);
				break;
			case KeyEvent.VK_RIGHT:
				controller.controlButton(RIGHT_VALUE);
				break;
			case KeyEvent.VK_DOWN:
				controller.controlButton(DOWN_VALUE);
				break;
			case NONE_VALUE:
				controller.controlButton(NONE_VALUE);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
	
    /** Handle the key typed event from the text field. */
	@Override
    public void keyTyped(KeyEvent e) {
		//doDirectionConversion(e.getKeyCode());
        displayInfo(e, "KEY TYPED: ");
    }
    
    /** Handle the key pressed event from the text field. */
	@Override
    public void keyPressed(KeyEvent e) {
		doDirectionConversion(e.getKeyCode());
        //displayInfo(e, "KEY PRESSED: ");
    }
    
    /** Handle the key released event from the text field. */
	@Override
    public void keyReleased(KeyEvent e) {
		doDirectionConversion(NONE_VALUE);
        //displayInfo(e, "KEY RELEASED: ");
    }
	
	 private void displayInfo(KeyEvent e, String keyStatus){
	        
	        //You should only rely on the key char if the event
	        //is a key typed event.
	        int id = e.getID();
	        String keyString;
	        if (id == KeyEvent.KEY_TYPED) {
	            char c = e.getKeyChar();
	            keyString = "key character = '" + c + "'";
	        } else {
	            int keyCode = e.getKeyCode();
	            keyString = "key code = " + keyCode
	                    + " ("
	                    + KeyEvent.getKeyText(keyCode)
	                    + ")";
	        }
	        
	        int modifiersEx = e.getModifiersEx();
	        String modString = "extended modifiers = " + modifiersEx;
	        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
	        if (tmpString.length() > 0) {
	            modString += " (" + tmpString + ")";
	        } else {
	            modString += " (no extended modifiers)";
	        }
	        
	        String actionString = "action key? ";
	        if (e.isActionKey()) {
	            actionString += "YES";
	        } else {
	            actionString += "NO";
	        }
	        
	        String locationString = "key location: ";
	        int location = e.getKeyLocation();
	        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
	            locationString += "standard";
	        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
	            locationString += "left";
	        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
	            locationString += "right";
	        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
	            locationString += "numpad";
	        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
	            locationString += "unknown";
	        }
	        
	        System.out.println(keyStatus);
	        System.out.println(keyString);
	        System.out.println(modString);
	        System.out.println(actionString);
	        System.out.println(locationString);
	    }
	 
	 
}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI ex = new GUI();
				ex.setVisible(true);
			}
		});
	}*/

