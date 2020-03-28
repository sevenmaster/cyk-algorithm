package edu.kit.informatik;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Grammar {
    private Map<Character, List<String>> rules = new HashMap<>();
    private static final Character emptyWord = 'e';

    private Character startingSymbol;

    public void addRule(Character from, String to) {
        if (rules.containsKey(from)) {
            rules.get(from).add(to);
        }
        else {
            List<String> value = new LinkedList<>();
            value.add(to);
            rules.put(from, value);
        }
    }

    public boolean isChomsky() {
        if (rules.containsKey(emptyWord)) return false;
        for (Map.Entry<Character, List<String>> clause : rules.entrySet()) {
            for (String rule : clause.getValue()) {
                if (rule.length() == 1) { // terminating symbols
                    if (rules.containsKey(rule.charAt(0))) return false;
                } else if (rule.length() == 2) { // non terminating symbols
                    if (!rules.containsKey(rule.charAt(0))) return false;
                    if (!rules.containsKey(rule.charAt(1))) return false;
                } else {
                    return false;
                }
                if (rule.contains(emptyWord.toString()) && !clause.getKey().equals(startingSymbol)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setStartSymbol(char startSymbol) {
        this.startingSymbol = startSymbol;
    }

    public Character getStartSymbol() {
        return this.startingSymbol;
    }

    public String getMappingTo(String to) {
        StringBuilder from = new StringBuilder();
        for (Map.Entry<Character, List<String>> clause : rules.entrySet()) {
            if (clause.getValue().contains(to)) from.append(clause.getKey());
        }
        return from.toString();
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "rules=" + rules +
                ", startingSymbol=" + startingSymbol +
                '}';
    }
}
