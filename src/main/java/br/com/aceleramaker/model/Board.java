package br.com.aceleramaker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * <p>
 * This class represents the Board of the game.
 * </p>
 * The board has a number of rows, columns and mines.
 * */
public class Board {

    private int rows;
    private int cols;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    /**
     * <p>
     * Constructs a new board.
     * </p>
     * Constructs a board with the given number of rows, cols and mines.
     * Generates the fields, defining neighbors and sorting mines across the board.
     *
     * @param rows number of rows.
     * @param cols number of columns.
     * @param mines number of mines.
     * */
    public Board(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;

        generateFields();
        defineNeighbors();
        mineFields();
    }

    /**
     * <p>
     * Generates the fields of the board.
     * </p>
     * Iterates over the number of rows and columns,
     * creating a Field for each position.
     * */
    private void generateFields() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                fields.add(new Field(row, column));
            }
        }
    }

    /**
     * <p>
     * Defines the neighbors of each Field
     * </p>
     * Iterates over each field, associating it with adjacent fields.
     * */
    private void defineNeighbors() {
        for (Field f1 : fields) {
            for (Field f2 : fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    /**
     * <p>
     * Mines the fields.
     * </p>
     *
     * */
    private void mineFields() {
        long plantedMines = 0;
        Predicate<Field> mined = Field::isMined;

        do {
            plantedMines = fields.stream().filter(mined).count();
            int randomPosition = (int) (Math.random() * fields.size());
            fields.get(randomPosition).mine();
        } while (plantedMines < mines);
    }

    public boolean goalAchieved() {
        return fields.stream().allMatch(Field::fieldGoalAchieved);
    }

    public void restartBoard() {
        fields.forEach(Field::restartField);
        mineFields();
    }
}
