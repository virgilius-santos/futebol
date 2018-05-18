package futAges.model.Util;

import futAges.model.Entity.FrameData;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Csv {

    private static final String KEY_TEMPO = "Tempo;";
    private static final String KEY_SEMI_COLON = ";";
    private static final String KEY_BREAK_LINE = "\n";
    private static final String KEY_EMPTY = "";

    private Csv(){}

    public static String converter(List<FrameData> dados){
        StringBuilder builder = new StringBuilder();
        HashMap<Integer, Integer[]> linhas = new HashMap<>();
        int countObj = dados.size();

        FrameData frameData;
        String nome;

        builder.append(KEY_TEMPO);

        for(int id=0; id<countObj; id++){
            frameData = dados.get(id);
            nome = frameData.getName();
            builder.append(nome).append(KEY_SEMI_COLON);

            int finalId = id;
            frameData.getQuadrants().forEach((tempo,quadrante) -> {
                if (!linhas.containsKey(tempo)) {
                    linhas.put(tempo, new Integer[countObj]);
                }
                linhas.get(tempo)[finalId] = quadrante;
            });

        }
        builder.append(KEY_BREAK_LINE);

        linhas
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(set -> {

            builder.append(set.getKey()).append(KEY_SEMI_COLON);

            for (Integer celula : set.getValue()) {
                builder.append((celula == null) ? KEY_EMPTY : celula.toString());
                builder.append(KEY_SEMI_COLON);
            }

            builder.append(KEY_BREAK_LINE);

        });

        return builder.toString();
    }
}
