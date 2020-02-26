package com.xlx.majiang.common.validate.image;

import com.xlx.majiang.common.validate.ValidateGenerator;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片验证码生成器
 *
 * @author xielx at 2020/2/11 16:48
 */
public class ImageCodeGenerator implements ValidateGenerator {
    
    
    @Override
    public ImageCode generate(ServletWebRequest servletWebRequest) {
        int width = 85;
        int height = 32;
        BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        // 画笔
        Graphics g = bufferedImage.getGraphics();
        Random random = new Random();
        // 设置颜色,,形状,字体,坐标轴/图片大小
        g.setColor(getRandomColor(200,250));
        g.fillRect(0,0,width,height);
        g.setFont(new Font("Times New Roman",Font.ITALIC,20));
        // 画背景条纹
        g.setColor(getRandomColor(160,200));
        for (int i = 0;i < 155;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x,y,x + x1,y + y1);
        }
        
        // 验证码字符
        String strCode = "";
        for (int i = 0; i < 4; i++) {
            String str = String.valueOf(random.nextInt(10));
            strCode += str;
            g.setColor(new Color(20 + random.nextInt(110),20 + random.nextInt(110),20 + random.nextInt(110)));
            g.drawString(str,13 * i + 6,16);
        }
        
        // 关闭画笔
        g.dispose();
        
        return new ImageCode(bufferedImage,strCode,60);
    }
    
    /**
     * 生成随机颜色
     * @param frontColor 前景色
     * @param backColor 背景色
     * @return Color
     */
    private Color getRandomColor(int frontColor,int backColor) {
        Random random = new Random();
        /*if (frontColor > 255){
            frontColor = 255;
        }
        if (backColor > 255){
            backColor = 255;
        }*/
        // 判断背景颜色数值范围
        frontColor = Math.min(frontColor, 255);
        backColor = Math.min(backColor,255);
        
        // R,G,B的随机值,注意bound参数的要为positive
        int r = frontColor + random.nextInt(backColor - frontColor);
        int g = frontColor + random.nextInt(backColor - frontColor);
        int b = frontColor + random.nextInt(backColor - frontColor);
        
        return new Color(r,g,b);
    }
    
}
