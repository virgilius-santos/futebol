package Main;
//-------------------------------------------------------

import futAges.modal.Entity.FrameData;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
//-------------------------------------------------------

public class DataController {

    private Integer stepDefault;

    private String nomeProjeto;
    private String videoPath;
    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private Map<Integer, FrameData> dados;

    public DataController() {
        this.stepDefault = 2;
        this.nomeProjeto = "default";
        this.videoPath = null;
        this.tempoDivisao = this.stepDefault;
        this.linhas = 4;
        this.colunas = 8;
        this.dados = new HashMap();
    }

    // Sets
    public void setNomeProjeto(File nomeProjeto) {
        if (nomeProjeto == null)return ;
        this.nomeProjeto = nomeProjeto.getName();
    }
    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }
    public void setTempo(Integer tempoDivisao){
        this.tempoDivisao = tempoDivisao;
    }
    public void setLinhas(Integer linhas){
        this.linhas = linhas;
    }
    public void setColunas(Integer colunas){
        this.colunas = colunas;
    }

    // Gets
    public String getNomeProjeto(){
        return nomeProjeto;
    }
    public Integer getLinhas(){
        return  linhas;
    }
    public Integer getColunas(){
        return  colunas;
    }
    public Integer getTempoDivisao() { return tempoDivisao; }

    public void addData(FrameData data) {
        this.dados.put(data.getId(), data);
    }

    public FrameData getData(Integer id) { return dados.get(id); }

    public  Map<Integer, FrameData> getDados() {
        return dados;
    }

    public String getVideoPath() { return videoPath; }
}
