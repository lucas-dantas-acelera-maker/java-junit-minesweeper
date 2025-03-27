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

                if (userIn.equalsIgnoreCase("n")) {
                    exitGame = true;
                }
            }

            board.restartBoard();
            throw new ExitException("See you soon!");

        } catch (ExitException e) {
            System.out.println(e.getMessage());
        } finally {
            sc.close();
        }
    }

    private void gameCycle() {
        try {
            while (!board.goalAchieved()) {
                System.out.println(board);

                String userIn = getUserInput("Enter the field coordinates (x,y): ");

                String[] parts = userIn.split(",");

                List<Integer> positions = Arrays.stream(parts)
                        .map(e -> Integer.parseInt(e.trim()))
                        .toList();

                int row = positions.getFirst();
                int column = positions.get(1);

                userIn = getUserInput("1 - Open field \n2 - Toggle mark: ");

                if (userIn.equalsIgnoreCase("1")) {
                    board.openField(row, column);
                }

                if (userIn.equalsIgnoreCase("2")) {
                    board.toggleMark(row, column);
                }

            }

            System.out.println("Congrats! You've won the game :)");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("BOOM!!! " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Coordinates must be numbers: " + e.getMessage());
        }
    }

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
