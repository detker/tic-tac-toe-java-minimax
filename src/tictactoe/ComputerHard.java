package tictactoe;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class ComputerHard extends Computer implements Player{
    private final char symbol;
    private final char opponent_symbol;

    ComputerHard(Board b, char sym, char opponent_sym) {
        super(b);
        symbol = sym;
        opponent_symbol = opponent_sym;
    }

    private Map<String, Integer> minimax(Board b, char act_player, char op_player) {
        List<Map<String, Integer>> moves = new ArrayList<>();
        List<Integer> empty_positions = Utils.getEmptyPosInt(b);

        if (Utils.checkIfGameIsWon(b, symbol)) {
            Map<String, Integer> hm = new HashMap<>();
            hm.put("Score", 10);
            return hm;
        }
        if (Utils.checkIfGameIsWon(b, opponent_symbol)) {
            Map<String, Integer> hm = new HashMap<>();
            hm.put("Score", -10);
            return hm;
        }
        if (empty_positions.isEmpty()) {
            Map<String, Integer> hm = new HashMap<>();
            hm.put("Score", 0);
            return hm;
        }

        for ( Integer idx : empty_positions ) {
            Map<String, Integer> move = new HashMap<>();
            move.put("Index", idx);

            b.setBoardAt(idx, act_player);

            Map<String, Integer> result = minimax(b, op_player, act_player);
            move.put("Score", result.get("Score"));

            b.setBoardAt(idx, board.free_cell_char);

            moves.add(move);
        }

        Map<String, Integer> bestMove = moves.get(0);
        Integer bestScore = moves.get(0).get("Score");
        if (act_player == symbol) {
            for (Map<String, Integer> el : moves) {
                if (el.get("Score") > bestScore) {
                    bestScore = el.get("Score");
                    bestMove = el;
                }
            }
        }
        else {
            for (Map<String, Integer> el : moves) {
                if (el.get("Score") < bestScore) {
                    bestScore = el.get("Score");
                    bestMove = el;
                }
            }
        }

        return bestMove;
    }

    @Override
    public void makeMove() {
        System.out.println("Making move level \"hard\"");
        Map<String, Integer> move = minimax(board, symbol, opponent_symbol);
        board.setBoardAt(move.get("Index"), symbol);
    }
}
