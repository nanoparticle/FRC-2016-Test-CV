package main;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class Main {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	//static VideoCapture cap = new VideoCapture();
	static VideoCapture cap = new VideoCapture(0);
	static Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3));
	static Mat mat = new Mat(640, 480, CvType.CV_8UC3);
	static Mat hsv = new Mat(640, 480, CvType.CV_8UC3);
	static Mat img1 = new Mat(640, 480, CvType.CV_8UC3);
	static ArrayList<RotatedRect> targets = new ArrayList<RotatedRect>();
	static ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	static Imshow window = new Imshow("camera");

	public static void main(String[] args) {
		//cap.open("http://10.20.85.11/mjpg/video.mjpg");
		if (cap.isOpened()) {
			System.out.println("Connected to Camera");
			//flushCamera();
			while (true) {
				targets = new ArrayList<RotatedRect>();
				contours = new ArrayList<MatOfPoint>();
				cap.read(mat);

				Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);

				//Core.inRange(hsv, new Scalar(80, 150, 150), new Scalar(95, 255, 255), img1);
				Core.inRange(hsv, new Scalar(80, 150, 150), new Scalar(95, 255, 255), img1);

				Imgproc.morphologyEx(img1, img1, Imgproc.MORPH_OPEN, kernel);

				Imgproc.findContours(img1.clone(), contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

				for (int i=0; i < contours.size(); i++) {
					Point[] points = new Point[4];
					MatOfPoint2f temp = new MatOfPoint2f();
					contours.get(i).convertTo(temp, CvType.CV_32FC2);
					RotatedRect rect = Imgproc.minAreaRect(temp);
					rect.points(points);
					if (rect.size.area() > 150) {
						Imgproc.line(mat, points[0], points[1], new Scalar(0, 0, 255), 3);
						Imgproc.line(mat, points[1], points[2], new Scalar(0, 0, 255), 3);
						Imgproc.line(mat, points[2], points[3], new Scalar(0, 0, 255), 3);
						Imgproc.line(mat, points[3], points[0], new Scalar(0, 0, 255), 3);
						Imgproc.circle(mat, rect.center, 3, new Scalar(255, 0, 0), 3);
						targets.add(rect);
					}
				}

				for (RotatedRect r : targets) {
					System.out.print(r.center + "  ");
				}
				System.out.println();
				window.showImage(mat);
			}
		} else {
			System.out.println("Connection Failed.");
		}
	}
}