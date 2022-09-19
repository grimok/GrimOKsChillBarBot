package com.grim.room;

import java.util.HashMap;

public class ActiveRooms {

    public static HashMap<String,String> activeRooms = new HashMap<>();

    public static HashMap<String,String> getActiveRooms() {
        return activeRooms;
    }

    public static void setActiveRooms(HashMap<String,String> activeRooms) {
        ActiveRooms.activeRooms = activeRooms;
    }

    public static void addRoom(String author,String ID){
        activeRooms.put(author,ID);
    }
}
