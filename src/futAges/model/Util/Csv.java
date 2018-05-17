package futAges.model.Util;

import futAges.controller.DataController;
import futAges.model.Entity.FrameData;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Csv {

    private static StringBuilder builder;

    public Csv(){
        builder = new StringBuilder();
    }

    public static String converter(ArrayList<FrameData> dados){
        int countObj = dados.size();

        //ArrayList<String> arrayHeaders = new ArrayList<>(countObj+1);
        //arrayHeaders.add("Tempo");

        builder.append("Tempo;");

        TreeMap<Integer, String> linhas = new TreeMap<>();



        for(int id=0; id<countObj; id++){
            FrameData frameData = dados.get(id);

            String nome = frameData.getName();
            builder.append(nome + ";");
            //arrayHeaders.add(nome);

            Set keys = frameData.getKeys();


            int finalId = id;
            keys.forEach(k -> {
                if(finalId == 0){
                    String q = String.valueOf(frameData.getQuadrant((Integer) k));
                    String l = k+";"+q+";";
                    linhas.put((Integer) k, l);
                }
                else{
                    linhas.get(k).concat(frameData.getQuadrant((Integer) k)+";");
                }
            });

        }
        builder.append("\n");

        Set keys = linhas.keySet();
        keys.forEach(k -> {
            System.out.print(linhas.get(k)+"\n");
            builder.append(linhas.get(k)+"\n");
        });

        return builder.toString();
    }
}
