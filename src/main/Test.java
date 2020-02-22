package main;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Test {
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	//static VideoCapture cap = new VideoCapture();
	static VideoCapture cap = new VideoCapture(0);
	static Imshow window = new Imshow("camera");
	static Mat mat = new Mat(640, 480, CvType.CV_8UC3);

	public static void main(String[] args) {
		if (cap.isOpened()) {
			System.out.println("Connected to Camera");
			while (true) {
				cap.read(mat);
				window.showImage(mat);
			}
		}
	}
}
