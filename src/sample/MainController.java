package sample;
//-------------------------------------------------------
import java.util.ArrayList;
//-------------------------------------------------------

public class MainController {
    private String nomeProjeto;
    private Integer tempoDivisao;
    private Integer linhas, colunas; // Variáveis que formam os quadrantes.
    private ArrayList<Object> dados;

    //private static Singleton uniqueInstance = new Singleton(); // Class deve ser implementada.


    public MainController(String nome, Integer tempoDivisao, Integer linhas, Integer colunas) {
        this.nomeProjeto = nome;
        this.tempoDivisao = tempoDivisao;
        this.linhas = linhas;
        this.colunas = colunas;

        dados = new ArrayList<Object>();

    }


    // Permite criar um objeto único para que tenha apenas uma instância.
    /*public static Singleton getInstance() {
        return uniqueInstance;
    }*/

    //Tranformar class singleton.
    //Faz com que a classe tenha um só objeto dala no sistema.





}
