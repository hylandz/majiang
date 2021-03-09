package com.xlx.majiang.system.provider;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * 网络请求
 *
 * @author xielx at 2021/3/9 10:47
 */
public class NetUtil {
    
    
    public static String sendGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        // GET
        final Request request = new Request.Builder()
                                        .url(url)
                                        .build();
        try(final Response response = client.newCall(request).execute()){
            final ResponseBody body = response.body();
            return body != null ? body.string() : "";
        }
    }
}
