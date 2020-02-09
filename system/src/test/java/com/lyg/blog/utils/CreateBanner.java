package com.lyg.blog.utils;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by winggonLee on 2020/2/9
 */
public class CreateBanner {

    public static void main(String[] args) {
        String path = "C:\\Users\\winggon\\Pictures\\ImageFromNetwook\\村田莲尔\\range_murata_353.jpg";
        String outPath = "D:\\Code\\blog\\system\\src\\main\\resources\\banner.txt";
        // 输出行数
        int row = 80;
        // 字符串由复杂到简单
        String base = "#8XOHLTI)i=+;:,. ";

        try {
            final BufferedWriter fos = new BufferedWriter(new FileWriter(outPath,false));   //true表示是否追加
            BufferedImage image = ImageIO.read(new File(path));  //读取图片
            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();

            int pace = imageHeight / row;
            int x=0,y=0,yy=0;
            while ((y += pace) < imageHeight) {
                if (yy++%2==0)continue;
                String colour = "";
                while ((x += pace) < imageWidth) {
                    int pixel = image.getRGB(x, y);
                    int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                    float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                    int index = Math.round(gray * (base.length() + 1) / 255);
                    String s = index >= base.length() ? " " : String.valueOf(base.charAt(index));
                    if (!" ".equals(s)) {
                        String newColour = getColour(r, g, b);
                        if (!colour.equals(newColour)) {
                            colour = newColour;
                            String colourStr = "${AnsiColor." + colour + '}';
                            fos.write(colourStr);
                        }
                    }
                    System.out.print(s);
                    fos.write(s);
                }
                x=0;
                System.out.println();
                fos.newLine();
            }
            String versionInfo = "${AnsiColor.YELLOW}Spring Boot Version: ${spring-boot.version}${spring-boot.formatted-version}";
            System.out.println(versionInfo);
            fos.write(versionInfo);
            fos.newLine();
            fos.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static String getColour(int r, int g, int b) {
        String colour;
        if (r > g && r > b) {
            colour = "RED";
        } else if (g > r && g > b) {
            colour = "GREEN";
        } else if (b > r && b > g) {
            colour = "BLUE";
        } else {
            colour = "BLACK";
        }
        return colour;
    }
}