package tictactoe;

import java.util.Scanner;
import tictactoe.exceptions.wrongInputException;


public class Board {
    int N;
    private final char[] board;
    char free_cell_char;

    Board(int N, char free_cell_char) {
        this.N = N;
        this.free_cell_char = free_cell_char;
        board = new char[N*N];
        for (int i=0; i<N*N; ++i) {
            board[i] = free_cell_char;
        }
    }

    char getBoardAt(int i, int j) {
        return board[(i*N)+j];
    }

    char getBoardAt(int i) {
        return board[i];
    }

    void setBoardAt(int i, char symbol) {
        board[i] = symbol;
    }

    public void setBoardAt(int i, int j, char symbol) {
        board[i*N+j] = symbol;
    }

    States startGame(String[] args, Scanner sc) throws wrongInputException {
        if (args.length != 3) {
            throw new wrongInputException();
        }

        if (!"start".equals(args[0]) ||
            !("user".equals(args[1]) || "easy".equals(args[1]) || "medium".equals(args[1]) || "hard".equals(args[1])) ||
            !("user".equals(args[2]) || "easy".equals(args[2]) || "medium".equals(args[2]) || "hard".equals(args[2])))
        {
            throw new wrongInputException();
        }

        printBoard();

        Player player_x;
        Player player_o;

        if ("user".equals(args[1])) { player_x = new User(this, sc, 'X'); }
        else if ("easy".equals(args[1])) { player_x = new ComputerEasy(this, 'X'); }
        else if ("medium".equals(args[1])) { player_x = new ComputerMedium(this, 'X', 'O'); }
        else { player_x = new ComputerHard(this, 'X', 'O'); }

        if ("user".equals(args[2])) { player_o = new User(this, sc, 'O'); }
        else if ("easy".equals(args[2])) { player_o = new ComputerEasy(this, 'O'); }
        else if ("medium".equals(args[2])) { player_o = new ComputerMedium(this, 'O', 'X'); }
        else { player_o = new ComputerHard(this, 'O', 'X'); }

        States act_state = States.NOT_FINISHED;
        boolean player_x_turn = true;
        while (act_state == States.NOT_FINISHED) {
            if (player_x_turn) {
                player_x.makeMove();
                player_x_turn = false;
            }
            else {
                player_o.makeMove();
                player_x_turn = true;
            }

            printBoard();
            act_state = concludeState();
        }

        return act_state;
    }

    public States concludeState() {
        if (Utils.checkIfGameIsWon(this, 'X')) {
            return States.WON_X;
        }
        else if (Utils.checkIfGameIsWon(this, 'O')) {
            return States.WON_0;
        }
        else {
            if (Utils.areThereEmptyCells(this)) {
                return States.NOT_FINISHED;
            }
            else {
                return States.DRAW;
            }
        }
    }

    void printBoard() {
        for(int i=0; i<3*N; ++i) { System.out.print('-'); }
        System.out.println();
        for (int i=0; i<N; ++i) {
            System.out.print("| ");
            for (int j=0; j<N; ++j) {
                System.out.print(board[i*N+j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        for(int i=0; i<3*N; ++i) { System.out.print('-'); }
        System.out.println();
    }

    void clearBoard() {
        for (int i=0; i<N*N; ++i) { board[i] = free_cell_char; }
    }
}
