package Modal;

public class FrameData {
    private Integer obj1;
    private Integer obj2;
    private Integer time;

    public FrameData(Integer obj1, Integer obj2, Integer time) throws Exception {

        if (obj1 == null && obj2 == null && time == null) {
            throw new Exception("elemento nulo");
        }

        this.obj1 = obj1;
        this.obj2 = obj2;
        this.time = time;
    }

    public Integer getObj1() {
        return obj1;
    }

    public void setObj1(Integer obj1) {
        this.obj1 = obj1;
    }

    public Integer getObj2() {
        return obj2;
    }

    public void setObj2(Integer obj2) {
        this.obj2 = obj2;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

}
