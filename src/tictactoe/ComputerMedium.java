package tictactoe;

import java.util.Random;


class ComputerMedium extends Computer implements Player{
    private final char symbol;
    private final char opponent_symbol;

    ComputerMedium(Board b, char sym, char opponent_sym) {
        super(b);
        symbol = sym;
        opponent_symbol = opponent_sym;
    }

    @Override
    public void makeMove() {
        System.out.println("Making move level \"medium\"");
        Random random = new Random();
        int x, y;

        int[] possible_coords = Utils.checkIfOneShotPossible(board, symbol);
        x = possible_coords[0];
        y = possible_coords[1];

        if (x != -1 && y != -1) {
            board.setBoardAt(x-1, y-1, symbol);
        }
        else {
            possible_coords = Utils.checkIfOneShotPossible(board, opponent_symbol);
            x = possible_coords[0];
            y = possible_coords[1];
            if (x != -1 && y != -1) {
                board.setBoardAt(x-1, y-1, symbol);
            }
            else {
                do {
                    x = random.nextInt(board.N);
                    y = random.nextInt(board.N);
                } while (board.getBoardAt(x, y) != board.free_cell_char);

                board.setBoardAt(x, y, symbol);
            }
        }
    }
}
