package com.lblog.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaUtil {
    //默认宽度
    private static final Integer DEFAULT_WIDTH = 120;

    //默认高度
    private static final Integer DEFAULT_HEIGHT = 40;

    //验证码长度
    private static final Integer CODE_LENGTH = 4;

    //干扰线数量
    private static final Integer LINE_COUNT = 10;

    //噪点数量
    private static final Integer NOISE_COUNT = 50;

    //字体大小
    private static final Integer FONT_SIZE = 24;

    //字符池，排除数字1和0，字母I和O
    private static final String CHARACTER_SET = "23456789abcdefghjklmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    //随机数生成器实例
    private static final Random random = new Random();

    //生成验证码
    public static String generateCode(){
        StringBuilder code = new StringBuilder();
        for(int i=0; i<CODE_LENGTH; i++){
            code.append(CHARACTER_SET.charAt(random.nextInt(CHARACTER_SET.length())));
        }

        return code.toString();
    }

    //生成验证码图片
    public static BufferedImage generateImage(String code){
        BufferedImage image = new BufferedImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        //设置背景色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //绘制干扰线
        drawInterferenceLines(g2d, DEFAULT_WIDTH, DEFAULT_HEIGHT, LINE_COUNT);

        //绘制噪点
        drawNoise(image, DEFAULT_WIDTH, DEFAULT_HEIGHT, NOISE_COUNT);

        //绘制验证码文字
        drawCode(g2d, code, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        g2d.dispose();

        return image;
    }

    //干扰线
    private static void drawInterferenceLines(Graphics2D g2d, Integer width, Integer height, Integer lineCount){
        g2d.setColor(Color.GRAY);
        for(int i=0; i<lineCount; i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    //噪点
    private static void drawNoise(BufferedImage image, Integer width, Integer height, Integer noiseCount){
        for(int i=0; i<noiseCount; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            image.setRGB(x, y, Color.GRAY.getRGB());
        }
    }

    //验证码文字
    private static void drawCode(Graphics2D g2d, String code, Integer width, Integer height){
        Integer codeLength = code.length();
        Integer charWidth = width / (codeLength + 1);

        Font FONT = new Font("Arial", Font.BOLD, FONT_SIZE);
        g2d.setFont(FONT);
        g2d.setColor(Color.BLACK);

        for(int i=0; i<codeLength; i++){
            int x = (charWidth * i) + (charWidth / 3) + random.nextInt(5);
            int y = (height / 2) + (FONT_SIZE / 3) + random.nextInt(5) - 2;

            String str = String.valueOf(code.charAt(i));
            g2d.drawString(str, x, y);
        }
    }

    //将图片转换为Base64
    public static String imageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(bytes);
    }

    //验证
    public static Boolean validateCode(String inputCode, String expectedCode){
        if(StringUtils.isBlank(inputCode) || StringUtils.isBlank(expectedCode)){
            return false;
        }

        return inputCode.trim().equalsIgnoreCase(expectedCode.trim());
    }
}
