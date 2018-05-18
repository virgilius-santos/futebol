package futAges.model.Entity;
//-------------------------------------------------------

import java.io.File;
import java.util.ArrayList;
//-------------------------------------------------------

public class ProjectData {

    private String projetoPath;
    private String videoPath;
    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private final ArrayList<FrameData> dados;
    private String videoMD5;

    public ProjectData() {
        Integer stepDefault = 2;
        this.projetoPath = null;
        this.videoPath = null;
        this.tempoDivisao = stepDefault;
        this.linhas = 4;
        this.colunas = 8;

        this.dados = new ArrayList<>(10);
        FrameData obj = new FrameData();
        this.dados.add(obj);
        obj = new FrameData();
        this.dados.add(obj);
    }

    public ArrayList<FrameData> getDados() {
        return (ArrayList<FrameData>) dados.clone();
    }

    // Adds
    public int addData(FrameData data) {
        if (this.dados.size() >= 10) return -1;
        int index = this.dados.size();
        this.dados.add(data);
        return index;
    }

    // Sets
    public void setProjetoPath(File file) {
        if (file == null) return;
        this.projetoPath = file.getPath();
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setTempoDivisao(Integer tempoDivisao){
        this.tempoDivisao = tempoDivisao;
    }

    public void setLinhas(Integer linhas){
        this.linhas = linhas;
    }

    public void setColunas(Integer colunas){
        this.colunas = colunas;
    }

    public void setVideoMD5(String videoMD5) { this.videoMD5 = videoMD5; }

    // Gets
    public String getProjetoPath(){
        return projetoPath;
    }

    public Integer getLinhas(){
        return  linhas;
    }

    public Integer getColunas(){
        return  colunas;
    }

    public Integer getTempoDivisao() {
        return tempoDivisao;
    }

    public FrameData getData(Integer id) {
        return dados.get(id);
    }

    public int getDataSize() {
        return dados.size();
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getVideoMD5() { return videoMD5; }

}
