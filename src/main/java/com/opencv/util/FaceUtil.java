package com.opencv.util;

import java.util.ArrayList;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_imgproc;

public class FaceUtil {
	
	static String faceXML = FileUtil.getHome() + "lbpcascades/lbpcascade_frontalface.xml";
	static String eyesXML = FileUtil.getHome() + "haarcascades/haarcascade_eye_tree_eyeglasses.xml";
	static opencv_objdetect.CascadeClassifier face = new opencv_objdetect.CascadeClassifier(faceXML);
	static opencv_objdetect.CascadeClassifier eyes = new opencv_objdetect.CascadeClassifier(eyesXML);
	
	public static Rect[] getFaces(Mat source) {
		Mat target = new Mat();
		opencv_imgproc.cvtColor(source, target, opencv_imgproc.COLOR_BGR2GRAY);
		RectVector faces = new RectVector();
		face.detectMultiScale(target, faces);
		Rect[] rects = faces.get();
		ArrayList<Rect> list = new ArrayList<Rect>();
		for (int i = 0; i < rects.length; i++) {
			Rect rect = rects[i];
			Rect[] eyes = getEyes(new Mat(target, rect));
			if(eyes.length != 0){
				list.add(rect);
			}
		}
		return list.toArray(new Rect[list.size()]);
	}
	
	public static Rect[] getEyes(Mat source){
		RectVector faces = new RectVector();
		eyes.detectMultiScale(source, faces);
		return faces.get();
	}
	
	public static Mat[] getFacesMat(Mat source){
		Rect[] rects = getFaces(source);
		Mat[] faces = new Mat[rects.length];
		for (int i = 0; i < rects.length; i++) {
			faces[i] = new Mat(source, rects[i]);
		}
		return faces;
	}
}
