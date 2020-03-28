package edu.kit.informatik.input;

import edu.kit.informatik.Grammar;

public class Parser {
    public static Grammar parse(String input) {
        Grammar grammar = new Grammar();

        String[] clauses = input.split(",");

        grammar.setStartSymbol(clauses[0].split("->")[0].charAt(0));

        for (String clause : clauses) {
            String[] mapping = clause.split("->");
            Character from = mapping[0].charAt(0);
            String[] rules = mapping[1].split("\\|");
            for (String rule : rules) {
                grammar.addRule(from, rule);
            }
        }
        return grammar;
    }
}
