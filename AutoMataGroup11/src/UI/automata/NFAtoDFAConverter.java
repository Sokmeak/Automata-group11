package UI.automata;

import java.util.*;

public class NFAtoDFAConverter {

    public static Automaton2 buildAutomaton(String numOfStates, String symbols, String transitionFunctions, String startStateInput, String setOfFinalStates) {
        Automaton2 automaton = new Automaton2();

        String[] symbolsArray = symbols.replaceAll("[{}]", "").split(", ");
        for (String symbol : symbolsArray) {
            if (!symbol.equals("ε")) {
                automaton.alphabet.add(symbol.charAt(0));
            }
        }

        int numStates = Integer.parseInt(numOfStates);
        for (int i = 0; i < numStates; i++) {
            automaton.addState("q" + i, setOfFinalStates.contains("q" + i));
        }

        String startStateIdentifier = startStateInput.replaceAll(".*?\"(.*?)\".*", "$1");
        State startState = automaton.states.get(startStateIdentifier);
        if (startState == null) {
            throw new IllegalArgumentException("Start state " + startStateIdentifier + " does not exist in the automaton");
        }
        automaton.setStartState(startState);

        automaton.parseTransitions(transitionFunctions);

        return automaton;
    }

    public static Automaton2 convertNFAtoDFA(Automaton2 nfa) {
        Automaton2 dfa = new Automaton2();
        Map<Set<State>, State> dfaStates = new HashMap<>();
        Queue<Set<State>> queue = new LinkedList<>();

        for (char symbol : nfa.alphabet) {
            if (symbol != '\u03B5') {
                dfa.alphabet.add(symbol);
            }
        }

        Set<State> startSet = epsilonClosure(Collections.singleton(nfa.startState));
        State dfaStartState = new State("q0", startSet.stream().anyMatch(s -> s.isFinal));
        dfa.addState(dfaStartState.name, dfaStartState.isFinal);
        dfa.setStartState(dfaStartState);
        dfaStates.put(startSet, dfaStartState);
        queue.add(startSet);

        while (!queue.isEmpty()) {
            Set<State> currentSet = queue.poll();
            State currentDFAState = dfaStates.get(currentSet);

            for (char symbol : dfa.alphabet) {
                Set<State> newSet = new HashSet<>();
                for (State state : currentSet) {
                    newSet.addAll(epsilonClosure(state.getReachableStates(symbol)));
                }

                if (!dfaStates.containsKey(newSet)) {
                    State newState = new State("q" + dfa.states.size(), newSet.stream().anyMatch(s -> s.isFinal));
                    dfa.addState(newState.name, newState.isFinal);
                    dfaStates.put(newSet, newState);
                    queue.add(newSet);
                }

                currentDFAState.addTransition(symbol, dfaStates.get(newSet));
            }
        }

        return dfa;
    }

    public static Set<State> epsilonClosure(Set<State> states) {
        Set<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        states.forEach(stack::push);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            state.getReachableStates('\u03B5').forEach(nextState -> {
                if (closure.add(nextState)) {
                    stack.push(nextState);
                }
            });
        }
        return closure;
    }

    public static Automaton2 minimizeDFA(Automaton2 dfa) {
        dfa.removeUnreachableStates();

        Map<State, Set<State>> partitions = new HashMap<>();
        Set<State> nonFinalStates = new HashSet<>(dfa.states.values());
        nonFinalStates.removeAll(dfa.finalStates);
        partitions.put(null, nonFinalStates); // Non-final states
        for (State finalState : dfa.finalStates) {
            partitions.put(finalState, new HashSet<>(Collections.singleton(finalState)));
        }

        boolean updated;
        do {
            updated = false;
            for (char symbol : dfa.alphabet) {
                Map<Set<State>, Set<State>> newPartitions = new HashMap<>();
                for (Set<State> partition : partitions.values()) {
                    Map<Set<State>, Set<State>> symbolPartitions = new HashMap<>();
                    for (State state : partition) {
                        Set<State> targetPartition = partitions.get(state.getReachableStates(symbol).stream().findFirst().orElse(null));
                        symbolPartitions.computeIfAbsent(targetPartition, k -> new HashSet<>()).add(state);
                    }
                    newPartitions.putAll(symbolPartitions);
                }

                if (newPartitions.size() > partitions.size()) {
                    partitions = new HashMap<>();
                    for (Set<State> newPartition : newPartitions.values()) {
                        for (State state : newPartition) {
                            partitions.put(state, newPartition);
                        }
                    }
                    updated = true;
                }
            }
        } while (updated);

        Automaton2 minimizedDFA = new Automaton2();
        minimizedDFA.alphabet = new HashSet<>(dfa.alphabet); // Maintain the alphabet
        Map<Set<State>, State> newStates = new HashMap<>();
        for (Set<State> partition : new HashSet<>(partitions.values())) {
            State newState = new State("q" + newStates.size(), partition.stream().anyMatch(s -> s.isFinal));
            newStates.put(partition, newState);
            minimizedDFA.addState(newState.name, newState.isFinal);
            if (partition.contains(dfa.startState)) {
                minimizedDFA.setStartState(newState);
            }
        }

        for (Map.Entry<Set<State>, State> entry : newStates.entrySet()) {
            Set<State> partition = entry.getKey();
            State newState = entry.getValue();
            for (char symbol : dfa.alphabet) {
                Set<State> targetPartition = partitions.get(partition.stream().flatMap(s -> s.getReachableStates(symbol).stream()).findFirst().orElse(null));
                newState.addTransition(symbol, newStates.get(targetPartition));
            }
        }

        return minimizedDFA;
    }
}
