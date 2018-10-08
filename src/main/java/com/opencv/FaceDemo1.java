package com.opencv;

import java.io.File;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import com.opencv.image.ImageViewer;
import com.opencv.util.FaceUtil;
import com.opencv.util.FileUtil;

public class FaceDemo1 {
	
	private static Mat render(Mat source){
		Rect[] faceRects = FaceUtil.getFaces(source);
		for(Rect rect : faceRects){
			opencv_imgproc.rectangle(source, rect, new Scalar(0, 255, 0, 1), 5, 8, 0);
		}
		Rect[] eyesRects = FaceUtil.getEyes(source);
		for(Rect rect : eyesRects){
			opencv_imgproc.rectangle(source, rect, new Scalar(0, 255, 0, 1), 5, 8, 0);
		}
		return source;
	}
	
	private static void render(String source){
		File sourceFile = new File(source);
		if(!sourceFile.exists() || !sourceFile.isFile()){ 
			throw new RuntimeException("must be a image file"); 
		}
		Mat dist = opencv_imgcodecs.imread(source).clone();
		dist = render(dist);
		opencv_imgcodecs.imwrite(sourceFile.getParent() + "/" + FileUtil.targetName(sourceFile, "target") , dist);
		ImageViewer.show(dist);
	}
	
	public static void main(String[] args) throws Exception {
		render("E:\\photo\\face\\3.jpg");
	}
}
