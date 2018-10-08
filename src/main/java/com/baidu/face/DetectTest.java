package com.baidu.face;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import com.baidu.Config;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.util.ImageBase64;

public class DetectTest {
	

	
	public static void detect() throws IOException {
		AipFace aipFace = new AipFace(Config.APPID, Config.APIKEY, Config.SECRETKEY);
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("face_field", "age");
		options.put("max_face_num", "2");
		options.put("face_type", "LIVE");

		//aipFace.setHttpProxy("ssfirewall", 8080);
		JSONObject data = aipFace.detect(ImageBase64.getBase64ImageFromPath("D:\\3.jpg"), "BASE64", options);
		System.out.println(data);
	}
	
	public static void match() throws IOException {
		AipFace aipFace = new AipFace(Config.APPID, Config.APIKEY, Config.SECRETKEY);
		//aipFace.setHttpProxy("ssfirewall", 8080);
		ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
		requests.add(new MatchRequest(ImageBase64.getBase64ImageFromPath("D:\\1.png"), "BASE64"));
		requests.add(new MatchRequest(ImageBase64.getBase64ImageFromPath("D:\\2.png"), "BASE64"));
		JSONObject data = aipFace.match(requests);
		System.out.println(data.get("result"));
		JSONObject result = (JSONObject) data.get("result");
		System.out.println(result.get("score"));
		System.out.println(data.get("log_id"));
		System.out.println(data.get("error_msg"));
		System.out.println(data);
	}

	/**
	 * 注册
	 */
	public static void add()throws IOException {
		AipFace aipFace = new AipFace(Config.APPID, Config.APIKEY, Config.SECRETKEY);
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", "user's info");
		options.put("quality_control", "NONE");
		options.put("liveness_control", "NONE");
		String image = ImageBase64.getBase64ImageFromPath("D:\\7.jpg");
		String imageType = "BASE64";
		String groupId = "group1";
		String userId = "cuifei";

		JSONObject res = aipFace.addUser(image, imageType, groupId, userId, options);
		System.out.println(res.toString(2));
	}

	/**
	 * 更新
	 */
	public static void update()throws IOException {
		AipFace aipFace = new AipFace(Config.APPID, Config.APIKEY, Config.SECRETKEY);
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", "user's info");
		options.put("quality_control", "NONE");
		options.put("liveness_control", "NONE");

		String image = ImageBase64.getBase64ImageFromPath("D:\\7.jpg");
		String imageType = "BASE64";
		String groupId = "group1";
		String userId = "cuifei";

		JSONObject res = aipFace.updateUser(image,imageType,groupId,userId,options);
		System.out.println(res.toString(2));
	}

	/**
	 * 删除
	 */
	public static void delete()throws IOException {
		AipFace aipFace = new AipFace(Config.APPID, Config.APIKEY, Config.SECRETKEY);
		HashMap<String, String> options = new HashMap<String, String>();

		String groupId = "group1";
		String userId = "cuifei";

		JSONObject res = aipFace.deleteUser(groupId,userId,options);
		System.out.println(res.toString(2));
	}

	/**
	 * 查询
	 */
	public static void getUser()throws IOException {
		AipFace aipFace = new AipFace(Config.APPID, Config.APIKEY, Config.SECRETKEY);
		HashMap<String, String> options = new HashMap<String, String>();

		String groupId = "group1";
		String userId = "user1";

		JSONObject res = aipFace.getUser(userId,groupId,options);
		System.out.println(res.toString(2));
	}


	public static void main(String[] args) throws IOException {
		match();
	}

}
