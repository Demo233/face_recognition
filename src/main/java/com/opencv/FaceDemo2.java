package com.opencv;

import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import com.opencv.util.FaceUtil;
import com.opencv.video.VideoPlay;
import com.opencv.video.VideoPlay.OnVideoPlay;

public class FaceDemo2 {
	
	public static void main(String[] args) throws Exception {
		VideoPlay.show("E:\\photo\\video\\1.AVI", new OnVideoPlay() {
			@Override
			public Mat mat(Mat mat) throws java.lang.Exception {
				return render(mat);
			}
		});
	}
	
	private static Mat render(Mat source){
		Rect[] faceRects = FaceUtil.getFaces(source);
		for(Rect rect : faceRects){
			System.out.println(rect);
			opencv_imgproc.rectangle(source, rect, new Scalar(0, 255, 0, 1), 5, 8, 0);
		}
		return source;
	}
}
