package UI.automata;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Automaton2 {
    Set<Character> alphabet = new HashSet<>();
    Map<String, State> states = new HashMap<>();
    State startState;
    Set<State> finalStates = new HashSet<>();

    public void addState(String name, boolean isFinal) {
        State state = new State(name, isFinal);
        states.put(name, state);
        if (isFinal) {
            finalStates.add(state);
        }
    }

    public void setStartState(State startState) {
        this.startState = startState;
    }

    public void addTransition(String from, String to, char symbol) {
        State fromState = states.get(from);
        State toState = states.get(to);
        fromState.addTransition(symbol, toState);
        alphabet.add(symbol);
    }

    public void parseTransitions(String transitionFunctions) {
        Matcher m = Pattern.compile("\\{(q\\d+=>q\\d+),(.)\\};").matcher(transitionFunctions);
        while (m.find()) {
            String[] parts = m.group(1).split("=>");
            String from = parts[0];
            String to = parts[1];
            char symbol = m.group(2).charAt(0);
            addTransition(from, to, symbol);
        }
    }

    public void removeUnreachableStates() {
        Set<State> reachable = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        queue.add(startState);
        reachable.add(startState);

        while (!queue.isEmpty()) {
            State state = queue.poll();
            for (char symbol : alphabet) {
                for (State target : state.getReachableStates(symbol)) {
                    if (reachable.add(target)) {
                        queue.add(target);
                    }
                }
            }
        }
        
        
         System.out.println(" ====>  Unreachable states being removed:");
        states.values().forEach(state -> {
            if (!reachable.contains(state)) {
                System.out.println(state.name);
            }
        });


        states.values().removeIf(state -> !reachable.contains(state));
        


        
        finalStates.removeIf(state -> !reachable.contains(state));
        
         System.out.println("\n====> Reachable states:");
        states.values().forEach(state -> {
            System.out.println(state.name);
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of States: ").append(states.size()).append(";\n");
        sb.append("Alphabet: {").append(alphabet.stream().map(Object::toString).collect(Collectors.joining(", "))).append("};\n");
        sb.append("Transition Functions: \"");

        List<String> transitions = new ArrayList<>();
        System.out.println("The set of states: ");
        states.keySet().forEach(s->System.out.println(s));
        states.values().forEach(state -> {
            alphabet.forEach(symbol -> {
                Set<State> targets = state.getReachableStates(symbol);
                targets.forEach(target -> {
                    transitions.add("{" + state.name + "=>" + target.name + "," + symbol + "}");
                });
            });
        });

        sb.append(String.join(";", transitions)).append("\";\n");
        sb.append("Start State: ").append(startState.name).append(";\n");
        sb.append("Final States: {").append(finalStates.stream().map(state -> state.name).collect(Collectors.joining(", "))).append("};\n");
        return sb.toString();
    }
}
