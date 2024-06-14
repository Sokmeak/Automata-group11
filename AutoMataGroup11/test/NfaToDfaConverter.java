/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
import java.util.*;

class NFA {
    Set<Integer> states;
    Set<Character> alphabet;
    Map<Integer, Map<Character, Set<Integer>>> transitions;
    int startState;
    Set<Integer> finalStates;
    
    // Constructor and other methods...
}

class DFA {
    Set<Set<Integer>> states;
    Set<Character> alphabet;
    Map<Set<Integer>, Map<Character, Set<Integer>>> transitions;
    Set<Integer> startState;
    Set<Set<Integer>> finalStates;

    // Constructor and other methods...
}

public class NfaToDfaConverter {
    
    public static DFA convert(NFA nfa) {
        DFA dfa = new DFA();
        dfa.alphabet = nfa.alphabet;

        // Use a queue to perform a BFS on the state space of the DFA
        Queue<Set<Integer>> queue = new LinkedList<>();
        Map<Set<Integer>, Map<Character, Set<Integer>>> dfaTransitions = new HashMap<>();

        // Get the epsilon closure of the NFA start state
        Set<Integer> startState = epsilonClosure(nfa, Collections.singleton(nfa.startState));
        dfa.startState = startState;
        queue.add(startState);
        dfa.states.add(startState);

        while (!queue.isEmpty()) {
            Set<Integer> currentDfaState = queue.poll();
            dfaTransitions.putIfAbsent(currentDfaState, new HashMap<>());

            for (char symbol : nfa.alphabet) {
                Set<Integer> nextDfaState = move(nfa, currentDfaState, symbol);
                nextDfaState = epsilonClosure(nfa, nextDfaState);

                if (!dfa.states.contains(nextDfaState)) {
                    dfa.states.add(nextDfaState);
                    queue.add(nextDfaState);
                }

                dfaTransitions.get(currentDfaState).put(symbol, nextDfaState);
            }
        }

        // Identify final states of the DFA
        for (Set<Integer> dfaState : dfa.states) {
            for (int nfaState : dfaState) {
                if (nfa.finalStates.contains(nfaState)) {
                    dfa.finalStates.add(dfaState);
                    break;
                }
            }
        }

        dfa.transitions = dfaTransitions;
        return dfa;
    }

    private static Set<Integer> epsilonClosure(NFA nfa, Set<Integer> states) {
       Stack<Integer> stack;
       stack = null;
       // stack = new Stack<>(states);
        Set<Integer> closure = new HashSet<>(states);

        while (!stack.isEmpty()) {
            int state = stack.pop();
            if (nfa.transitions.containsKey(state) && nfa.transitions.get(state).containsKey(null)) {
                for (int nextState : nfa.transitions.get(state).get(null)) {
                    if (closure.add(nextState)) {
                        stack.push(nextState);
                    }
                }
            }
        }
        return closure;
    }

    private static Set<Integer> move(NFA nfa, Set<Integer> states, char symbol) {
        Set<Integer> result = new HashSet<>();
        for (int state : states) {
            if (nfa.transitions.containsKey(state) && nfa.transitions.get(state).containsKey(symbol)) {
                result.addAll(nfa.transitions.get(state).get(symbol));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // Example usage
        NFA nfa = new NFA();
        // Initialize the NFA...

        DFA dfa = convert(nfa);
        // Now you have the DFA equivalent of the NFA
    }
}

