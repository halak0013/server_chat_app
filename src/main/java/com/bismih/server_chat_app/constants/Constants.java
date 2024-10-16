package com.bismih.server_chat_app.constants;

import java.awt.Color;

public class Constants {
    // private static Color color = new Color(250, 164, 1);
    public static String server_ip = "localhost";
    //public static String server_ip = "45.89.28.240";
    public static int server_port = 57145;
    public static int file_server_port = 14571;

    private static Color color = new Color(141, 190, 223);

    public static Color btMainColor() {
        return new Color(24, 24, 24);
    }

    public static Color btOverColor() {
        return new Color(52, 52, 52);
    }

    public static Color btClickColor() {
        return new Color(70, 70, 70);
    }

    public static Color borderColor() {
        return color;
    }

    public static Color hintColor() {
        return new Color(202, 202, 202);
    }

    public static Color txNormalColor() {
        return new Color(244, 244, 244);
    }

    public static Color txHintColor() {
        return new Color(244, 244, 244);
    }

    public static Color txDarkColor() {
        return new Color(24, 24, 24);
    }

    public static Color panelFocusGain() {
        return color;
    }

    public static Color panelNormalColor() {
        return new Color(24, 24, 24);
    }
}
