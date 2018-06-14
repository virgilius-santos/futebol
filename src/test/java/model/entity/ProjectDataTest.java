package model.entity;

import model.io.IOFiles;
import model.util.Conversion;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProjectDataTest {

    private ProjectData projectData;

    @org.junit.Before
    public void setUp() throws Exception {
        File file = new File("VideoA.mp4");
        projectData = new ProjectData(file);
        projectData.setVideoMD5(Conversion.getMD5CheckSum(file));
        projectData.setProjetoFile(new File("teste.json"));
        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
        projectData.setTempoDivisao(2);
    }

    /**
     * Testa se o arquivo de vídeo inicialmente atribuído ao objeto corresponde ao arquivo que
     * realmente foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getVideoFile() {
        File file = new File("VideoA.mp4");
        assertEquals(new File(file.toURI()), projectData.getVideoFile());
    }

    /**
     * Testa se a reatribuição do arquivo de vídeo do objeto realmente o atualiza e retorna
     * o arquivo correto.
     */
    @org.junit.Test
    public void setVideoFile() {
        File file = new File("VideoB.mp4");
        projectData.setVideoFile(file);
        assertEquals(new File(file.toURI()), projectData.getVideoFile());
    }

    /**
     * Testa se o MD5 do vídeo inicialmente atribuído ao objeto corresponde ao MD5 que realmente
     * foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getVideoMD5() throws Exception {
        String md5 = Conversion.getMD5CheckSum(projectData.getVideoFile());
        assertEquals(md5, projectData.getVideoMD5());
    }

    /**
     * Testa se a reatribuição do MD5 do vídeo do objeto realmente o atualiza e retorna o MD5 correto.
     */
    @org.junit.Test
    public void setVideoMD5() throws Exception {
        String md5 = Conversion.getMD5CheckSum(projectData.getVideoFile());
        projectData.setVideoMD5(md5);
        assertEquals(md5, projectData.getVideoMD5());

    }

    /**
     * Testa se o número de colunas inicial e padrão do objeto corresponde ao número que realmente
     * foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getColunas() {
        assertEquals((Integer) 8, projectData.getColumns());
    }

    /**
     * Testa se a reatribuição do número de colunas do objeto realmente o atualiza e retorna o número correto.
     */
    @org.junit.Test
    public void setColunas() {
        projectData.setColumns(9);
        assertEquals((Integer) 9, projectData.getColumns());
    }

    /**
     * Testa se o número de linhas inicial e padrão do objeto corresponde ao número que realmente
     * foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getLinhas() {
        assertEquals((Integer) 4, projectData.getLines());
    }

    /**
     * Testa se a reatribuição do número de linhas do objeto realmente o atualiza e retorna o número correto.
     */
    @org.junit.Test
    public void setLinhas() {
        projectData.setLines(5);
        assertEquals((Integer) 5, projectData.getLines());
    }

    /**
     * Testa se o arquivo do projeto inicialmente atribuído ao objeto corresponde ao arquivo que
     * realmente foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getProjetoFile() {
        File file = new File("teste.json");
        assertEquals(file, projectData.getProjetoFile());
    }

    /**
     * Testa se a reatribuição do arquivo do projeto desse objeto realmente o atualiza e retorna
     * o arquivo correto.
     */
    @org.junit.Test
    public void setProjetoFile() {
        File file = new File("teste2.json");
        projectData.setProjetoFile(file);
        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
        assertEquals(file, projectData.getProjetoFile());
    }

    /**
     * Testa se o intervalo de tempo entre os frames inicialmente atribuído ao objeto corresponde
     * ao intervalo de tempo que realmente foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getTempoDivisao() {
        assertEquals((Integer) 2, projectData.getTempoDivisao());
    }

    /**
     * Testa se a reatribuição do intervalo de tempo entre os frames do objeto realmente o atualiza
     * e retorna o intervalo de tempo correto.
     */
    @org.junit.Test
    public void setTempoDivisao() {
        projectData.setTempoDivisao(3);
        assertEquals((Integer) 3, projectData.getTempoDivisao());
    }

    /**
     * Testa se o URI do vídeo inicialmente atribuído ao objeto corresponde ao URI que
     * realmente foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getVideoURI() {
        URI videoURI = projectData.getVideoFile().toURI();
        assertEquals((videoURI).toString(), projectData.getVideoURI());
    }

    /**
     * Testa se a quantidade inicial e padrão de objetos do ProjectData corresponde à quantidade
     * que realmente foi guardada e que está sendo retornada.
     */
    @org.junit.Test
    public void getDataSize() {
        assertEquals( 2, projectData.getDataSize());
    }

    /**
     * Testa se os dados do ProjectData (lista de FrameDatas) inicial correspondem aos dados
     * que realmente foram guardados e que estão sendo retornados.
     */
    @org.junit.Test
    public void getDados() {
        ArrayList<FrameData> dados = new ArrayList<>(10);
        dados.add(new FrameData());
        dados.add(new FrameData());
        List<FrameData> actual = projectData.getDados();
        assertEquals(dados.size(), actual.size());
        assertEquals(dados.get(0).getName(), actual.get(0).getName());
        assertEquals(dados.get(1).getName(), actual.get(1).getName());
        assertEquals(dados.get(0).getQuadrants().size(), actual.get(0).getQuadrants().size());
        assertEquals(dados.get(1).getQuadrants().size(), actual.get(1).getQuadrants().size());
    }

    /**
     * Testa se um dado individual da lista de FrameDatas corresponde ao dado que realmente
     * foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getData() {
        FrameData frameData = new FrameData();
        assertEquals(frameData.getName(), projectData.getData(0).getName());
        assertEquals(frameData.getQuadrants().size(), projectData.getData(0).getQuadrants().size());
    }

    /**
     * Testa se adição de um novo objeto à lista de FrameDatas realmente atualiza a lista,
     * guarda e retorna o objeto correto.
     */
    @org.junit.Test
    public void addData() {
        FrameData frameData = new FrameData();
        frameData.setName("Bola");
        projectData.addData(frameData);
        assertEquals(frameData, projectData.getData(2));

    }

}