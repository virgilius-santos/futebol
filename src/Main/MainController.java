package Main;
//-------------------------------------------------------

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
//-------------------------------------------------------

public class MainController {

    private Integer stepDefault;

    private String nomeProjeto;
    private String videoPath;
    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private Map<Integer,Object> dados;

    public static MainController shared = new MainController(); //singleton

    private MainController() {
        this.stepDefault = 2;
        this.nomeProjeto = "default";
        this.videoPath = null;
        this.tempoDivisao = this.stepDefault;
        this.linhas = null;
        this.colunas = null;
        this.dados = new HashMap();
    }

    // Sets
    public void setNomeProjeto(String nomeProjeto){
        this.nomeProjeto = nomeProjeto;
    }
    public void setVideoPath(String videoPath){
        this.videoPath = videoPath;
    }
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

    public Integer getTempoDivisao() {
        return tempoDivisao;
    }

    public void addData(Object data, Integer timeInSeconds){
        this.dados.put(timeInSeconds, data);
    }

    public Object getData(Integer timeInSeconds){ return dados.get(timeInSeconds); }





}
