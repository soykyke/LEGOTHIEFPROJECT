import java.awt.Graphics;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.VideoInputFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import javax.swing.*;

public class GrabberShow extends JPanel implements Runnable {
	IplImage image;
	IplImage img;
	int width;
	int height;
	
	public GrabberShow(int w, int h) {
		width = w;
		height = h;
	}
	
	@Override
	public void run() {
		FrameGrabber grabber = new VideoInputFrameGrabber(2); // 1 for next camera
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
		g.drawImage(img.getBufferedImage(), 0, 0, width, height, null);
}
	
	
}


