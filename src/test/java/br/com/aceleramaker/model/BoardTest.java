package br.com.aceleramaker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private static final int ROWS = 5;
    private static final int COLS = 5;
    private static final int MINES = 5;

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
        int expectedSize = 5 * 5;
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
        assertEquals(5, board.getMines());
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

}
