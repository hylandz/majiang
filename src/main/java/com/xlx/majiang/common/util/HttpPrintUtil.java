package com.xlx.majiang.common.util;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.system.dto.oauth.GitHubAccessTokenDTO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @author xielx on 2019/8/5
 */
@Slf4j
public class HttpPrintUtil {
    
    
	
	
	
    /**
     * 输出
     *
     * @param response resp
     * @param object   obj
     */
    public static void httpOut(HttpServletResponse response, Object object) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.println(JSON.toJSONString(object));
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            log.error("response.getWriter()获取对象失败:[{}]", e.getMessage());
            throw new ClassCastException(e.getMessage());
        }
    }
    
    
    public static String sendGet(String api) throws IOException {
        // 创建连接对象
        URL url = new URL(api);
        URLConnection urlConnection = url.openConnection();
        // 设置参数和请求属性
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        connection.setRequestProperty("Charset", "UTF-8"); // 字符编码UTF-8
        connection.setRequestProperty("Connection", "Keep-Alive"); // 保持长连接
        connection.setRequestProperty("accept","application/json"); // 响应类型json
        // 建立连接
        connection.connect();
        log.info("响应码：{}",connection.getResponseCode());
        
        // 读取响应内容
        StringBuilder sb = new StringBuilder();
        String result = "";
        try (InputStream is = connection.getInputStream(); InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
        	while ((result = br.readLine()) != null){
        	    sb.append(result);
	        }
        }
        //
        connection.disconnect();
        return  sb.toString();
    }
	
    
    
    public static String sendPost(String api,String query) throws IOException {
        URL url = new URL(api);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
    
        connection.setRequestProperty("Charset", "UTF-8"); // 字符编码UTF-8
        connection.setRequestProperty("Connection", "Keep-Alive"); // 保持长连接
        connection.setRequestProperty("accept","application/json"); // 响应类型json
        connection.setRequestMethod("POST"); // 请求方法为post,默认GET
        connection.setDoInput(true); // 从 URL 连接读取数据,默认true
        connection.setDoOutput(true); // 数据写入 URL 连接,post请求要开启，默认false
        connection.setUseCaches(false);
        connection.connect();
        log.info("响应码：{}",connection.getResponseCode());
        StringBuilder sb = new StringBuilder();
        String content = "";
        try(PrintWriter pw = new PrintWriter(connection.getOutputStream())){
            pw.print(query);
            pw.flush();
        }
        
        try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8))){
            while ((content = br.readLine()) != null){
                sb.append(content);
            }
    
        }
        
        connection.disconnect();
        return sb.toString();
    }
	public static void main(String[] args) throws IOException {
		//String api = "https://tianqiapi.com/free/day?appid=92516954&appsecret=2DGSGTVr&city=济南";
		//sendGet(api);
		
		String postApi = " https://github.com/login/oauth/access_token";
        GitHubAccessTokenDTO token = new GitHubAccessTokenDTO("1245qq","e698304670d8ec4cd1f3","ZjBjY2FiMzMyYzBmNTZjYWMwZDVmMzdhYzgwYThhYjZiMjYyNzVkZQ==","","1");
        String post = sendPost(postApi, JSON.toJSONString(token));
        log.info("响应数据：{}",post);
    }
    
}
