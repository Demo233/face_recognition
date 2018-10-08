package com.opencv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import org.opencv.core.CvType;
import com.opencv.image.ImageViewer;
 
public class FaceDemo4 {
	
	public static void main(String[] args) {
		Mat source = opencv_imgcodecs.imread("E:\\photo\\face\\1\\1.jpg").clone();
		Mat target = new Mat();
		opencv_imgproc.cvtColor(source, target, opencv_imgproc.COLOR_BGR2GRAY);
		
		
		ImageViewer.show(target);
		Mat kernel = new Mat(0, 0, 0, 0, 5, 0, 0, 0, 0);
		opencv_imgproc.filter2D(target, target, CvType.CV_8UC3, kernel);
		ImageViewer.show(target);
		/*Mat[] faces = FaceUtil.getFacesMat(source);
		for (Mat face : faces) {
			ImageViewer.show(face);
		}*/
	}
}
