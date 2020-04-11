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
    
    public static String decodeBase64(byte[] encode){
        return  new String(Base64.getDecoder().decode(encode));
    }
    
    
    
    public static void main(String[] args) {
        String qq = "d3bf18f0a6d5985483d15ff8ca3e8c95";
        String github = "f0ccab332c0f56cac0d5f37ac80a8ab6b26275de";
        String gitee = "70bfbe7ba190b4064baaff8280240ae47364b4effd46ffeb3e3c546d63768c2e";
        String h2 = "123";
        String mail= "snwymjpmntmtjeic";
        System.out.println(encodeBase64(mail));
        
    }
}
