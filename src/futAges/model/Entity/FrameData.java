package futAges.model.Entity;

import java.util.HashMap;
import java.util.Map;

public class FrameData {

    private static int ids = 0;

    private Integer id;
    private String name;
    private Map<Integer, Integer> quadrants;

    public FrameData() {
        this(null);
    }

    public FrameData(String name) {

        this.id = ids;
        ids++;
        this.name = name;
        quadrants = new HashMap();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuadrant(Integer time) {
        return quadrants.get(time);
    }

    public void setQuadrant(Integer time, Integer quadrant) {
        quadrants.put(time, quadrant);
    }
}
