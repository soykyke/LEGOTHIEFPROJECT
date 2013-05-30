import java.awt.GridLayout;
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
		
		getContentPane().setLayout(null);
		
		JPanel hud = new JPanel();
		hud.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		hud.setLayout(null);
		
		GrabberShow gs = new GrabberShow(VIDEO_WIDTH, VIDEO_HEIGHT);
        Thread th = new Thread(gs);
        th.start();
        gs.setSize(VIDEO_WIDTH, VIDEO_HEIGHT);
        gs.setLocation(0,0);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(null);
		controlPanel.setSize(SCREEN_WIDTH - VIDEO_WIDTH, SCREEN_HEIGHT);
		controlPanel.setLocation(VIDEO_WIDTH,0);
		controlPanel.add(new JButton("hi"));
		hud.add(controlPanel);

        hud.add(gs);
        getContentPane().add(hud);
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
