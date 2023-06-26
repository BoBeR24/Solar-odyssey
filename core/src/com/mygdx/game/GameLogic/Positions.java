package com.mygdx.game.GameLogic;
import com.mygdx.game.Objects.*;

import java.util.HashMap;

public class Positions {
    private HashMap<String, Vector > positions;
    private static Positions instance;
    
    private Positions(){
        this.positions = new HashMap<>();
    }

    public static void addPosition(String key, Vector value){
        if (Positions.instance == null){
            instance = new Positions();
        }
        instance.positions.put(key, value);
    }

    public static synchronized Positions getInstance(){
        if(instance == null){
            instance = new Positions();
        }
        return instance;
    }

    public Vector getPosition(String week) {
        return positions.get(week);
    }
}
