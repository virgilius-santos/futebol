package futAges.controller;
//-------------------------------------------------------

import futAges.model.Entity.FrameData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
//-------------------------------------------------------

public class DataController {

    private String projetoPath;
    private String videoPath;
    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private Map<Integer, FrameData> dados;

    DataController() {
        Integer stepDefault = 2;
        this.projetoPath = null;
        this.videoPath = null;
        this.tempoDivisao = stepDefault;
        this.linhas = 4;
        this.colunas = 8;

        this.dados = new HashMap<>();
        FrameData obj = new FrameData();
        this.dados.put(obj.getId(),obj);
        obj = new FrameData();
        this.dados.put(obj.getId(), obj);
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

    void addData(FrameData data) {
        this.dados.put(data.getId(), data);
    }

    FrameData getData(Integer id) {
        return dados.get(id);
    }

    Map<Integer, FrameData> getDados() {
        return dados;
    }

    String getVideoPath() {
        return videoPath;
    }
}
