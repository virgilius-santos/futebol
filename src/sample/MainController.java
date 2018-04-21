package sample;
//-------------------------------------------------------
import com.sun.org.apache.xalan.internal.xsltc.dom.SingletonIterator;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.ArrayList;
//-------------------------------------------------------

public class MainController {
    private String nomeProjeto;
    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private ArrayList<Object> dados = new ArrayList<>();
    private Integer ContadorDeFrames = 0;

    public static MainController shared = new MainController(); //singleton

    private MainController() {

    }






    // Sets


    public void  SetAllMainController(String nome, Integer tempoDivisao, Integer linhas, Integer colunas) {
        this.nomeProjeto = nome;
        this.tempoDivisao = tempoDivisao;
        this.linhas = linhas;
        this.colunas = colunas;
        ContadorDeFrames ++;


    }

    public void setNomeProjeto(String nomeProjeto){
        this.nomeProjeto = nomeProjeto;
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
    public  Integer getColunas(){
        return  colunas;
    }

    public ArrayList<Object> getListData(){
        return dados;
    }

    public  Integer getContadorDeFrames(){
        return  ContadorDeFrames;
    }







}
