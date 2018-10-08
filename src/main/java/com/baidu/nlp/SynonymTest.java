package com.baidu.nlp;

import org.json.JSONObject;
import com.baidu.Config;
import com.baidu.aip.nlp.AipNlp;

public class SynonymTest {
	
	public static void main(String[] args) {
		AipNlp aipNlp = new AipNlp(Config.APPID, Config.APIKEY, Config.SECRETKEY);
		aipNlp.setHttpProxy("ssfirewall", 8080);
		JSONObject data= aipNlp.simnet("判断两个文本的相似度得分", "判断两句话是不是相似", null);
		System.out.println(data);
	}
}
