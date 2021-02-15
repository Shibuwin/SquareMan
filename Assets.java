package com.bsherwin;

import java.awt.image.BufferedImage;

public class Assets {
    private static final int width = 32, height =32;
    public static BufferedImage player1, player2, grass, sand;

    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/textures/sheet.png"));
        player1 = sheet.crop(0,0,width,height);
        player2 = sheet.crop(width,0,width,height);
        grass = sheet.crop(width * 2,0,width,height);
        sand = sheet.crop(width * 3,0,width,height);

    }
}
