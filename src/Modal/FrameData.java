package Modal;

public class FrameData {
    private Double obj1;
    private Double obj2;
    private String obs;
    private Integer time;

    public FrameData(Double obj1, Double obj2, String obs, Integer time) throws Exception {

        if (obj1 == null && obj2 == null && obs == null && time == null) {
            throw new Exception("elemento nulo");
        }

        this.obj1 = obj1;
        this.obj2 = obj2;
        this.obs = obs;
        this.time = time;
    }

    public Double getObj1() {
        return obj1;
    }

    public void setObj1(Double obj1) {
        this.obj1 = obj1;
    }

    public Double getObj2() {
        return obj2;
    }

    public void setObj2(Double obj2) {
        this.obj2 = obj2;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
