package model.entity;

import model.io.IOFiles;
import model.util.Conversion;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectDataTest {

    private ProjectData projectData;

    @org.junit.Before
    public void setUp() throws Exception {
        File file = new File("VideoA.mp4");
        projectData = new ProjectData(file);
        projectData.setVideoMD5(Conversion.getMD5Checksum(file));
        projectData.setProjetoFile(new File("teste.json"));
        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
        projectData.setTempoDivisao(2);
    }

    @org.junit.Test
    public void getVideoFile() throws Exception {
        File file = new File("VideoA.mp4");
        assertEquals(new File(file.toURI()), projectData.getVideoFile());
    }

    @org.junit.Test
    public void setVideoFile() throws Exception {
        File file = new File("VideoB.mp4");
        projectData.setVideoFile(file);
        assertEquals(new File(file.toURI()), projectData.getVideoFile());
    }

    @org.junit.Test
    public void getVideoMD5() throws Exception {
        String md5 = Conversion.getMD5Checksum(projectData.getVideoFile());
        assertEquals(md5, projectData.getVideoMD5());
    }

    @org.junit.Test
    public void setVideoMD5() throws Exception {
        String md5 = Conversion.getMD5Checksum(projectData.getVideoFile());
        projectData.setVideoMD5(md5);
        assertEquals(md5, projectData.getVideoMD5());

    }

    @org.junit.Test
    public void getColunas() throws Exception {
        assertEquals((Integer) 8, projectData.getColunas());
    }

    @org.junit.Test
    public void setColunas() throws Exception {
        projectData.setColunas(9);
        assertEquals((Integer) 9, projectData.getColunas());
    }

    @org.junit.Test
    public void getLinhas() throws Exception {
        assertEquals((Integer) 4, projectData.getLinhas());
    }

    @org.junit.Test
    public void setLinhas() throws Exception {
        projectData.setLinhas(5);
        assertEquals((Integer) 5, projectData.getLinhas());
    }

    @org.junit.Test
    public void getProjetoFile() throws Exception {
        File file = new File("teste.json");
        assertEquals(file, projectData.getProjetoFile());
    }

    @org.junit.Test
    public void setProjetoFile() throws Exception {
        File file = new File("teste2.json");
        projectData.setProjetoFile(file);
        IOFiles.saveJsonFile(projectData.getProjetoFile(), projectData);
        assertEquals(file, projectData.getProjetoFile());
    }

    @org.junit.Test
    public void getTempoDivisao() throws Exception {
        assertEquals((Integer) 2, projectData.getTempoDivisao());
    }

    @org.junit.Test
    public void setTempoDivisao() throws Exception {
        projectData.setTempoDivisao(3);
        assertEquals((Integer) 3, projectData.getTempoDivisao());
    }

    @org.junit.Test
    public void getVideoURI() throws Exception {
        URI videoURI = projectData.getVideoFile().toURI();
        assertEquals((videoURI).toString(), projectData.getVideoURI());
    }

    @org.junit.Test
    public void getDataSize() throws Exception {
        assertEquals( 2, projectData.getDataSize());
    }

    @org.junit.Test
    public void getDados() throws Exception {
        ArrayList<FrameData> dados = new ArrayList<>(10);
        dados.add(new FrameData());
        dados.add(new FrameData());
        assertEquals(dados, projectData.getDados());
    }

    @org.junit.Test
    public void getData() throws Exception {
        FrameData frameData = new FrameData();
        assertEquals(frameData, projectData.getData(0));
    }

    @org.junit.Test
    public void addData() throws Exception {
        FrameData frameData = new FrameData();
        frameData.setName("Bola");
        projectData.addData(frameData);
        assertEquals(frameData, projectData.getData(2));

    }

}