package view;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;

import javax.swing.GrayFilter;
import javax.swing.JPanel;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.VideoInputFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class GrabberShow extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	IplImage image;
	IplImage img;
	int width;
	int height;
	public boolean greyOut = false;
	
	public GrabberShow(int w, int h) {
		super();
		width = w;
		height = h;
	}
	
	@Override
	public void run() {
		FrameGrabber grabber = new VideoInputFrameGrabber(3); // 1 for next camera
		try {
			grabber.start();
			
			while (true) {
				img = grabber.grab();
				if (img != null) {
					this.repaint();
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null) {
			if (greyOut) {
				Graphics2D g2 = (Graphics2D) g;
				ImageFilter filter = new GrayFilter(true, 50);
				ImageProducer producer = new FilteredImageSource(img.getBufferedImage().getSource(), filter);
				Image image = this.createImage(producer);
				g2.drawImage(image, 0, 0, width, height, null);
			} else {
				Graphics2D g2 = (Graphics2D) g;
				g2.drawImage(img.getBufferedImage(), 0, 0, width, height, null);
			}
		}
	}
}


