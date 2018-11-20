package model.io;

import model.entity.FrameData;
import model.entity.ProjectData;
import model.util.Conversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IOFilesTest {

    private ProjectData projectData;

    @org.junit.Before
    public void setUp() throws Exception {
        File file = new File("VideoA.mp4");
        projectData = new ProjectData(file);
        projectData.setVideoMD5(Conversion.getMD5CheckSum(file));
        projectData.getData(0).setName("Jogador");
        projectData.getData(1).setName("Bola");
        projectData.getData(0).setQuadrant(0,15);
        projectData.getData(1).setQuadrant(0, 16);
    }

    /**
     * Testa se os dados salvos no arquivo JSON correspondem aos dados que realmente deveriam ser salvos.
     */
    @org.junit.Test
    public void saveJsonFile() {
        String pathname = "teste.json";
        File teste = new File(pathname);
        projectData.setProjetoFile(teste);
        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
        Path path = Paths.get(pathname);
        StringBuilder actual = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("utf8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        String expected = "{\"videoMD5\":\"a0e9d74b6ed985e3cfaa42df337d7f36\",\"videoURI\":\"file:/Users/virgiliussantos/git/futebol/VideoA.mp4\",\"projetoFile\":{\"path\":\"teste.json\"},\"tempoDivisao\":2,\"lines\":2,\"columns\":4,\"dados\":[{\"name\":\"Jogador\",\"quadrants\":{\"0\":15}},{\"name\":\"Bola\",\"quadrants\":{\"0\":16}}]}";
        assertEquals(expected, actual.toString());
    }

    /**
     * Testa se os dados salvos no arquivo CSV correspondem aos dados que realmente deveriam ser salvos.
     */
    @org.junit.Test
    public void saveCsvFile() {
        String pathname = "teste.csv";
        File teste = new File(pathname);
        projectData.setProjetoFile(teste);
        String csv = Conversion.convertToCSV(projectData.getDados());
        IOFiles.saveCsvFile(projectData.getProjetoFile(), csv);
        Path path = Paths.get(pathname);
        StringBuilder actual = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("utf8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actual.append(line);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        String expected = "Tempo;Jogador;Bola;0;15;16;";
        assertEquals(expected, actual.toString());
    }

    /**
     * Testa se o carregamento dos dados que foram salvos em arquivo JSON correspondem aos dados do projeto
     * salvos como uma lista de FrameDatas.
     */
    @org.junit.Test
    public void loadJsonFile() {
        String pathname = "teste.json";
        File teste = new File(pathname);
        projectData.setProjetoFile(teste);
        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
        List<FrameData> actual = IOFiles.loadJsonFile(teste, ProjectData.class).getDados();
        List<FrameData> expected = projectData.getDados();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
        assertEquals(expected.get(0).getQuadrants().size(), actual.get(0).getQuadrants().size());
        assertEquals(expected.get(1).getQuadrants().size(), actual.get(1).getQuadrants().size());
    }

}