package model.entity;
//-------------------------------------------------------

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
//-------------------------------------------------------

public class ProjectData {

    private String videoMD5;
    private URI videoURI;
    private File projetoFile;

    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private final ArrayList<FrameData> dados;

    public ProjectData(File videoFile) {
        Integer stepDefault = 2;
        this.projetoFile = null;
        this.videoURI = videoFile.toURI();
        this.tempoDivisao = stepDefault;
        this.linhas = 4;
        this.colunas = 8;

        this.dados = new ArrayList<>(10);
        this.dados.add(new FrameData());
        this.dados.add(new FrameData());
    }

    public List<FrameData> getDados() {
        return (List<FrameData>) dados.clone();
    }

    // Adds
    public int addData(FrameData data) {
        if (this.dados.size() >= 10) return -1;
        int index = this.dados.size();
        this.dados.add(data);
        return index;
    }

    // Sets
    public void setProjetoFile(File file) {
        this.projetoFile = file;
    }

    public void setVideoFile(File videoFile) {
        this.videoURI = videoFile.toURI();
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
    public File getProjetoFile(){
        return projetoFile;
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

    public File getVideoFile() {
        return new File(videoURI);
    }

    public String getVideoURI() {
        return videoURI.toString();
    }

    public String getVideoMD5() { return videoMD5; }

}