package tictactoe;

import tictactoe.exceptions.*;
import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;


public class Utils {
    public static void checkIfStateCorrect(String str, final int N) throws wrongInputException {
        if (str.length() != N*N) {
            throw new wrongInputException("Input length not valid!");
        }

        Pattern p = Pattern.compile("[OX_]+");
        Matcher m = p.matcher(str);
        if (!m.find() || !str.equals(m.group(0))) {
            throw new wrongInputException("Input contains at least one invalid symbol.");
        }
    }

    public static void checkIfCoordsCorrect(int x, int y, Board b) throws wrongCoordsException{
        if (x < 1 || x > b.N || y < 1 || y > b.N) {
            throw new wrongCoordsException("Coordinates should be from 1 to 3!");
        }

        if (b.getBoardAt(x-1, y-1) != b.free_cell_char) {
            throw new wrongCoordsException("This cell is occupied! Choose another one!");
        }
    }

    public static boolean areThereEmptyCells(Board b) {
        for (int i=0; i<b.N*b.N; ++i) {
            if (b.getBoardAt(i) == b.free_cell_char) {
                return true;
            }
        }
        return false;
    }

    public static List<Integer> getEmptyPosInt(Board board) {
        List<Integer> result = new ArrayList<>();
        for (int i=0; i<board.N*board.N; ++i) {
            if (board.getBoardAt(i) == board.free_cell_char) {
                result.add(i);
            }
        }
        return result;
    }

    public static int[] checkIfOneShotPossible(Board b, char sym) {
        int[] result = {-1, -1};
        int ok_diag_n = 0;
        int ok_anti_diag_n = 0;
        int free_slot_diag = -1;
        int free_slot_anti_diag = -1;

        for (int i=0; i<b.N; ++i) {
            if (b.getBoardAt(i, i) == sym) {
                ok_diag_n += 1;
            }
            else if (b.getBoardAt(i, i) == b.free_cell_char) {
                free_slot_diag = i+1;
            }

            if (b.getBoardAt(i, b.N-i-1) == sym) {
                ok_anti_diag_n += 1;
            }
            else if (b.getBoardAt(i, b.N-i-1) == b.free_cell_char) {
                free_slot_anti_diag = i+1;
            }

            int ok_h_i_n = 0;
            int ok_v_i_n = 0;
            int free_slot_h_i = -1;
            int free_slot_v_i = -1;
            for (int j=0; j<b.N; ++j) {
                if (b.getBoardAt(i, j) == sym) {
                    ok_h_i_n += 1;
                }
                else if (b.getBoardAt(i, j) == b.free_cell_char) {
                    free_slot_h_i = j+1;
                }

                if (b.getBoardAt(j, i) == sym) {
                    ok_v_i_n += 1;
                }
                else if (b.getBoardAt(j, i) == b.free_cell_char) {
                    free_slot_v_i = j+1;
                }
            }

            if (ok_h_i_n == b.N-1 && free_slot_h_i != -1) {
                result[0] = i+1;
                result[1] = free_slot_h_i;
                return result;
            }
            if (ok_v_i_n == b.N-1 && free_slot_v_i != -1) {
                result[0] = free_slot_v_i;
                result[1] = i+1;
                return result;
            }
        }

        if (ok_diag_n == b.N-1 && free_slot_diag != -1) {
            result[0] = free_slot_diag;
            result[1] = free_slot_diag;
        }
        else if (ok_anti_diag_n == b.N-1 && free_slot_anti_diag != -1) {
            result[0] = free_slot_anti_diag;
            result[1] = b.N-free_slot_anti_diag+1;
        }
        return result;
    }

    public static boolean checkIfGameIsWon(Board b, char sym) {
        boolean ok_diag = true;
        boolean ok_anti_diag = true;
        boolean ok_h = false;
        boolean ok_v = false;
        boolean cond;

        for (int i=0; i<b.N; ++i) {
            if (ok_diag && b.getBoardAt(i, i) != sym) {
                ok_diag = false;
            }

            if (ok_anti_diag && b.getBoardAt(i, b.N-i-1) != sym) {
                ok_anti_diag = false;
            }

            boolean i_ok_h = true;
            boolean i_ok_v = true;

            for (int j=0; j<b.N; ++j) {
                if (i_ok_h && b.getBoardAt(i, j) != sym) {
                    i_ok_h = false;
                }

                if (i_ok_v && b.getBoardAt(j, i) != sym) {
                    i_ok_v = false;
                }
            }

            if (i_ok_h) {
                ok_h = true;
            }
            if (i_ok_v) {
                ok_v = true;
            }
        }
        cond = ok_diag || ok_anti_diag || ok_h || ok_v;
        return cond;
    }
}
