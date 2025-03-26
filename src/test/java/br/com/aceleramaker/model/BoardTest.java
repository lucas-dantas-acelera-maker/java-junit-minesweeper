package br.com.aceleramaker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private static final int ROWS = 6;
    private static final int COLS = 6;
    private static final int MINES = 6;

    @BeforeEach
    void startBoard() {
        board = new Board(ROWS, COLS, MINES);
    }

    @Test
    void testBoardConstructor() {
        assertNotNull(board, "Board should be initialized");
    }

    @Test
    void testGenerateFields() {
        int expectedSize = 6 * 6;
        assertEquals(expectedSize, board.getCols() * board.getRows());
    }

    @Test
    void testDefineNeighbors() {
        assertTrue(
                board.getFields().stream()
                                .noneMatch(field -> field.getNeighboringFields()
                                .isEmpty())
        );
    }

    @Test
    void testMineFields() {
        assertEquals(6, board.getMines());
    }

    @Test
    void testGameWon() {
        board.getFields().stream()
                .filter(field -> !field.isMined())
                .forEach(Field::openField);

        assertTrue(board.goalAchieved());
    }

    @Test
    void testRestartBoard() {
        board.getFields()
                .forEach(field -> {
                    if (!field.isMined()) {
                        field.openField();
                    } else {
                        field.switchMarkedField();
                    }
                });

        board.restartBoard();

        boolean isBoardReseted = board.getFields().stream()
                .allMatch(field -> !field.isOpened() && !field.isMarked());

        assertTrue(isBoardReseted);

        assertEquals(MINES, board.getMines());
    }

    @Test
    void testOpenField() {
        Field f = board.getFields().stream()
                .filter(field -> !field.isMined())
                .findFirst()
                .orElse(new Field(3, 3));

        board.openField(f.getRow(), f.getColumn());

        assertTrue(f.isOpened());
    }

    @Test
    void testToggleMark() {
        Field f = board.getFields().stream()
                .findAny()
                .orElse(new Field(3,3));

        board.toggleMark(f.getRow(), f.getColumn());

        assertTrue(f.isMarked());
    }

    @Test
    void testToStringRowAndColumnNumbers() {
        String expected = """
                  0  1  2  3  4  5\s
                0 ?  ?  ?  ?  ?  ?\s
                1 ?  ?  ?  ?  ?  ?\s
                2 ?  ?  ?  ?  ?  ?\s
                3 ?  ?  ?  ?  ?  ?\s
                4 ?  ?  ?  ?  ?  ?\s
                5 ?  ?  ?  ?  ?  ?\s
                """;

        assertEquals(expected, board.toString());
    }

}
