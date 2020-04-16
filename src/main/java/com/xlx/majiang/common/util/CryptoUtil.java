package com.xlx.majiang.common.util;

import java.util.Base64;

/**
 * 加密
 *
 * @author xielx at 2020/4/11 21:29
 */
public class CryptoUtil {
    
    public static String encodeBase64(String str){
       return Base64.getEncoder().encodeToString(str.getBytes());
    }
    
    public static String decodeBase64(byte[] base64Data){
        return  new String(Base64.getDecoder().decode(base64Data));
    }
    
    public static byte[] encode(String src){
        return Base64.getEncoder().encode(src.getBytes());
    }
    public static byte[] decode(byte[] base64Data){
        return Base64.getDecoder().decode(base64Data);
    }
    
    
    
    public static void main(String[] args) {
        String key = "西红柿炒鸡蛋";
        System.out.println(encodeBase64(key));
        
    }
}
