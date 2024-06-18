import java.util.*;

class NFA {
    Set<String> states;
    Set<String> alphabet;
    Map<String, Map<String, Set<String>>> transitions;
    String startState;
    Set<String> finalStates;

    public NFA(Set<String> states, Set<String> alphabet, Map<String, Map<String, Set<String>>> transitions, String startState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.finalStates = finalStates;
    }
}

class DFA {
    Set<Set<String>> states;
    Set<String> alphabet;
    Map<Set<String>, Map<String, Set<String>>> transitions;
    Set<String> startState;
    Set<Set<String>> finalStates;

    public DFA(Set<Set<String>> states, Set<String> alphabet, Map<Set<String>, Map<String, Set<String>>> transitions, Set<String> startState, Set<Set<String>> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.finalStates = finalStates;
    }
}

public class NFAToDFA {

    public static Set<String> epsilonClosure(NFA nfa, String state) {
        Stack<String> stack = new Stack<>();
        Set<String> closure = new HashSet<>();
        stack.push(state);
        closure.add(state);
        
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (nfa.transitions.containsKey(current) && nfa.transitions.get(current).containsKey("")) {
                for (String nextState : nfa.transitions.get(current).get("")) {
                    if (!closure.contains(nextState)) {
                        closure.add(nextState);
                        stack.push(nextState);
                    }
                }
            }
        }
        
        return closure;
    }

    public static Set<String> move(NFA nfa, Set<String> states, String symbol) {
        Set<String> nextStates = new HashSet<>();
        
        for (String state : states) {
            if (nfa.transitions.containsKey(state) && nfa.transitions.get(state).containsKey(symbol)) {
                nextStates.addAll(nfa.transitions.get(state).get(symbol));
            }
        }
        
        return nextStates;
    }

    public static DFA convertNfaToDfa(NFA nfa) {
        Set<String> initialClosure = epsilonClosure(nfa, nfa.startState);
        Set<String> dfaStartState = new HashSet<>(initialClosure);
        Set<Set<String>> dfaStates = new HashSet<>();
        Queue<Set<String>> unmarkedStates = new LinkedList<>();
        Map<Set<String>, Map<String, Set<String>>> dfaTransitions = new HashMap<>();
        Set<Set<String>> dfaFinalStates = new HashSet<>();
        
        dfaStates.add(dfaStartState);
        unmarkedStates.add(dfaStartState);
        
        while (!unmarkedStates.isEmpty()) {
            Set<String> currentDfaState = unmarkedStates.poll();
            
            for (String symbol : nfa.alphabet) {
                Set<String> nextNfaStates = move(nfa, currentDfaState, symbol);
                if (!nextNfaStates.isEmpty()) {
                    Set<String> nextDfaState = new HashSet<>();
                    for (String state : nextNfaStates) {
                        nextDfaState.addAll(epsilonClosure(nfa, state));
                    }
                    if (!dfaStates.contains(nextDfaState)) {
                        dfaStates.add(nextDfaState);
                        unmarkedStates.add(nextDfaState);
                    }
                    dfaTransitions.putIfAbsent(currentDfaState, new HashMap<>());
                    dfaTransitions.get(currentDfaState).put(symbol, nextDfaState);
                    
                    for (String finalState : nfa.finalStates) {
                        if (nextDfaState.contains(finalState)) {
                            dfaFinalStates.add(nextDfaState);
                        }
                    }
                }
            }
        }
        
        return new DFA(dfaStates, nfa.alphabet, dfaTransitions, dfaStartState, dfaFinalStates);
    }

    public static NFA parseUserInput(int numOfStates, String symbols, String transitionFunctions, String startState, String setOfFinalStates) {
        Set<String> states = new HashSet<>();
        for (int i = 0; i < numOfStates; i++) {
            states.add("q" + i);
        }

        Set<String> alphabet = new HashSet<>(Arrays.asList(symbols.replaceAll("[{}\\s]", "").split(",")));

        Map<String, Map<String, Set<String>>> transitions = new HashMap<>();
        String[] transitionsArray = transitionFunctions.replaceAll("[{}]", "").split(";");
        for (String transition : transitionsArray) {
            String[] parts = transition.split("=>|,");
            String fromState = parts[0].trim();
            String toState = parts[1].trim();
            String symbol = parts[2].trim();

            transitions.putIfAbsent(fromState, new HashMap<>());
            transitions.get(fromState).putIfAbsent(symbol, new HashSet<>());
            transitions.get(fromState).get(symbol).add(toState);
        }

        Set<String> finalStates = new HashSet<>(Arrays.asList(setOfFinalStates.replaceAll("[{}\\s]", "").split(",")));

        return new NFA(states, alphabet, transitions, startState, finalStates);
    }

    public static void main(String[] args) {
        // Example user input
        int numOfStates = 4;
        String symbols = "{a, b}";
        String transitionFunctions = "{q0=>q0,a};{q0=>q0,b};{q0=>q1,b};{q1=>q2,a};{q1=>q2,b};{q1=>q2,ε};{q2=>q3,a};{q2=>q3,b}";
        String startState = "q0";
        String setOfFinalStates = "{q3}";

        NFA nfa = parseUserInput(numOfStates, symbols, transitionFunctions, startState, setOfFinalStates);
        DFA dfa = convertNfaToDfa(nfa);
        
        System.out.println("DFA States: " + dfa.states);
        System.out.println("DFA Alphabet: " + dfa.alphabet);
        System.out.println("DFA Transitions:");
        for (Map.Entry<Set<String>, Map<String, Set<String>>> entry : dfa.transitions.entrySet()) {
            Set<String> state = entry.getKey();
            Map<String, Set<String>> transitions = entry.getValue();
            for (Map.Entry<String, Set<String>> transEntry : transitions.entrySet()) {
                System.out.println("  " + state + " --" + transEntry.getKey() + "--> " + transEntry.getValue());
            }
        }
        System.out.println("DFA Start State: " + dfa.startState);
        System.out.println("DFA Final States: " + dfa.finalStates);
    }
}
