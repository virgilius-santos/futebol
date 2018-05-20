package model.Entity;

import java.util.HashMap;

public class FrameData {

    private static int ids = 0;

    private final Integer id;
    private String name;
    private final HashMap<Integer,Integer> quadrants;

    public FrameData() {

        this.id = ids;
        ids++;
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

    public HashMap<Integer,Integer> getQuadrants(){
        return (HashMap<Integer, Integer>) quadrants.clone();
    }
}
