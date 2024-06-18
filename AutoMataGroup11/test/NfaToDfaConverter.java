
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class State {

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

//    Set<State> getReachableStates(char symbol) {
//        return transitions.getOrDefault(symbol, new HashSet<>());
//    }
    Set<State> getReachableStates(char symbol) {
        System.out.println("Check transitions map: " + transitions);
        System.out.println("Getting reachable states for symbol '" + symbol + "' from state " + name + ": " + transitions.getOrDefault(symbol, new HashSet<>()));
        return transitions.getOrDefault(symbol, new HashSet<>());
    }

}

class Automaton {

    Set<Character> alphabet = new HashSet<>();
    Map<String, State> states = new HashMap<>();
    State startState;
    Set<State> finalStates = new HashSet<>();

    void addState(String name, boolean isFinal) {
        State state = new State(name, isFinal);
        states.put(name, state);
        if (isFinal) {
            finalStates.add(state);
        }
    }

    void setStartState(State startState) {
        this.startState = startState;
    }

    void addTransition(String from, String to, char symbol) {
        State fromState = states.get(from);
        State toState = states.get(to);
        fromState.addTransition(symbol, toState);
        System.out.println("Added transition from " + from + " to " + to + " via " + symbol);
    }

    // Parsing transitions from a formatted string
    void parseTransitions(String transitionFunctions) {
        Matcher m = Pattern.compile("\\{(.*?),(.*?)\\};").matcher(transitionFunctions);
        while (m.find()) {
            String from = m.group(1).split("=>")[0];
            String to = m.group(1).split("=>")[1];
            char symbol = m.group(2).charAt(0);
            addTransition(from, to, symbol);
            alphabet.add(symbol);
        }
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        
//        System.out.println("The equivalent FA: ");
//        sb.append("Number of States: ").append(states.size()).append(";\n");
//        sb.append("Alphabet: {").append(alphabet.stream().map(Object::toString).collect(Collectors.joining(", "))).append("};\n");
//        sb.append("Transition Functions: \"");
//
//        // Ensure the transition is sorted and consistent
//        List<String> transitions = new ArrayList<>();
//
//        System.out.println(states.keySet());
//        states.values().forEach(s -> System.out.println(s));
//        states.values().forEach(state -> {
//            alphabet.forEach(symbol -> {
//                System.out.println(symbol);
//                //Set<State> targets = state.getReachableStates(symbol);
//                Set<State> targets = state.getReachableStates(symbol);
//
//                // This function should return key-value paired
//                if (targets == null) {
//                    System.out.println("Warning: No reachable states from state " + state.name + " on symbol '" + symbol + "'");
//                } else {
//                    targets.forEach(target -> {
//                        String transition = "{" + state.name + "=>" + target.name + "," + symbol + "}";
//                       
//                        transitions.add(transition);
//                        System.out.println("Added transition: " + transition);
//                    });
//                }
//            });
//        });
//
//        // Join all transitions with a semicolon
//        sb.append(String.join(";", transitions));
//        sb.append("\";\n");
//
//        sb.append("Start State: ").append(startState.name).append(";\n");
//        sb.append("Final States: {").append(finalStates.stream().map(state -> state.name).collect(Collectors.joining(", "))).append("};\n");
//
//        return sb.toString();
//    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of States: ").append(states.size()).append(";\n");
        sb.append("Alphabet: {").append(alphabet).append("};\n");
        sb.append("Transition Functions: ");

        for (State state : states.values()) {
            for (Character symbol : alphabet) {
                Set<State> reachableStates = state.getReachableStates(symbol);
                if (!reachableStates.isEmpty()) {
                    reachableStates.forEach(target -> sb.append(state.name + " --" + symbol + "--> " + target.name + "; "));
                }
            }
        }
        sb.append("\nStart State: ").append(startState.name).append(";\n");
        sb.append("Final States: {").append(finalStates.stream().map(state -> state.name).collect(Collectors.joining(", "))).append("};\n");

        return sb.toString();
    }

}

