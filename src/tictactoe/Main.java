package tictactoe;

import java.util.*;
import tictactoe.exceptions.*;

public class Main {
    public static void main(String[] args) {
        final int N = 3;
        Scanner scanner = new Scanner(System.in);
        Board board = new Board(N, ' ');
        String user_choice;
        States end_state;

        do {
            System.out.print("Input command: ");
            user_choice = scanner.nextLine();
            String[] user_choice_parsed = user_choice.split(" ");
            if (!"exit".equals(user_choice)) {
                try {
                    end_state = board.startGame(user_choice_parsed, scanner);
                    switch (end_state) {
                        case WON_X:
                            System.out.println("X wins");
                            break;
                        case WON_0:
                            System.out.println("O wins");
                            break;
                        case DRAW:
                            System.out.println("Draw");
                            break;
                    }
                    board.clearBoard();
                } catch (wrongInputException e) {
                    System.out.println("Bad parameters!");
                }
            }
        } while (!"exit".equals(user_choice));
    }
}
