package cn.edu.hstc.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.hstc.food.client.exception.GetJsonDataException;


public class URLTool {

	public static String getJsonData(String urlPath, int connectTimeout,
			String method) throws Exception  {
		if (urlPath != null && !"".equals(urlPath.trim()) && connectTimeout > 0) {
			if (method == null || "".equals(method.trim())) {
				method = "GET";
			}
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(connectTimeout);
			connection.setRequestMethod(method);
			if (connection.getResponseCode() == 200) {
				InputStream in = connection.getInputStream();
				String content = new String(StreamTool.read(in));
				return content;
				
			}else{
				throw new GetJsonDataException("获取json数据失败。");
			}
		}else {
			throw new GetJsonDataException("获取json数据失败。");
		}
		
	}
}
