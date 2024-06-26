package UI.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class State {
    String name;
    boolean isFinal;
    Map<Character, Set<State>> transitions = new HashMap<>();

    public State(String name, boolean isFinal) {
        this.name = name;
        this.isFinal = isFinal;
    }

    void addTransition(char symbol, State state) {
        transitions.computeIfAbsent(symbol, k -> new HashSet<>()).add(state);
    }

    Set<State> getReachableStates(char symbol) {
        return transitions.getOrDefault(symbol, new HashSet<>());
    }
    public boolean isFinal() {
        return isFinal;
    }
    public State getTransition(char symbol) {
        return transitions.getOrDefault(symbol, new HashSet<>()).stream().findFirst().orElse(null);
    }
    public Map<Character, Set<State>> getTransitions() {
    return transitions;
}

    


    @Override
    public String toString() {
        return name + (isFinal ? "(final)" : "");
    }
}