public class NFAtoDFAConverter {

//    public static Automaton buildAutomaton(String numOfStates, String symbols, String transitionFunctions, String startStateInput, String setOfFinalStates) {
//        Automaton automaton = new Automaton();
//
//        // Parse symbols and remove curly braces
//        String[] symbolsArray = symbols.replaceAll("[{}]", "").split(", ");
//        for (String symbol : symbolsArray) {
//            if (!symbol.equals("ε")) {  // Skip epsilon if present
//                automaton.alphabet.add(symbol.charAt(0));
//            }
//        }
//
//        // Parse number of states
//        int numStates = Integer.parseInt(numOfStates.split(" = ")[1].trim());
//        for (int i = 0; i < numStates; i++) {
//            automaton.addState("q" + i, setOfFinalStates.contains("q" + i));
//        }
//
//        // Set start state
//        String startStateIdentifier = startStateInput.split("=")[1].trim().replace("\"", "");
//        State startState = automaton.states.get(startStateIdentifier);
//        if (startState == null) {
//            throw new IllegalArgumentException("Start state " + startStateIdentifier + " does not exist in the automaton");
//        }
//
//        // Parse transitions
//        automaton.parseTransitions(transitionFunctions);
//
//        return automaton;
//    }
    public static void main(String[] args) {
        // Example initialization based on the provided format

//        
//         String numOfStates = "numOfStates = 4";
//        String symbols = "{a, b}";
//        String transitionFunctions = "{q0=>q0,a};{q0=>q0,b};{q0=>q1,b};{q1=>q2,a};{q1=>q2,b};{q1=>q2,ε};{q2=>q3,a};{q2=>q3,b};";
//        String startState = "String startState = \"q0\"";
//        String setOfFinalStates = "{q3}";
        // Automaton automaton = buildAutomaton(numOfStates, symbols, transitionFunctions, startState, setOfFinalStates);
       
     Automaton automaton = new Automaton();
         automaton.addState("q0", false);
         automaton.addState("q1", true);
        automaton.addTransition("q0", "q1", 'a');
  

        
        
        Automaton nfa = new Automaton();

        nfa.addState("q0", false);
        nfa.addState("q1", false);
        nfa.addState("q2", false);
        nfa.addState("q3", false);
        nfa.addState("q4", true);  // Final state

        String transitionFunctions = "{q0=>q0,a};{q0=>q0,b};{q0=>q1,a};{q1=>q2,b};{q2=>q3,b};{q3=>q3,a};{q2=>q3,b};{q3=>q4,b};;{q3=>q4,a}";
        nfa.parseTransitions(transitionFunctions);

        nfa.startState = nfa.states.get("q0"); // set as default

        //   System.out.println(nfa.toString());
//        nfa.addTransition("q0", "q1", 'a');
//        nfa.addTransition("q1", "q2", 'b');
// Make sure these lines or similar are in your setup or initialization code and are being executed.
        // Convert NFA to DFA
        // This is a placeholder; actual conversion logic needs to be implemented
        Automaton dfa = convertNFAtoDFA(nfa);

        System.out.println(dfa.toString());

        // Output DFA states and transitions
        // Placeholder for output logic
    }

    static Automaton convertNFAtoDFA(Automaton nfa) {
        Automaton dfa = new Automaton();
        Map<Set<State>, State> dfaStates = new HashMap<>();
        Queue<Set<State>> queue = new LinkedList<>();

        // Initialize DFA alphabet from NFA alphabet (excluding ε)
        for (char symbol : nfa.alphabet) {
            if (symbol != '\u03B5') {  // Exclude epsilon
                dfa.alphabet.add(symbol);
            }
        }

        Set<State> startSet = epsilonClosure(Collections.singleton(nfa.startState));
        State dfaStartState = new State("q" + dfa.states.size(), startSet.stream().anyMatch(s -> s.isFinal));
        dfa.addState(dfaStartState.name, dfaStartState.isFinal);
        dfa.setStartState(dfaStartState);
        dfaStates.put(startSet, dfaStartState);
        queue.add(startSet);

        while (!queue.isEmpty()) {
            Set<State> currentSet = queue.poll();
            State currentDFAState = dfaStates.get(currentSet);

            for (char symbol : dfa.alphabet) {  // Use DFA alphabet here
                Set<State> newSet = new HashSet<>();
                for (State state : currentSet) {
                    Set<State> targetStates = epsilonClosure(state.getReachableStates(symbol));
                    System.out.println(targetStates.toString());
                    newSet.addAll(targetStates);
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

    static Set<State> epsilonClosure(Set<State> states) {
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

}
