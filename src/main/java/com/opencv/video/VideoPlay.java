package com.opencv.video;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
 
public class VideoPlay {
	private JLabel imageView;
	private OnVideoPlay play;
	
	public interface OnVideoPlay{
		Mat mat(Mat mat) throws Exception;
	}
	
	public static void show(String videoPath, OnVideoPlay play) throws Exception {
		new VideoPlay(videoPath, play);
	}
 
	private VideoPlay(String videoPath, OnVideoPlay play) throws Exception {
		this.play = play;
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
		grabber.start();
		createJFrame("Video", grabber.getImageWidth(), grabber.getImageHeight());
		Frame frame = null;
        do {
        	frame = grabber.grabImage();
        	play(frame);
		} while (frame != null && frame.image != null);
        grabber.release();
        grabber.close();
	}
	
	private void play(Frame frame) throws Exception {
		ToMat convert = new ToMat();
    	Mat mat = convert.convert(frame);
    	if(mat != null && !mat.empty()){
    		if(play != null){
    			mat = play.mat(mat);
    		}
    		Image b = toBufferedImage(mat);
    		imageView.setIcon(new ImageIcon(b));
    	}
	}
	
    private void setSystemLookAndFeel() {
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    private void createJFrame(String windowName, int width, int height){
    	setSystemLookAndFeel();
        JFrame frame = new JFrame(windowName);
        imageView = new JLabel();
        final JScrollPane imageScrollPane = new JScrollPane(imageView);
        imageScrollPane.setPreferredSize(new Dimension(width, height));
        frame.add(imageScrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 用户点击窗口关闭
    }
 
    private Image toBufferedImage(Mat source) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (source.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = source.channels() * source.cols() * source.rows();
        byte[] buffer = new byte[bufferSize];
        source.arrayData().get(buffer);// 获取所有的像素点
        BufferedImage image = new BufferedImage(source.cols(), source.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }
}
