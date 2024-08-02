package tictactoe;

import tictactoe.exceptions.wrongCoordsException;
import java.util.InputMismatchException;
import java.util.Scanner;


class User implements Player {
    private final Board board;
    private final Scanner scanner;
    private final char symbol;

    User(Board b, Scanner sc, char sym) {
        board = b;
        scanner = sc;
        symbol = sym;
    }

    public void makeMove() {
        boolean ok = false;
        int x=1, y=1;
        do {
            try {
                System.out.print("Enter the coordinates: ");
                x = scanner.nextInt();
                y = scanner.nextInt();
                System.out.println();
                Utils.checkIfCoordsCorrect(x, y, board);
                ok = true;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("You should enter numbers!");
            } catch (wrongCoordsException e) {
                System.out.println(e.getMessage());
            }
        } while (!ok);

        board.setBoardAt(x-1, y-1, symbol);
        scanner.nextLine();
    }
}
