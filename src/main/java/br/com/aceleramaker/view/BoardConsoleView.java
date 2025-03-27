package br.com.aceleramaker.view;

import br.com.aceleramaker.exception.ExitException;
import br.com.aceleramaker.exception.ExplosionException;
import br.com.aceleramaker.model.Board;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Handles the console-based interaction for the Minesweeper game.
 * <p>
 * This class is responsible for managing user input and displaying the board.
 * It also controls the game loop, processes user commands, and handles exceptions
 * such as game exits and explosions.
 * </p>
 */
public class BoardConsoleView {

    private Board board;
    Scanner sc = new Scanner(System.in);


    /**
     * Constructs a BoardConsoleView and starts the game loop.
     *
     * @param board The game board to be managed.
     */
    public BoardConsoleView(Board board) {
        this.board = board;

        runGame();
    }

    /**
     * Manages the main game loop, handling user input and game restarts.
     * Catches ExitException when the user chooses to quit the game.
     */
    private void runGame() {
        try {
            boolean exitGame = false;

            while (!exitGame) {
                gameCycle();

                String userIn = getUserInput("Restart game? (S/n): ");

                if (userIn.equalsIgnoreCase("s")) {
                    board.restartBoard();
                } else {
                    exitGame = true;
                }
            }

            throw new ExitException("See you soon!");
        } catch (ExitException e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
    }

    /**
     * Executes the cycle of the game, processing user actions.
     * Handles opening fields, marking fields, and detecting game outcomes.
     * Catches exceptions related to invalid inputs and explosions.
     */
    private void gameCycle() {
        try {
            while (!board.goalAchieved()) {
                System.out.println(board);

                String userIn = getUserInput("Enter the field coordinates (x,y): ");


                List<Integer> positions = getCoordinates(userIn);

                if (positions != null) {
                    int row = positions.getFirst();
                    int column = positions.get(1);

                    userIn = getUserInput("1 - Open field \n2 - Toggle mark: ");

                    switch (userIn) {
                        case "1" -> board.openField(row, column);
                        case "2" -> board.toggleMark(row, column);
                        default -> System.out.println("Invalid option. Please enter 1 or 2.");
                    }
                }
            }

            System.out.println("Congrats! You've won the game :)");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("BOOM!!! " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Converts the user's coordinate input into a list of integers.
     *
     * @param input The user input in the format "row,column".
     * @return A list containing row and column as integers.
     */
    private List<Integer> getCoordinates(String input) {
        String[] parts = input.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Please enter coordinates in the format 'row,column'.");
        }

        try {
            return Arrays.stream(parts)
                    .map(e -> Integer.parseInt(e.trim()))
                    .toList();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Coordinates must be numeric values.");
        }
    }


    /**
     * Prompts the user for input and returns their response.
     *
     * @param text The message displayed to the user.
     * @return The user's input as a string.
     * @throws ExitException if the user inputs "exit".
     */
    private String getUserInput(String text) {
        System.out.print(text);
        String userInput = sc.nextLine();
        System.out.println();

        if (userInput.equalsIgnoreCase("exit")) {
            throw new ExitException("See you soon!");
        }

        return userInput;
    }

}
