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
}
