package br.com.aceleramaker.application;

import br.com.aceleramaker.model.Board;
import br.com.aceleramaker.view.BoardConsoleView;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(6,6,6);
        new BoardConsoleView(board);
    }
}
