package br.com.aceleramaker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldTest {

    private Field field;

    @BeforeEach
    void startField() {
        field = new Field(3, 3);
    }

    @Test
    void testTrueNeighborDistance1Left() {
        Field neighbor = new Field(3, 2);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testTrueNeighborDistance1Right() {
        Field neighbor = new Field(3, 4);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testTrueNeighborDistance1Up() {
        Field neighbor = new Field(2, 3);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testTrueNeighborDistance1Down() {
        Field neighbor = new Field(4, 3);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testTrueNeighborDistance2DiagonalTopLeft() {
        Field neighbor = new Field(2, 2);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testTrueNeighborDistance2DiagonalTopRight() {
        Field neighbor = new Field(2, 4);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testTrueNeighborDistance2DiagonalBottomLeft() {
        Field neighbor = new Field(4, 2);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testTrueNeighborDistance2DiagonalBottomRight() {
        Field neighbor = new Field(4, 4);
        boolean res = field.addNeighbor(neighbor);
        assertTrue(res);
    }

    @Test
    void testFalseNeighbor() {
        Field neighbor = new Field(3, 3);
        boolean res = field.addNeighbor(neighbor);
        assertFalse(res);
    }

}
