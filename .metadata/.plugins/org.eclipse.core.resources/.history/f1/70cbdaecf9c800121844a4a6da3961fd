import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.VideoInputFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import javax.swing.*;

public class GrabberShow implements Runnable {
	//final int INTERVAL=1000;///you may use interval
	IplImage image;
	JPanel panel;
	CanvasFrame canvas = new CanvasFrame("Web Cam");
	
	public GrabberShow(JPanel p) {
		panel = p;
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void run() {
		FrameGrabber grabber = new VideoInputFrameGrabber(3); // 1 for next camera
		int i=0;
		try {
			grabber.start();
			IplImage img;
canvas.add(panel);
			while (true) {
				img = grabber.grab();
				if (img != null) {
					cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
							// show image on window
					canvas.showImage(img);
				}
				//Thread.sleep(INTERVAL);
			}
		} catch (Exception e) {
		}
	}
}


