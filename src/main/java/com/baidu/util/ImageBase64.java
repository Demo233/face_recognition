package com.baidu.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
 
public class ImageBase64 {
	final static Base64.Decoder decoder = Base64.getDecoder();
	final static Base64.Encoder encoder = Base64.getEncoder();
	
	public static String getBase64ImageFromUrl(String imgURL) throws IOException {
		URL url = new URL(imgURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream();
		byte[] data = new byte[inStream.available()];
		inStream.read(data);
		inStream.close();
		return encoder.encodeToString(data);
	}
 
	public static String getBase64ImageFromPath(String imgPath) throws IOException {
		InputStream in = new FileInputStream(imgPath);
		byte[] data = new byte[in.available()];
		in.read(data);
		in.close();
		return encoder.encodeToString(data);
	}
}
