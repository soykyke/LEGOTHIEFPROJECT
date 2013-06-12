package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Controller;


public class GUI extends JFrame implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;
	
	private JButton connectButton = new JButton("Connect");   
	private JButton ExitButton = new JButton("Exit");    
	private JButton leftButton = new JButton("LEFT");
	private JButton rightButton = new JButton("RIGHT");
	private JButton upButton = new JButton("UP");
	private JButton downButton = new JButton("DOWN");

	private final int UP_VALUE = -1;
	private final int DOWN_VALUE = -2;
	private final int LEFT_VALUE = -3;
	private final int RIGHT_VALUE = -4;
	private final int NONE_VALUE = -5;
	Controller controller;
	JPanel p1;
	
	public GUI(Controller c) {
		controller = c;
		System.out.println("HOLA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("PC APP");
		setSize(500, 400);
		setLocationRelativeTo(null);
	   
		 p1 = new JPanel();
		
	    
		// holds connect button
	   
		p1.add(connectButton);
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.initCommunicators();
				p1.requestFocus();
				p1.requestFocusInWindow();
				p1.setFocusable(true);
			}
		});
	    
		p1.add(upButton);
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.controlButton(UP_VALUE);
			}
		});
		
		p1.add(downButton);
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.controlButton(DOWN_VALUE);
			}
		});
		
		p1.add(leftButton);
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.controlButton(LEFT_VALUE);
			}
		});
		
		p1.add(rightButton);
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.controlButton(RIGHT_VALUE);
			}
		});
	    
		p1.add(ExitButton);
		ExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		GridLayout l = new GridLayout(8,2);
		l.setColumns(2);
		p1.setLayout(l);
		
		p1.requestFocus();
		p1.requestFocusInWindow();
		p1.setFocusable(true);
		p1.addKeyListener(this);
		getContentPane().add(p1);



	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("HOLA");
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