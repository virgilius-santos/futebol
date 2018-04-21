package IO;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class IOFiles {

    public static String getPath(){
        FileChooser video = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select the video(*.mp4)","*.mp4");
        video.getExtensionFilters().add(filter);
        File file = video.showOpenDialog(null);
        String filePath = file.toURI().toString();
        return filePath;
    }

    public void save(String savePath, Object object){
            try {
                FileOutputStream fileOut = new FileOutputStream(savePath); // abre uma stream direcionada ao arquivo especifico em savePath acima

                ObjectOutputStream out = new ObjectOutputStream(fileOut); // abre uma stream de objeto, para serializar, o objeto e colocar neste arquivo
                out.writeObject(object); // escreve o objeto no arquivo alvo através do stream de objetos acima (ObjectOutputStream)

                out.close(); // fecha a stream de objeto
                fileOut.close(); // fecha a stream de arquivo.

                // É importante sempre fechar as streams após usa-las para evitar que dados sejam corrompidos

                System.out.printf("Data saved to \"" + savePath + "\""); // Verificar no console

            } catch (IOException i) {  // Caso dê algum outro erro referente aos streams de gravação em arquivo
                System.err.println(i.getMessage());
            }
    }
}
