package br.com.aceleramaker.model;

import br.com.aceleramaker.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * This class represents a single field of the Board
 * </p>
 * Each field has a position determined by row and column,
 * can be mined and can be opened or marked.
 * */
public class Field {
    private final int row;
    private final int column;

    private boolean isOpen = false;
    private boolean hasMine = false;
    private boolean isMarked = false;

    private List<Field> neighboringFields = new ArrayList<>();

    /**
     * <p>
     * Constructs a field with position based on row x column.
     * </p>
     * @param row field's row.
     * @param column field's column.
     * */
    Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * <p>
     * Adds a neighbor to the field.
     * </p>
     * The field is valid as a neighbor if the distance from the original
     * is equal to 1 for top, left, right, bottom fields, or 2 for diagonal.
     * @param neighbor the adjacent field to be added as neighbor.
     * @return true if the neighbor is added, otherwise returns false,
     * */
    boolean addNeighbor(Field neighbor) {
        boolean diffRow = this.row != neighbor.row;
        boolean diffCol = this.column != neighbor.column;
        boolean diagonal = diffRow && diffCol;

        // distance between actual row/column and possible neighbor row/column
        int deltaRow = Math.abs(this.row - neighbor.row);
        int deltaCol = Math.abs(this.column - neighbor.column);
        int deltaTotal = deltaRow + deltaCol;

        // same row or column neighbor distance must be 1
        if (deltaTotal == 1 && !diagonal) {
            neighboringFields.add(neighbor);
            return true;
        }

        // diagonal neighbor distance must be 2
        if (deltaTotal == 2 && diagonal) {
            neighboringFields.add(neighbor);
            return true;
        }

        return false;
    }

    public List<Field> getNeighboringFields() {
        return neighboringFields;
    }

    /**
     * <p>
     * Switches the marked state.
     * </p>
     * */
    void switchMarkedField() {
        if (!isOpen) {
            isMarked = !isMarked;
        }
    }

    /**
     * <p>
     * Opens the field.
     * </p>
     * Verifies if the field is already opened or marked and if
     * so, the neighbor fields are opened as well.
     *
     * @return true if the selected field has been opened,
     * otherwise returns false.
     * @throws ExplosionException if the selected field is mined.
     *
     * */
    boolean openField() {
        if (!isOpen && !isMarked) {
            isOpen = true;

            if (hasMine) {
                throw new ExplosionException("Game over!");
            }

            if (isNeighborhoodSafe()) {
                neighboringFields.forEach(Field::openField);
            }

            return true;
        }
        return false;
    }

    /**
     * <p>
     * Verifies if neighbor fields are safe.
     * </p>
     * @return true if the neighborhood is safe, otherwise returns false.
     * */
    boolean isNeighborhoodSafe() {
        return neighboringFields.stream()
                .noneMatch(nField -> nField.hasMine);
    }

    /**
     * <p>
     * Mines a field.
     * </p>
     * */
    void mine() {
        if (!hasMine) {
            hasMine = true;
        }
    }

    public boolean isMined() {
        return hasMine;
    }

    /**
     * <p>
     * Verifies if the field is marked.
     * </p>
     *
     * @return true if it's marked, otherwise returns false.
     * */
    public boolean isMarked() {
        return isMarked;
    }

    /**
     * <p>
     * Verifies if the field is opened.
     * </p>
     *
     * @return true if the field is opened, otherwise returns false
     * */
    public boolean isOpened() {
        return isOpen;
    }

    void setIsOpen() {
        isOpen = true;
    }

    /**
     * Gets the row position.
     *
     * @return number of the row.
     * */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column position.
     *
     * @return number of the column.
     * */
    public int getColumn() {
        return column;
    }
    
    /**<p>
     * Determines if the goal is achieved for the field.
     * </p>
     *
     * @return true if the field is safely opened or a mined field is marked,
     * otherwise returns false.
     *
     * */
    boolean fieldGoalAchieved() {
        boolean revealedField = !hasMine && isOpen;
        boolean protectedField = hasMine && isMarked;

        return protectedField || revealedField;
    }

    /**
     * <p>
     * Counts the number of mines in the neighboring fields.
     * </p>
     *
     * @return long number of mines in adjacent fields.
     * */
    long countNeighborhoodMines() {
        return neighboringFields.stream().filter(neighbor -> neighbor.hasMine).count();
    }

    /**
     * <p>
     * Restart the state of the field.
     * </p>
     * */
    void restartField() {
        isOpen = false;
        hasMine = false;
        isMarked = false;
    }

    /**
     * Returns a textual representation of the field for console display.
     * <p>
     * The output varies based on the field's state:
     * <ul>
     *  <li>"x" if the field is marked.</li>
     *  <li>"*" if the field is open and contains a mine.</li>
     *  <li>The number of neighboring mines if the field is open and has adjacent mines.</li>
     *  <li>" " (space) if the field is open and has no adjacent mines.</li>
     *  <li>"?" if the field is closed and not marked.</li>
     * </ul>
     *
     * @return a string representing the field's state.
     * */
    @Override
    public String toString() {
        if (isMarked) return "x";
        if (isOpen && hasMine) return "*";
        if (isOpen && countNeighborhoodMines() > 0) {
            return Long.toString(countNeighborhoodMines());
        }
        if (isOpen) return " ";
        return "?";
    }
}
