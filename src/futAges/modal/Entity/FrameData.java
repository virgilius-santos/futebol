package futAges.modal.Entity;

import java.util.HashMap;
import java.util.Map;

public class FrameData {
    private Integer id;
    private String name;
    private Map<Integer, Integer> quadrants;

    public FrameData(Integer id, String name) {

        this.id = id;
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
