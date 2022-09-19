package com.grim.level.system;

import com.grim.database.DatabaseConnection;

import java.awt.*;

import static java.awt.Color.RGBtoHSB;

public class ColorPicker {

    public Color convertColor(String id){
        DatabaseConnection connection = new DatabaseConnection();
        return switch (connection.getColor(id)) {
            case "CYAN" -> Color.CYAN;
            case "DARK_GRAY" -> new Color(51,51,51);//
            case "GRAY" -> new Color(128,128,128);//
            case "PINK" -> new Color(255,133,190);//
            case "MAGENTA" -> new Color(255,77,190);//
            case "YELLOW" -> new Color(255,255,153);//
            case "RED" -> new Color(255,61,81);//
            case "GREEN" -> new Color(182,255,153);//
            case "BLUE" -> new Color(128,181,255);//
            default -> new Color(224,224,224); //
        };
    }

}
