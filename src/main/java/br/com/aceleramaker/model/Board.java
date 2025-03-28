package br.com.aceleramaker.model;

import br.com.aceleramaker.exception.ExplosionException;

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
     * Gets the board number of rows.
     *
     * @return integer number of rows.
     * */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the board number of columns.
     *
     * @return integer number of columns.
     * */
    public int getCols() {
        return cols;
    }

    /**
     * Gets the board number of mines.
     *
     * @return integer number of mines.
     * */
    int getMines() {
        return mines;
    }

    /**
     * Gets the fields of the board.
     *
     * @return List of Field in the board.
     * */
    List<Field> getFields() {
        return fields;
    }

    /**
     * <p>
     * Opens a selected field.
     * </p>
     * Iterates over the fields of the board and open the selected field.
     *
     * @param row number of the field's row to be opened.
     * @param column number of the field's column to be opened.
     * */
    public void openField(int row, int column) {
        try {
            fields.parallelStream()
                    .filter(field -> field.getRow() == row && field.getColumn() == column)
                    .findFirst()
                    .ifPresent(Field::openField);
        } catch (ExplosionException e) {
            fields.stream()
                    .filter(Field::isMined)
                    .forEach(Field::setIsOpen);
            throw e;
        }

    }

    /**
     * <p>
     * Toggle the mark of a selected field.
     * </p>
     * Iterates over the fields of the board and toggles the mark of the field.
     *
     * @param row number of the field's row to be marked.
     * @param column number of the field's column to be marked.
     * */
    public void toggleMark(int row, int column) {
        fields.parallelStream()
                .filter(field -> field.getRow() == row && field.getColumn() == column)
                .findFirst()
                .ifPresent(Field::switchMarkedField);
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
     * Verifies the amount of mined fields and mine a field with
     * random position.
     * */
    private void mineFields() {
        long plantedMines = 0;
        Predicate<Field> mined = Field::isMined;

        do {
            int randomPosition = (int) (Math.random() * fields.size());
            fields.get(randomPosition).mine();
            plantedMines = fields.stream().filter(mined).count();
        } while (plantedMines < mines);
    }

    /**
     * <p>
     * Verifies if the user won the game.
     * </p>
     *
     * @return true if all safe fields are opened, returns false otherwise.
     * */
    public boolean goalAchieved() {
        return fields.stream()
                .filter(field -> !field.isMined())
                .allMatch(Field::isOpened);
    }

    /**
     * <p>
     * Restart the board, resetting state and mining fields again.
     * </p>
     * */
    public void restartBoard() {
        fields.forEach(Field::restartField);
        mineFields();
    }

    /**<p>
     * Returns a textual representation of the Board for console display.
     * </p>
     * The output includes:
     * <ul>
     *   <li>Column numbers at the top for reference.</li>
     *   <li>Row numbers at the beginning of each row.</li>
     *   <li>The state of each field displayed in a structured grid format.</li>
     * </ul>
     * This method is useful for visualizing the minefield during gameplay.
     *
     * @return a formatted string representing the minefield.
     * */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int i = 0;

        // Prints col numbers
        sb.append(" ");
        for (int c = 0; c < cols; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }

        sb.append("\n");

        // Prints row numbers and fields
        for(int row = 0; row < rows; row ++) {
            sb.append(row);
            for (int col = 0; col < cols; col ++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");

                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
