package futAges.model.Util;

import futAges.model.Entity.FrameData;

import java.util.HashMap;
import java.util.List;

public class Csv {

    private static StringBuilder builder;
    private static int countObj;
    private static HashMap<Integer, Integer[]> linhas;
    private static FrameData frameData;
    private static String nome;

    public static String converter(List<FrameData> dados){
        builder = new StringBuilder();
        linhas = new HashMap<>();
        countObj = dados.size();
        builder.append("Tempo;");

        for(int id=0; id<countObj; id++){
            frameData = dados.get(id);
            nome = frameData.getName();
            builder.append(nome).append(";");

            int finalId = id;
            frameData.getQuadrants().forEach((tempo,quadrante) -> {
                if (!linhas.containsKey(tempo)) {
                    linhas.put(tempo, new Integer[countObj]);
                }
                linhas.get(tempo)[finalId] = quadrante;
            });

        }

        builder.append("\n");

        linhas.forEach((k, v) -> {
            builder.append(k.toString()).append(";");
            for (int i=0; i<v.length; i++)
                builder.append( (v[i]==null) ? "" : v[i].toString()).append(";");
            builder.append("\n");
        });

        return builder.toString();
    }
}
