package futAges.controller;
//-------------------------------------------------------

import futAges.model.Entity.FrameData;
import futAges.model.Util.MD5;

import java.io.File;
import java.util.ArrayList;
//-------------------------------------------------------

public class DataController {

    private String projetoPath;
    private String videoPath;
    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private ArrayList<FrameData> dados;
    private String videoMD5;

    DataController() {
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

    // Adds
    int addData(FrameData data) {
        if (this.dados.size() >= 10) return -1;
        int index = this.dados.size();
        this.dados.add(data);
        return index;
    }

    // Sets
    void setProjetoPath(File file) {
        if (file == null) return;
        this.projetoPath = file.getPath();
    }

    void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    void setTempoDivisao(Integer tempoDivisao){
        this.tempoDivisao = tempoDivisao;
    }

    void setLinhas(Integer linhas){
        this.linhas = linhas;
    }

    void setColunas(Integer colunas){
        this.colunas = colunas;
    }

    void setVideoMD5(String videoMD5) { this.videoMD5 = videoMD5; }

    // Gets
    String getProjetoPath(){
        return projetoPath;
    }

    Integer getLinhas(){
        return  linhas;
    }

    Integer getColunas(){
        return  colunas;
    }

    Integer getTempoDivisao() {
        return tempoDivisao;
    }

    FrameData getData(Integer id) {
        return dados.get(id);
    }

    int getDataSize() {
        return dados.size();
    }

    String getVideoPath() {
        return videoPath;
    }

    String getVideoMD5() { return videoMD5; }

}
