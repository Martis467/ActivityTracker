package com.utilities;


import java.awt.*;

public class HexColorPicker {

    /**
     * Returns random HSB color in RGB format
     * @return
     */
    public static int getRandomColor(){
        Color cl = new Color(Color.HSBtoRGB((float) Math.random(), (float) Math.random(), 0.5F + ((float) Math.random())/2F));
        return cl.getRGB();
    }
}
