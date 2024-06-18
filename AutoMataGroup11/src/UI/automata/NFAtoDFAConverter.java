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

//   public static Automaton minimizeDFA(Automaton dfa) {
//        dfa.removeUnreachableStates();
//        List<Set<State>> partitions = new ArrayList<>();
//        Map<Set<State>, State> partitionToState = new HashMap<>();
//        Automaton minimizedDFA = new Automaton();
//        int stateId = 0;
//
//        // Initialize partitions with final and non-final states
//        Set<State> finalStates = new HashSet<>();
//        Set<State> nonFinalStates = new HashSet<>();
//
//        for (State state : dfa.states.values()) {
//            if (state.isFinal) finalStates.add(state);
//            else nonFinalStates.add(state);
//        }
//
//        if (!finalStates.isEmpty()) partitions.add(finalStates);
//        if (!nonFinalStates.isEmpty()) partitions.add(nonFinalStates);
//
//        // Create new states from partitions
//        for (Set<State> partition : partitions) {
//            boolean isFinal = false;
//            for (State state : partition) {
//                if (state.isFinal) {
//                    isFinal = true;
//                    break;
//                }
//            }
//            State newState = new State("q" + stateId++, isFinal);
//            partitionToState.put(partition, newState);
//            minimizedDFA.addState(newState.name, newState.isFinal);
//        }
//
//        // Define transitions for new states based on old transitions
//        for (Set<State> partition : partitions) {
//            State newState = partitionToState.get(partition);
//            for (State oldState : partition) {
//                for (Map.Entry<Character, Set<State>> entry : oldState.transitions.entrySet()) {
//                    char symbol = entry.getKey();
//                    for (State targetState : entry.getValue()) {
//                        Set<State> targetPartition = partitions.stream()
//                            .filter(p -> p.contains(targetState))
//                            .findFirst()
//                            .orElse(null);
//                        if (targetPartition != null) {
//                            newState.addTransition(symbol, partitionToState.get(targetPartition));
//                        }
//                    }
//                }
//            }
//        }
//
//        minimizedDFA.setStartState(partitionToState.get(
//            partitions.stream().filter(p -> p.contains(dfa.startState)).findFirst().orElse(null)
//        ));
//
//        return minimizedDFA;
//    }
    /* public static Automaton2 minimizeDFA(Automaton2 dfa) {
        dfa.removeUnreachableStates();

        // Initialize partitions
        Set<State> finalStates = new HashSet<>(dfa.finalStates);
        Set<State> nonFinalStates = new HashSet<>(dfa.states.values());
        nonFinalStates.removeAll(finalStates);

        List<Set<State>> partitions = new ArrayList<>();
        if (!nonFinalStates.isEmpty()) partitions.add(nonFinalStates);
        if (!finalStates.isEmpty()) partitions.add(finalStates);

        boolean updated;
        do {
            updated = false;
            List<Set<State>> newPartitions = new ArrayList<>();

            for (Set<State> partition : partitions) {
                Map<Map<Set<State>, Character>, Set<State>> transitionGroups = new HashMap<>();

                for (State state : partition) {
                    Map<Set<State>, Character> transitions = new HashMap<>();
                    for (char symbol : dfa.alphabet) {
                        Set<State> reachableStates = state.getReachableStates(symbol);
                        Set<State> representativePartition = null;
                        for (Set<State> p : partitions) {
                            if (!reachableStates.isEmpty() && p.containsAll(reachableStates)) {
                                representativePartition = p;
                                break;
                            }
                        }
                        transitions.put(representativePartition, symbol);
                    }

                    transitionGroups.computeIfAbsent(transitions, k -> new HashSet<>()).add(state);
                }

                newPartitions.addAll(transitionGroups.values());
                if (transitionGroups.size() > 1) {
                    updated = true;
                }
            }

            partitions = newPartitions;
        } while (updated);

        // Create the minimized DFA
        Automaton2 minimizedDFA = new Automaton2();
        minimizedDFA.alphabet = new HashSet<>(dfa.alphabet);

        Map<Set<State>, State> newStateMap = new HashMap<>();
        int stateCounter = 0;
        for (Set<State> partition : partitions) {
            State representative = partition.iterator().next();
            boolean isFinal = partition.stream().anyMatch(s -> s.isFinal);
            State newState = new State("q" + stateCounter++, isFinal);
            newStateMap.put(partition, newState);
            minimizedDFA.addState(newState.name, newState.isFinal);

            if (partition.contains(dfa.startState)) {
                minimizedDFA.setStartState(newState);
            }
        }

        for (Map.Entry<Set<State>, State> entry : newStateMap.entrySet()) {
            Set<State> oldStates = entry.getKey();
            State newState = entry.getValue();

            for (char symbol : minimizedDFA.alphabet) {
                Set<State> oldTargetStates = new HashSet<>();
                for (State oldState : oldStates) {
                    oldTargetStates.addAll(oldState.getReachableStates(symbol));
                }

                for (Set<State> partition : partitions) {
                    if (partition.containsAll(oldTargetStates)) {
                        State newTargetState = newStateMap.get(partition);
                        newState.addTransition(symbol, newTargetState);
                        break;
                    }
                }
            }
        }

        return minimizedDFA;
    }
     */
    public static Automaton2 minimizeDFA(Automaton2 dfa) {
        dfa.removeUnreachableStates();

        Set<State> finalStates = new HashSet<>(dfa.finalStates);
        Set<State> nonFinalStates = new HashSet<>(dfa.states.values());
        nonFinalStates.removeAll(finalStates);

        List<Set<State>> partitions = new ArrayList<>();
        if (!nonFinalStates.isEmpty()) {
            partitions.add(nonFinalStates);
        }
        if (!finalStates.isEmpty()) {
            partitions.add(finalStates);
        }

        boolean updated;
        do {
            updated = false;
            List<Set<State>> newPartitions = new ArrayList<>();

            for (Set<State> partition : partitions) {
                Map<Map<Set<State>, Character>, Set<State>> transitionGroups = new HashMap<>();
                for (State state : partition) {
                    Map<Set<State>, Character> transitions = new HashMap<>();
                    for (char symbol : dfa.alphabet) {
                        Set<State> reachableStates = state.getReachableStates(symbol);
                        Set<State> representativePartition = partitions.stream()
                                .filter(p -> p.containsAll(reachableStates))
                                .findFirst()
                                .orElse(null);
                        transitions.put(representativePartition, symbol);
                    }
                    transitionGroups.computeIfAbsent(transitions, k -> new HashSet<>()).add(state);
                }
                newPartitions.addAll(transitionGroups.values());
                if (transitionGroups.size() > 1) {
                    updated = true;
                }
            }
            partitions = newPartitions;
        } while (updated);

        Automaton2 minimizedDFA = new Automaton2();
        minimizedDFA.alphabet = new HashSet<>(dfa.alphabet);

        Map<Set<State>, State> newStateMap = new HashMap<>();
        int stateCounter = 0;
        State startState = null;
        boolean startStateAssigned = false;

        for (Set<State> partition : partitions) {
            State newState = new State("q" + stateCounter++, partition.stream().anyMatch(State::isFinal));
            if (partition.contains(dfa.startState) && !startStateAssigned) {
                minimizedDFA.setStartState(newState);
                startStateAssigned = true; // Ensure the start state is set once
            }
            newStateMap.put(partition, newState);
            minimizedDFA.addState(newState.name, newState.isFinal);
        }

        newStateMap.values().forEach(state -> minimizedDFA.addState(state.name, state.isFinal));
        if (startState != null) {
            minimizedDFA.setStartState(startState);
        } else {
            minimizedDFA.setStartState(newStateMap.get(partitions.get(0))); // Default to first partition if not specified
        }

        Map<Set<State>, State> finalNewStateMap = newStateMap; // Ensure the reference is effectively final.
        List<Set<State>> finalPartitions = partitions; // Ensure the reference is effectively final.

        finalNewStateMap.entrySet().forEach(entry -> {
            entry.getKey().forEach(oldState -> {
                oldState.getTransitions().forEach((symbol, targets) -> {
                    targets.forEach(target -> {
                        Set<State> targetPartition = null;
                        for (Set<State> partition : finalPartitions) { // Use the effectively final reference
                            if (partition.contains(target)) {
                                targetPartition = partition;
                                break;
                            }
                        }

                        if (targetPartition != null) {
                            State newTargetState = finalNewStateMap.get(targetPartition);
                            if (newTargetState != null) {
                                finalNewStateMap.get(entry.getKey()).addTransition(symbol, newTargetState);
                            }
                        }
                    });
                });
            });
        });

        minimizedDFA.finalStates.clear();  // Clear existing entries if any
        for (Set<State> partition : partitions) {
            State newState = newStateMap.get(partition);
            if (partition.stream().anyMatch(State::isFinal)) {
                minimizedDFA.finalStates.add(newState);  // Adds only unique states
            }
        }

        return minimizedDFA;
    }

}
