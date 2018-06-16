package model.entity;

import static org.junit.Assert.*;

public class FrameDataTest {

    private FrameData frameData;

    @org.junit.Before
    public void setUp() {
        frameData = new FrameData();
        frameData.setName("Bob");
        frameData.setQuadrant(1,3);
        frameData.setQuadrant(3,7);
        frameData.setQuadrant(5,7);
        frameData.setQuadrant(5,null);
        frameData.setQuadrant(7,9);
    }

    /**
     * Testa se o nome inicialmente atribuído ao objeto corresponde ao nome que realmente foi guardado
     * e que está sendo retornado.
     */
    @org.junit.Test
    public void getName() {
        assertEquals("Bob", frameData.getName());
    }

    /**
     * Testa se a renomeação do objeto realmente o atualiza e retorna o nome correto.
     */
    @org.junit.Test
    public void setName() {
        frameData.setName("Tevez");
        assertEquals("Tevez", frameData.getName());
    }

    /**
     * Testa se o quadrante inicialmente atribuído para o objeto em um determinado tempo corresponde
     * ao quadrante que realmente foi guardado e que está sendo retornado.
     */
    @org.junit.Test
    public void getQuadrant() {
        int q1 = frameData.getQuadrant(1);
        int q2 = 3;
        assertEquals(q1, q2);
    }

    /**
     * Testa se a reatribuição de um quadrante do objeto para um determinado tempo realmente o atualiza
     * e retorna o quadrante correto.
     */
    @org.junit.Test
    public void setQuadrant() {
        frameData.setQuadrant(1,4);
        int q1 = frameData.getQuadrant(1);
        int q2 = 4;
        assertEquals(q1, q2);
    }

    /**
     * Testa se um quadrante inicializado como nulo realmente guardou e está retornando o valor nulo.
     */
    @org.junit.Test
    public void getNullQuadrant() {
        assertNull(frameData.getQuadrant(5));
    }

}