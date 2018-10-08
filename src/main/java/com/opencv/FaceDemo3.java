package com.opencv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgcodecs;

import com.opencv.image.ImageViewer;
import com.opencv.util.FaceUtil;
 
public class FaceDemo3 {
	
	public static void main(String[] args) {
		Mat source = opencv_imgcodecs.imread("E:\\photo\\face\\3.jpg").clone();
		Mat[] faces = FaceUtil.getFacesMat(source);
		for (Mat face : faces) {
			ImageViewer.show(face);
		}
	}
}
