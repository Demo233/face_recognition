package com.opencv.image;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.bytedeco.javacpp.opencv_imgproc;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageViewer {
    private JLabel imageView;
    private Mat image;
    private String windowName;
    
    public static void show(String image){
    	new ImageViewer(image).show();
    }
    
    public static void show(String image,String windowName){
    	new ImageViewer(image, windowName).show();
    }
    
    public static void show(Mat image){
    	new ImageViewer(image).show();
    }
    
    public static void show(Mat image,String windowName){
    	new ImageViewer(image, windowName).show();
    }

    private ImageViewer(String imgpath) {
    	this(opencv_imgcodecs.imread(imgpath), "ImageView");
    }
    
    private ImageViewer(String imagePath, String windowName) {
    	this(opencv_imgcodecs.imread(imagePath), windowName);
    }
    
    private ImageViewer(Mat image) {
    	this(image, "ImageView");
    }

    private ImageViewer(Mat image, String windowName) {
        this.image = resize(image);
        this.windowName = windowName;
    }
    
    private void show() {
        setSystemLookAndFeel();
        Image loadedImage = toBufferedImage(image);
        JFrame frame = createJFrame(windowName, image.size().width(), image.size().height());
        imageView.setIcon(new ImageIcon(loadedImage));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 用户点击窗口关闭
    }

    private void setSystemLookAndFeel(){
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    private JFrame createJFrame(String windowName, int width, int height) {
        JFrame frame = new JFrame(windowName);
        imageView = new JLabel();
        final JScrollPane imageScrollPane = new JScrollPane(imageView);
        imageScrollPane.setPreferredSize(new Dimension(width, height));
        frame.add(imageScrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return frame;
    }

    private static Mat resize(Mat src) {
    	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    	double scale_w = src.size().width() / screensize.getWidth();
    	double scale_h = src.size().height() / screensize.getHeight();
    	double scale = scale_w > scale_h ? scale_w : scale_h;
    	
    	Mat dst = new Mat();
    	opencv_imgproc.resize(src, dst, 
    			new Size(
    					(int)(src.size().width() / scale) - 100, 
    					(int)(src.size().height() / scale) - 100
    			)
    		);
    	return dst;
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