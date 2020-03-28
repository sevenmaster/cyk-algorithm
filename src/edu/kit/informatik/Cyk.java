package edu.kit.informatik;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cyk {
    private Grammar grammar;

    String[][] pyramid;
    String input;

    public Cyk(Grammar grammar) {
        this.grammar = grammar;
    }

    public boolean check(String input) {
        this.input = input;
        if (input.equals("e")) {
            return grammar.getMappingTo("e").contains(grammar.getStartSymbol().toString());
        }
        int n = input.length();
        pyramid = new String[n][n];
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            String res = grammar.getMappingTo(Character.toString(charArray[i]));
            pyramid[n - 1][i] = res;
        }
        for (int row = n - 2; row >= 0; row--) {
            for (int column = 0; column < row + 1; column++) {
                fill(row, column, n);
            }
        }
        return pyramid[0][0].contains(grammar.getStartSymbol().toString());
    }

    private void fill(int row, int column, int n) {
        Set<Character> full = new HashSet<>();
        for (int i = 0; i < n - row - 1; i++) {
            int r = row + 1 + i;
            int c = column + n - row - 1 - i;
            int rc = n - 1 - i;
            char[] front = pyramid[r][column].toCharArray();
            char[] back = pyramid[rc][c].toCharArray();
            for (char f : front) {
                for (char b : back) {
                    for (char j : grammar.getMappingTo(((Character) f).toString() + ((Character) b).toString()).toCharArray()) {
                        full.add(j);
                    }
                }
            }
        }
        StringBuilder f = new StringBuilder();
        for (Character c : full) {
            f.append(c.toString());
        }
        pyramid[row][column] = f.toString();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (String[] strings : pyramid) {
            result.append(Arrays.deepToString(strings));
            result.append("\n");
        }
        result.append(this.input.toCharArray());
        return result.toString().replace("null", "/");
    }
}
