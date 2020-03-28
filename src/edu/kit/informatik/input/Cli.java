package edu.kit.informatik.input;

import edu.kit.informatik.Cyk;
import edu.kit.informatik.Grammar;

import java.util.Scanner;

public class Cli {
    private static String chomskyClauseRegex = "[A-z]->([A-z]?([A-z])\\|)*[A-z]?([A-z])";
    private static String clauseRegex = "[A-z]->([A-z]+\\|)*[A-z]";
    private static String rulesRegex = String.format("(%s,)*%s", chomskyClauseRegex, chomskyClauseRegex);

    private Scanner scanner = new Scanner(System.in);

    public void doSession() {
        Grammar grammar;
        while (true) {
            System.out.println("Input Rules. e is the empty word. The first rule is assumed to map the start symbol." +
                    " E.g. S->AB|e|a,A->AA|a,B->b");
            String input = scanner.nextLine();
            if (input.equals("quit") || input.equals("exit")) return;
            if (!input.matches(rulesRegex)) {
                System.out.println("Invalid syntax");
                continue;
            }
            grammar = Parser.parse(input);
            if (!grammar.isChomsky()) {
                System.out.println("The provided grammar is not in Chomsky normal form.");
                continue;
            }
            break;
        }
        String command;
        Cyk cyk = new Cyk(grammar);
        while (true) { // loop as long input is not quit or exit
            System.out.println("Check if a word is in the grammar by typing check <word>. Or type quit to exit.");
            command = scanner.nextLine();
            if (command.equals("quit") || command.equals("exit")) break;
            String[] split = command.split(" ");
            if (split.length != 2) { // good for now
                System.out.println("Invalid input");
                continue;
            }
            String toCheck = split[1];
            if (!toCheck.equals("e")) {
                toCheck = toCheck.replace("e", "");
            }
            System.out.println(cyk.check(toCheck));
            if (!toCheck.equals("e")) {
                System.out.println(cyk.toString());
            }
        }
    }
}
