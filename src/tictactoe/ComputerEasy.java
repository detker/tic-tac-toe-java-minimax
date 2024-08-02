package tictactoe;

import java.util.Random;


class ComputerEasy extends Computer implements Player {
    private final char symbol;

    ComputerEasy(Board b, char sym) {
        super(b);
        symbol = sym;
    }

    @Override
    public void makeMove() {
        System.out.println("Making move level \"easy\"");
        Random random = new Random();

        int x, y;
        do {
            x = random.nextInt(board.N);
            y = random.nextInt(board.N);
        } while (board.getBoardAt(x, y) != board.free_cell_char);

        board.setBoardAt(x, y, symbol);
    }
}
