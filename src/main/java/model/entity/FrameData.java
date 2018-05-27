package model.entity;

import java.util.HashMap;
import java.util.Map;

public class FrameData {

    private String name;
    private final HashMap<Integer,Integer> quadrants;

    public FrameData() {
        this.name = null;
        quadrants = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuadrant(Integer tempo) {
        return quadrants.get(tempo);
    }

    public void setQuadrant(Integer tempo, Integer quadrant) {
        if (quadrant == null && quadrants.containsKey(tempo)) {
            quadrants.remove(tempo);
        } else if (quadrant != null) {
            quadrants.put(tempo, quadrant);
        }
    }

    public Map<Integer,Integer> getQuadrants(){
        return (Map<Integer, Integer>) quadrants.clone();
    }
}
