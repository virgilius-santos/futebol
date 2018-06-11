package model.io;

import model.entity.ProjectData;
import model.util.Conversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class IOFilesTest {

    private ProjectData projectData;

    @org.junit.Before
    public void setUp() throws Exception {
        File file = new File("VideoA.mp4");
        projectData = new ProjectData(file);
        projectData.setVideoMD5(Conversion.getMD5Checksum(file));
    }

    @org.junit.Test
    public void saveJsonFile() throws Exception {
        String pathname = "teste.json";
        File teste = new File(pathname);
        projectData.setProjetoFile(teste);
        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
        Path path = Paths.get(pathname);
        String actual = "";
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                actual += line;
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        String expected = "{\"videoMD5\":\"a0e9d74b6ed985e3cfaa42df337d7f36\",\"videoURI\":\"file:/home/16200797/IdeaProjects/futebol/VideoA.mp4\"," +
                "\"projetoFile\":{\"path\":\"teste.json\"},\"tempoDivisao\":2,\"linhas\":4,\"colunas\":8,\"dados\":[{\"quadrants\":{}},{\"quadrants\":{}}]}";
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void saveCsvFile(){
        String pathname = "teste.csv";
        File teste = new File(pathname);
        projectData.setProjetoFile(teste);
        String csv = Conversion.converter(projectData.getDados());
        IOFiles.saveCsvFile(projectData.getProjetoFile(), csv);
        Path path = Paths.get(pathname);
        String actual = "";
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                actual += line;
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        String expected = "Tempo;null;null;";
        assertEquals(expected, actual);
    }

}