package futAges.model.Entity;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class FrameData {

    private static int ids = 0;

    private Integer id;
    private String name;
    private HashMap<Integer,Integer> quadrants;

    public FrameData() {
        this(null);
    }

    private FrameData(String name) {

        this.id = ids;
        ids++;
        this.name = name;
        quadrants = new HashMap<>();
    }

    public Integer getId() {
        return id;
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
        quadrants.put(tempo, quadrant);
    }

    public Set getKeys(){
        return quadrants.keySet();
    }
}
