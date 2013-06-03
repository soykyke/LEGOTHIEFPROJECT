import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.*;


import com.googlecode.javacv.OpenCVFrameGrabber;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class GUI extends JFrame {
	int SCREEN_WIDTH = 1280;
	int SCREEN_HEIGHT = 768;
	int VIDEO_WIDTH = 1024;
	int VIDEO_HEIGHT = 768;
	
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Thief Game");
		
		//setUndecorated(true);
		setLocationRelativeTo(null);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.pack();
		getContentPane().setLayout(null);
		this.setVisible(true);
		
		JPanel hud = new JPanel();
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI ex = new GUI();
				ex.setVisible(true);
			}
		});
	}

}
