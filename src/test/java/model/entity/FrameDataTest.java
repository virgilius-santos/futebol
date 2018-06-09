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

    @org.junit.Test
    public void getName() {
        assertEquals("Bob", frameData.getName());
    }

    @org.junit.Test
    public void setName() {
        frameData.setName("Tevez");
        assertEquals("Tevez", frameData.getName());
    }

    @org.junit.Test
    public void getQuadrant() {
        int q1 = frameData.getQuadrant(1);
        int q2 = 3;
        assertEquals(q1, q2);
    }

    @org.junit.Test
    public void setQuadrant() {
        frameData.setQuadrant(1,4);
        int q1 = frameData.getQuadrant(1);
        int q2 = 4;
        assertEquals(q1, q2);
        assertNull(frameData.getQuadrant(5));

    }

    @org.junit.Test
    public void getQuadrants() {
        assertNull(frameData.getQuadrant(5));
    }

}