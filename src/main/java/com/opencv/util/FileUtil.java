package com.opencv.util;

import java.io.File;

public class FileUtil {
	
	public static String targetName(File file,String target){
		String name = file.getName().substring(0, file.getName().lastIndexOf(".") + 1);
		String suffix = file.getName().substring(name.length() - 1);
		return name + target + suffix;
	}
	
	public static String getHome() {
		return FileUtil.class.getResource("/").getPath().substring(1);
	}
	
	public static void main(String[] args) {
		System.out.println(getHome());
	}
}
