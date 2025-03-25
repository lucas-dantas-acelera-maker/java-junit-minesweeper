package br.com.aceleramaker.model;

import br.com.aceleramaker.exception.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testIsMarkedDefaultValue() {
        assertFalse(field.isMarked());
    }

    @Test
    void testSwitchIsMarked() {
        field.switchMarkedField();
        assertTrue(field.isMarked());
    }

    @Test
    void testSwitchIsMarkedTwice() {
        field.switchMarkedField();
        field.switchMarkedField();
        assertFalse(field.isMarked());
    }

    @Test
    void testOpenNotMinedNotMarked() {
        assertTrue(field.openField());
    }

    @Test
    void testOpenNotMinedMarked() {
        field.switchMarkedField();
        assertFalse(field.openField());
    }

    @Test
    void testOpenMinedMarked() {
        field.switchMarkedField();
        field.mine();
        assertFalse(field.openField());
    }

    @Test
    void testOpenMinedNotMarked() {
        field.mine();
        assertThrows(ExplosionException.class, () -> field.openField());
    }

    @Test
    void testOpenNeighbors() {
        Field field11 = new Field(1,1);
        Field field22 = new Field(2,2);

        field22.addNeighbor(field11);
        field.addNeighbor(field22);

        field.openField();

        assertTrue(field22.isOpened() && field11.isOpened());
    }

    @Test
    void testOpenMinedNeighbor() {
        Field field22 = new Field(2,2);
        Field field12 = new Field(1,2);
        Field field11 = new Field(1,1);

        field12.mine();

        field.addNeighbor(field22);
        field22.addNeighbor(field11);
        field22.addNeighbor(field12);

        field.openField();

        assertTrue(field22.isOpened() && !field11.isOpened());
    }

}
