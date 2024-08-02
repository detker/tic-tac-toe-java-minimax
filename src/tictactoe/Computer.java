package tictactoe;

abstract class Computer implements Player {
    protected Board board;

    protected Computer(Board board) {
        this.board = board;
    }

    abstract public void makeMove();
}
