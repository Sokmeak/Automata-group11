package UI.automata;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class UI_automata1 extends javax.swing.JFrame {

//    public static String nameSelect1 = "";
//    public static String dateSelect1 = "";
    public static String startState = "q0";

    public Color SetColor1() {
        return new Color(104, 105, 106);
    }

    public String[] renderStates(int numState) {
        String[] stateGenerate = new String[numState];
        for (int i = 0; i < numState; i++) {
            stateGenerate[i] = "q" + i;
        }

        return stateGenerate;
    }

    public String[] exTractSymbols(String sym) {
        String[] syms = {};

        if (sym.startsWith("{") && sym.endsWith("}")) {

            sym = sym.substring(1, sym.length() - 1);
        }

        syms = sym.split(",");

        return syms;
    }

//    String str, int numState, String [] tranFunction, String startState, String finalState
     public boolean testString(String str, int numState, String[] tranFunction, String startState, String finalState) {
        Set<String> finalStates = new HashSet<>(Arrays.asList(finalState.split(","))); // Handles multiple final states
        String[] states = renderStates(numState);
        String currentState = startState;

        for (int i = 0; i < str.length(); i++) {
            String testSymbol = String.valueOf(str.charAt(i));
            boolean transitionFound = false;

            for (int j = 0; j < states.length; j++) {
                String transitionPattern = currentState + "=>" + states[j] + "," + testSymbol;

                for (String tran : tranFunction) {
                    if (transitionPattern.equals(tran)) {
                        currentState = states[j];
                        transitionFound = true;
                        break;
                    }
                }

                if (transitionFound) {
                    break;
                }
            }

            // Break early if we reach a final state
            if (finalStates.contains(currentState)) {
              //  System.out.println("Accept!");
                return true;
            }
        }

        // If no final state is reached after processing the entire string
       
        return false;
    }

    // Check if there are any state transfrom via epsilon symbol
    public ArrayList<String> getepsilonTransition(String tranFunction, int numState) {
        //String[] transitionWithEp = {};
        String[] tranFunctionSplited = tranFunction.split(";", 0);
        // System.out.println("All Transition functions");

        String[] stateGenerate = new String[numState];
        for (int i = 0; i < numState; i++) {
            stateGenerate[i] = "q" + i;
        }
        // eps
        char epsilon = '\u03B5';
        String completeEpsilonSymbol = String.valueOf(epsilon);
        List<String> transitionFunctionByGenerate = new ArrayList<>();
        ArrayList<String> transitionMatched = new ArrayList<>();

        //  System.out.println("All Generate : ");
        for (int i = 0; i < numState; i++) {
            for (int j = 0; j < numState; j++) {
                String tranRander = "{" + stateGenerate[i] + "=>" + stateGenerate[j] + "," + completeEpsilonSymbol + "}";
                transitionFunctionByGenerate.add(tranRander);
            }
        }

        // System.out.println(transitionFunctionByGenerate.size());
        int size1 = transitionFunctionByGenerate.size();
        int size2 = tranFunctionSplited.length;

        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                String s1 = transitionFunctionByGenerate.get(i);
                String s2 = tranFunctionSplited[j];

                if (s1.equals(s2)) {
                    transitionMatched.add(s2);
                }

            }
        }
        
        

        return transitionMatched;
    }
    
    public String[] renderNewStateP(){
        String []newStates = new String[20];
        
        // initial 20 states just in case
        for(int i=0 ; i<20;i++){
            newStates[i] = "p"+i;
        }
        
       return newStates;
        
        
    }
    
    
    // get date from input field
    
    public Automaton getUserInput(){
        int numState = Integer.parseInt(setOfStatesField.getText());
        String setOfSymbol = setOfSymbolsField.getText();
        String transitionFunction = transitionFunctionField.getText();
        String startState = startStateField.getText();
        String setFinalState = setOfFinalStatesFields.getText();
        
        
        String dfName = headerName.getText();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        
        
        Automaton auto = new Automaton();
        
        auto.setNumberOfState(numState);
        auto.setSetofSymbols(setOfSymbol);
        auto.setTransitionFunctions(transitionFunction);
        auto.setStartState(startState);
        auto.setSetofFinalStates(setFinalState);
        auto.setName(dfName);
        auto.setDate(date);

        
        return auto;

    }
 
    public String[] exTractStateContainEpsilon(String transition[]) {
        int size = transition.length;
        String[] SetofStatesContainEpsilon = new String[size];

        for (int i = 0; i < size; i++) {
            SetofStatesContainEpsilon[i] = transition[i].substring(1, 3);
        }

        return SetofStatesContainEpsilon;
    }

    public String[] exTractNextState(String transition[]) {
        int size = transition.length;
        List<String> SetofNextStates = new ArrayList<>();
        int j = 0;

        for (int i = 0; i < size; i++) {
            String newState = transition[i].substring(5, 7);
            if (newState.equals(startState)) {
                // nothing to do

            } else {

                SetofNextStates.add(transition[i].substring(5, 7));

            }

        }

        String[] SetofNextStatesAsarrSting = SetofNextStates.toArray(new String[0]);

        return SetofNextStatesAsarrSting;
    }

    public String[] newTransitionFunction(String symBols, int numState, String tranFunction, String startState, String finalState) {

        return null;
    }

    public String[] newsetOfFinalState(String symBols, int numState, String tranFunction, String startState, String finalState) {

        return null;
    }

    public String[] newsetStartState(String symBols, int numState, String tranFunction, String startState, String finalState) {

        return null;
    }

    public String[] epsilonTransition(String symBols, int numState, String tranFunction, String startState, String finalState) {

        // object that we want to return in this functions
        String[] newSetStates = {"p0", "p1", "p2", "p3", "p4"};

        String[] tranFunctionSplited = tranFunction.split(";", 0);
//        // System.out.println("All Transition functions");
//
//        String[] stateGenerate = new String[numState];
//        for (int i = 0; i < numState; i++) {
//            stateGenerate[i] = "q" + i;
//        }
//        // eps
//        char epsilon = '\u03B5';
//        String completeEpsilonSymbol = String.valueOf(epsilon);
//        List<String> transitionFunctionByGenerate = new ArrayList<>();
//        List<String> transitionMatchedEpsilon = new ArrayList<>();
//
//        //  System.out.println("All Generate : ");
//        for (int i = 0; i < numState; i++) {
//            for (int j = 0; j < numState; j++) {
//                String tranRander = "{" + stateGenerate[i] + "=>" + stateGenerate[j] + "," + completeEpsilonSymbol + "}";
//                transitionFunctionByGenerate.add(tranRander);
//            }
//        }
//
//        // System.out.println(transitionFunctionByGenerate.size());
//        int size1 = transitionFunctionByGenerate.size();
//        int size2 = tranFunctionSplited.length;
//
//        for (int i = 0; i < size1; i++) {
//            for (int j = 0; j < size2; j++) {
//                String s1 = transitionFunctionByGenerate.get(i);
//                String s2 = tranFunctionSplited[j];
//
//                if (s1.equals(s2)) {
//                    transitionMatchedEpsilon.add(s2);
//                }
//
//            }
//        }

        ArrayList<String> transitionMatchedEpsilon = getepsilonTransition(tranFunction, numState);
        
        // This function returns transition functions go with epsilon
        
        /// Let try with normal transition, 

        String p0 = "q0"; // default
        // find the set of state  move by symbol a
        // chage to variable next time
        
        
        
        List<Integer> indexContainq0Witha = new ArrayList<>();
        List<Integer> indexContainq0Withb = new ArrayList<>();
        for (int i = 0; i < tranFunctionSplited.length; i++) {
            if (tranFunctionSplited[i].contains(p0) && tranFunctionSplited[i].contains("a")) {
                indexContainq0Witha.add(i);
            }
        }

        for (int i = 0; i < tranFunctionSplited.length; i++) {
            if (tranFunctionSplited[i].contains(p0) && tranFunctionSplited[i].contains("b")) {
                indexContainq0Withb.add(i);
            }
        }

        List<String> setTransitionForEachSymbola = new ArrayList<>();
        List<String> setTransitionForEachSymbolb = new ArrayList<>();

        for (Integer i : indexContainq0Witha) {

            setTransitionForEachSymbola.add(tranFunctionSplited[i]);
        }

        for (Integer i : indexContainq0Withb) {

            setTransitionForEachSymbolb.add(tranFunctionSplited[i]);
        }

        // get next state here 
        String[] ToarrsetTransitionForEachSymbola = setTransitionForEachSymbola.toArray(new String[0]);
        String[] ToarrsetTransitionForEachSymbolb = setTransitionForEachSymbolb.toArray(new String[0]);

        String[] startTemp = {startState};
        String[] stateTempForEachSymA = exTractNextState(ToarrsetTransitionForEachSymbola);

        String[] stateTempForEachSymB = exTractNextState(ToarrsetTransitionForEachSymbolb);

        if (stateTempForEachSymA == null) {
            stateTempForEachSymA = new String[0]; // Handle null return value
        }

        if (stateTempForEachSymB == null) {
            stateTempForEachSymB = new String[0]; // Handle null return value
        }

        int numOfnextStepA = startTemp.length + stateTempForEachSymA.length;
        int numOfnextStepB = startTemp.length + stateTempForEachSymB.length;



        String[] nextStateA = new String[numOfnextStepA];
        String[] nextStateB = new String[numOfnextStepB];

        System.arraycopy(startTemp, 0, nextStateA, 0, startTemp.length);
        System.arraycopy(stateTempForEachSymA, 0, nextStateA, startTemp.length, stateTempForEachSymA.length);

        System.arraycopy(startTemp, 0, nextStateB, 0, startTemp.length);
        System.arraycopy(stateTempForEachSymB, 0, nextStateB, startTemp.length, stateTempForEachSymB.length);

        System.out.println("Set of new State go with symbol a:");
//        for (int j = 0; j < nextStateA.length; j++) {
//            System.out.println(nextStateA[j]);
//        }

        System.out.println("Set of new State go with symbol b:");
//        for (int j = 0; j < nextStateB.length; j++) {
//            System.out.println(nextStateB[j]);
//        }
//
//        System.out.println("All Match : ");
//        for (String k : transitionMatchedEpsilon) {
//            System.out.println(k);
//        }

        String[] statesArray = transitionMatchedEpsilon.toArray(new String[0]);

        String[] transitionEp = exTractStateContainEpsilon(statesArray);

        System.out.println("=> The states That Constain epsilon transiton: ");
//        for (String s : transitionEp) {
//
//            System.out.println(s);
//        }

        //   System.out.println("All states contain epsilon transition: ");
        if (transitionMatchedEpsilon.isEmpty()) {
            //  System.out.println("No match Transition!");
        }

        /// Check condition for epsilon transition
        boolean containEpsilonforA = false;
        boolean containEpsilonforB = false;

        String[] toArrtransitionMatched = transitionMatchedEpsilon.toArray(new String[0]);
        String[] nextEp = exTractNextState(toArrtransitionMatched);
        String[] currentEp = exTractStateContainEpsilon(toArrtransitionMatched);

        int lenghtNextA = nextStateA.length;
        int lenghtCurEp = currentEp.length;

        int lenghtNextB = nextStateB.length;
        //   int lenghtCurEp = currentEp.length;
        // find new set of states for each symbol
        String[] newNextA = {};
        String[] newNextB = {};

        for (int t = 0; t < lenghtNextA; t++) {

            for (int r = 0; r < lenghtCurEp; r++) {
                boolean isEqual = nextStateA[t].equals(currentEp[r]);
                if (isEqual) {
                    containEpsilonforA = true;

                } else {

                    containEpsilonforA = false;

                }

            }

        }

        for (int t = 0; t < lenghtNextB; t++) {

            for (int r = 0; r < lenghtCurEp; r++) {
                //        nextStateA[t].equals(transitionEp[r]);

                //   && nextStateB[t].equals(nextEp[r]
                boolean isEqual = nextStateB[t].equals(currentEp[r]);

                if (isEqual) {
//                    containEpsilonforA = true;
                    containEpsilonforB = true;
                    // keep the same set
//                    newNextB = nextStateB;

                } else {
                    containEpsilonforB = false;

                }

            }

        }

     //   System.out.println("For transition for A: " + containEpsilonforA);

        if (containEpsilonforA == true) {
            //  more transition with epsilon
            newNextA = new String[nextStateA.length + nextEp.length];
            System.arraycopy(nextStateA, 0, newNextA, 0, nextStateA.length);
            System.arraycopy(nextEp, 0, newNextA, nextEp.length, nextStateA.length);

        } else {
            // nothing to do
            newNextA = nextStateA;
        }

        System.out.println("With A");
        for (String s : newNextA) {
//            System.out.println(s);
        }
        System.out.println("For transition for B: " + containEpsilonforB);

        if (containEpsilonforB == true) {
            //  more transition with epsilon

            newNextB = new String[nextStateB.length + nextEp.length];
//                    System.out.println(newNextB.length);

            System.arraycopy(nextStateB, 0, newNextB, 0, nextStateB.length);

            // Copy elements from nextEp to newNextB
            System.arraycopy(nextEp, 0, newNextB, nextStateB.length, nextEp.length);

        } else {
            newNextB = nextStateB;
            // nothing to do
        }

        System.out.println("With B");
        for (String s : newNextB) {
      //      System.out.println(s);
        }

        return newSetStates;

    }

    /// To do here
    public String[] newsetOfStates(String symBols, int numState, String tranFunction, String startState, String finalState) {

        // Process as array two D but return only array 1 D of String!
        // check condition here
        // if there are any qo which go with e-transition
        
        String []transitionSplit = tranFunction.split(";");
        String oldSetofState[] = renderStates(numState);
        
        String []renderP = renderNewStateP();
        
         // Testing
        
        ArrayList<ArrayList<String>> setOfNewStates2D = new ArrayList<>();
        String p0 = oldSetofState[0];
        ArrayList<String> statesgoWithA = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
          
        
        String []syms = {"a"};
        temp.add(p0);
//        statesgoWithA.add(p0);
         ArrayList <Integer> getIndex = new ArrayList<>();
        
         if(transitionSplit[0].contains(p0) && transitionSplit[0].contains(syms[0]) ){
                    getIndex.add(0);
                    temp.add(p0+oldSetofState[1]);
                }
        boolean isTrue = false;
        for (int r = temp.size() ; r>=0 ; r--){
            if(!temp.get(temp.size()-1).equals(temp.get(r-1))){
                isTrue = true;
                // take the previous to add
                 temp.add(temp.get(temp.size()-1)+oldSetofState[2]);
                break;
               
            }
        }
        
      
        
        
        
        for(int p = 0 ; p<temp.size();p++){
            statesgoWithA.add(temp.get(p));
        }
        System.out.println("The set of new States:");
        for (String k : statesgoWithA){

            System.out.println(k);
            
            
        }
        
        System.out.println("The number of new state = "+statesgoWithA.size());
        
        
        
        
        
       
         // now you can compare it
         
         
        
       
        //change to while loop next time
        
//       for(int i = 0 ; i<transitionSplit.length ; i++){
//           
//           // over number of symbols
//           for(int j =0 ; j<syms.length;j++){
//                if(transitionSplit[i].contains(p0) && transitionSplit[i].contains(syms[j]) ){
//                    getIndex.add(i);
//                    temp.add(p0+oldSetofState[1]);
//                }
//           }
//          
//       }
//        int i = 0;
//     //   while(!temp.get(i).equals(temp.get(i+1))
        
        
        
        
        // focus only one symbol first
        
        
        ArrayList<String> statesgoWithB = new ArrayList<>();

        
        // initail: for state process
       
//        setOfNewStates2D.get(0).add(p0);
        
        
        
        // while there are new state generate more
//        int i = 0;
//        while(!setOfNewStates2D.get(i).equals(i))
//        
//         
//        
//
//        String[] symbols = exTractSymbols(symBols);
        
        // use this old set of state and move one by one by each symbol
        // create a function for symBolstransitions
        String[] newsetOfStates = epsilonTransition(symBols, numState, tranFunction, startState, finalState);

        return newsetOfStates;
    }

    public FA ConstructEquivalentDFA(String symBols, int numState, String tranFunction, String startState, String finalState) {

        String[] newTransitinFunction = newTransitionFunction(symBols, numState, tranFunction, startState, finalState);
        String[] newStartState = newsetOfStates(symBols, numState, tranFunction, startState, finalState);

        String[] newsetOfFinalState = newsetOfFinalState(symBols, numState, tranFunction, startState, finalState);
        // mix functions

        // function for create a new set of state
        String[] newsetOfStates = newsetOfStates(symBols, numState, tranFunction, startState, finalState);

        // Convert those to normal String
//        String newTransitinFunctionString = String.join(";", newTransitinFunction);
//        String newStartStateString = String.join(";", newStartState);
//        String newsetOfFinalStateString = String.join(";", newsetOfFinalState);
//        String newsetOfStatesString = String.join(";", newsetOfStates);
        FA dfa = new FA();
        dfa.setSetofSymbols(symBols);
        dfa.setNumberOfState(numState);
        dfa.setTransitionFunctions(tranFunction);
        dfa.setStartState(startState);
        dfa.setSetofFinalStates(finalState);

        System.out.println();
        System.out.println("The set of States (expect): ");
        for (String s : newsetOfStates) {
            System.out.println(s);
        }
        return dfa;

    }

    public UI_automata1() {
        initComponents();
        UpdateTable();
        setTitle("UI automata2");
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        Main2.setVisible(false);
    }

    // class for get user input
    class Automaton {

        private int numberOfState;
        private String setofSymbols;
        private String transitionFunctions;
        private String startState;
        private String setofFinalStates;

        private String name;

        private String date;

        @Override
        public String toString() {
            return "Automaton "
                    + "Number of States: " + numberOfState + "\n"
                    + "Set of Symbols: " + setofSymbols + "\n"
                    + "Transition Functions: " + transitionFunctions + "\n"
                    + "Start State: " + startState + "\n"
                    + "Set of Final States: " + setofFinalStates;
        }

        public int getNumberOfState() {
            return numberOfState;
        }

        /**
         * @param numberOfState the numberOfState to set
         */
        public void setNumberOfState(int numberOfState) {
            if (numberOfState < 0) {
                JOptionPane.showMessageDialog(rootPane, "Number of State must be greater than 0!", "Automaton Processor", ERROR);
            } else {
                this.numberOfState = numberOfState;
            }

        }

        /**
         * @return the setofSymbols
         */
        public String getSetofSymbols() {
            return setofSymbols;
        }

        /**
         * @param setofSymbols the setofSymbols to set
         */
        public void setSetofSymbols(String setofSymbols) {
            this.setofSymbols = setofSymbols;
        }

        /**
         * @return the transitionFunctions
         */
        public String getTransitionFunctions() {
            return transitionFunctions;
        }

        /**
         * @param transitionFunctions the transitionFunctions to set
         */
        public void setTransitionFunctions(String transitionFunctions) {
            this.transitionFunctions = transitionFunctions;
        }

        /**
         * @return the startState
         */
        public String getStartState() {
            return startState;
        }

        /**
         * @param startState the startState to set
         */
        public void setStartState(String startState) {
            this.startState = startState;
        }

        /**
         * @return the setofFinalStates
         */
        public String getSetofFinalStates() {
            return setofFinalStates;
        }

        /**
         * @param setofFinalStates the setofFinalStates to set
         */
        public void setSetofFinalStates(String setofFinalStates) {
            this.setofFinalStates = setofFinalStates;
        }

        /**
         * @return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

    }

    class FA {

        private int numberOfState;
        private String setofSymbols;
        private String transitionFunctions;
        private String startState;
        private String setofFinalStates;

        public String toString() {
            return "DFA "
                    + "Number of States: " + numberOfState + "\n"
                    + "Set of Symbols: " + setofSymbols + "\n"
                    + "Transition Functions: " + transitionFunctions + "\n"
                    + "Start State: " + startState + "\n"
                    + "Set of Final States: " + setofFinalStates;
        }

        ;
        /**
         * @return the numberOfState
         */
        public int getNumberOfState() {
            return numberOfState;
        }

        /**
         * @param numberOfState the numberOfState to set
         */
        public void setNumberOfState(int numberOfState) {
            this.numberOfState = numberOfState;
        }

        /**
         * @return the setofSymbols
         */
        public String getSetofSymbols() {
            return setofSymbols;
        }

        /**
         * @param setofSymbols the setofSymbols to set
         */
        public void setSetofSymbols(String setofSymbols) {
            this.setofSymbols = setofSymbols;
        }

        /**
         * @return the transitionFunctions
         */
        public String getTransitionFunctions() {
            return transitionFunctions;
        }

        /**
         * @param transitionFunctions the transitionFunctions to set
         */
        public void setTransitionFunctions(String transitionFunctions) {
            this.transitionFunctions = transitionFunctions;
        }

        /**
         * @return the startState
         */
        public String getStartState() {
            return startState;
        }

        /**
         * @param startState the startState to set
         */
        public void setStartState(String startState) {
            this.startState = startState;
        }

        /**
         * @return the setofFinalStates
         */
        public String getSetofFinalStates() {
            return setofFinalStates;
        }

        /**
         * @param setofFinalStates the setofFinalStates to set
         */
        public void setSetofFinalStates(String setofFinalStates) {
            this.setofFinalStates = setofFinalStates;
        }

    }

    // initial table here 
    public void UpdateTable() {
//
//        String name = "Haha";
//        String date = "2024-06-07";

        ArrayList<Automaton> automatonList = new ArrayList<Automaton>();

        try {
            Connection con = DBConnection2.getConnection();
            String name = "";
            String date = "";
            int StateNum = 0;
            String symbols = "";
            String transitions = "";
            String startstate = "";
            String finalstate = "";

            PreparedStatement pst = con.prepareStatement("select * from automatadetails ");
//            pst.setString(1, name);
//            pst.setString(2, date);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                date = rs.getString("TheDate");
                StateNum = rs.getInt("StateNum");
                symbols = rs.getString("symbols");
                transitions = rs.getString("transitions");
                startstate = rs.getString("startstate");
                finalstate = rs.getString("finalstate");

                // Create a new Automaton object for each row
                Automaton auto = new Automaton();

                auto.setName(name); // Assuming 'name' is set elsewhere
                auto.setDate(date); // Assuming 'date' is set elsewhere (these might not be needed if not stored)
                auto.setNumberOfState(StateNum);
                auto.setSetofSymbols(symbols);
                auto.setTransitionFunctions(transitions);
                auto.setStartState(startstate);
                auto.setSetofFinalStates(finalstate);

                automatonList.add(auto); // Add the Automaton object to the list
            }

            // Handle the case where no data is found (optional)
            if (automatonList.isEmpty()) {
                System.out.println("No data found in the database.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources (assuming these are managed outside this block)
            // rs.close();
            // pst.close();
            // con.close();
        }

        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();
        model.setRowCount(0); /// clear table first
        for (Automaton auto : automatonList) {
            String nameInserted = auto.getName(); // Assuming a getter method for name in Automaton
            String dateInserted = auto.getDate(); // Assuming a getter method for date in Automaton
            Object[] row = {nameInserted, dateInserted};
            model.addRow(row);
        }
    }
    public void setTextToOutputArea(String str){
        OutputArea.setText(str);
        OutputArea.setEditable(false);  // Make the text area read-only
        OutputArea.setLineWrap(true);   // Enable line wrapping
        OutputArea.setWrapStyleWord(true); // Wrap at word boundaries
    }
    
   
    public String checkType(int numState, String setOfSymbol, String transitionFunction){
        String message= "";
        String mySymbol[] = setOfSymbol.split("[{,} ]", 0);
        String myTranf[] = transitionFunction.split(";", 0);

        for (String s : myTranf) {
            System.out.println(s);
        }

        int numOfTranf = myTranf.length;


        String joinedStr = String.join("", mySymbol);

        int numOfSymbol = joinedStr.length();

        boolean IsEqual;

     
        message = " => It is a NFA";

        IsEqual = (numOfSymbol * numState) == numOfTranf;

        if ((!(transitionFunction.contains("ε"))) && IsEqual) {
            message = "   => It is a DFA";

        }

        return message;
        
    }
    
    
    
    /// Database section
    
    
    public boolean insertValid(String name, String TheDate, int StateNum, String symbols, String transitions, String startstate, String finalstate) {
        boolean IsValid = false;

        Connection myCon = DBConnection2.getConnection();
        try {
            // Check for duplicate entry by comparing all attributes
            String checkSql = "SELECT COUNT(*) FROM automatadetails WHERE name = ? AND TheDate = ? AND StateNum = ? AND symbols = ? AND transitions = ? AND startstate = ? AND finalstate = ?";
            PreparedStatement checkPst = myCon.prepareStatement(checkSql);
            checkPst.setString(1, name);
            checkPst.setString(2, TheDate);
            checkPst.setInt(3, StateNum);
            checkPst.setString(4, symbols);
            checkPst.setString(5, transitions);
            checkPst.setString(6, startstate);
            checkPst.setString(7, finalstate);

            ResultSet rs = checkPst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                IsValid = count == 0; // If count is 0, it's valid to insert
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return IsValid;
    }

    public void insertAutomaton(String name, String TheDate, int StateNum, String symbols, String transitions, String startstate, String finalstart) {
        Connection myCon = DBConnection2.getConnection();
        try {
            // Check for duplicate entry
            boolean isInsertValid = insertValid(name, TheDate, StateNum, symbols, transitions, startstate, finalstart);
            // Proceed with insertion if no duplicate is found
            if (isInsertValid) {
                String sqlStatement = "INSERT INTO automatadetails(name, TheDate, StateNum, symbols, transitions, startstate, finalstate) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = myCon.prepareStatement(sqlStatement);

                pst.setString(1, name);
                pst.setString(2, TheDate);
                pst.setInt(3, StateNum);
                pst.setString(4, symbols);
                pst.setString(5, transitions);
                pst.setString(6, startstate);
                pst.setString(7, finalstart);

                int updateRowCount = pst.executeUpdate(); // Check if insert is successful
                if (updateRowCount > 0) {
                    // JOptionPane.showMessageDialog(null, "Record Inserted Successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Record Inserted Unsuccessfully");
                }
            } else {
                // just in case
                // JOptionPane.showMessageDialog(null, "Record with the same name and date already exists.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
      
        }
        UpdateTable();
    }

    public void deleteFA(String name, String date) {
        String nameSelected = name;
        String dateSelected = date;
        Connection myCon = DBConnection2.getConnection();

        try {
            String sqlStatement = "DELETE FROM automatadetails WHERE name = ? and TheDate = ?";

            PreparedStatement pst = myCon.prepareStatement(sqlStatement);

            pst.setString(1, nameSelected);
            pst.setString(2, dateSelected);

            int updateRowCount = pst.executeUpdate(); // check if delete is successful

            if (updateRowCount > 0) {
                JOptionPane.showConfirmDialog(this, "Record Deleted Successfully");
            } else {
                JOptionPane.showConfirmDialog(this, "Record Deletion Unsuccessful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshField() {
        headerName.setText("");
        setOfStatesField.setText("");
        setOfSymbolsField.setText("");
        transitionFunctionField.setText("");
        startStateField.setText("");
        setOfFinalStatesFields.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Main1 = new javax.swing.JPanel();
        headerName = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        setOfStatesField = new javax.swing.JTextField();
        setOfSymbolsField = new javax.swing.JTextField();
        transitionFunctionField = new javax.swing.JTextField();
        startStateField = new javax.swing.JTextField();
        setOfFinalStatesFields = new javax.swing.JTextField();
        btnMinimize = new javax.swing.JButton();
        BtnTeststring = new javax.swing.JButton();
        Main2 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        btnTesting = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnCheckFA = new javax.swing.JButton();
        btnConstruct = new javax.swing.JButton();
        testStringfield = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        BtnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        OutputArea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        RecentTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Btnload = new javax.swing.JButton();
        Btnupdate = new javax.swing.JButton();
        Btndelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 255, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Main1.setBackground(new java.awt.Color(255, 255, 255));

        headerName.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        headerName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        headerName.setText("Automaton Processor");
        headerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerNameActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel3.setText("Input set of symbols");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel4.setText("Input transition functions");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel5.setText("Input number of states");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel6.setText("Input set of final states");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel7.setText("Input start state");

        setOfStatesField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        setOfStatesField.setForeground(new java.awt.Color(102, 102, 102));
        setOfStatesField.setText("2");
        setOfStatesField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setOfStatesFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                setOfStatesFieldFocusLost(evt);
            }
        });
        setOfStatesField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setOfStatesFieldActionPerformed(evt);
            }
        });

        setOfSymbolsField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        setOfSymbolsField.setForeground(new java.awt.Color(102, 102, 102));
        setOfSymbolsField.setText("{a, b}");
        setOfSymbolsField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setOfSymbolsFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                setOfSymbolsFieldFocusLost(evt);
            }
        });
        setOfSymbolsField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setOfSymbolsFieldActionPerformed(evt);
            }
        });

        transitionFunctionField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        transitionFunctionField.setForeground(new java.awt.Color(102, 102, 102));
        transitionFunctionField.setText("{q0=>q1, a};{q0=>q0, b}");
        transitionFunctionField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                transitionFunctionFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                transitionFunctionFieldFocusLost(evt);
            }
        });
        transitionFunctionField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionFunctionFieldActionPerformed(evt);
            }
        });

        startStateField.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        startStateField.setForeground(new java.awt.Color(102, 102, 102));
        startStateField.setText("q0");
        startStateField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                startStateFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                startStateFieldFocusLost(evt);
            }
        });
        startStateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStateFieldActionPerformed(evt);
            }
        });

        setOfFinalStatesFields.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        setOfFinalStatesFields.setForeground(new java.awt.Color(102, 102, 102));
        setOfFinalStatesFields.setText("{q1}");
        setOfFinalStatesFields.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                setOfFinalStatesFieldsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                setOfFinalStatesFieldsFocusLost(evt);
            }
        });
        setOfFinalStatesFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setOfFinalStatesFieldsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startStateField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(transitionFunctionField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(setOfFinalStatesFields, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setOfStatesField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(setOfSymbolsField, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 32, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setOfStatesField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setOfSymbolsField, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transitionFunctionField, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startStateField, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setOfFinalStatesFields, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
                .addGap(0, 24, Short.MAX_VALUE))
        );

        btnMinimize.setBackground(new java.awt.Color(0, 0, 255));
        btnMinimize.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnMinimize.setForeground(new java.awt.Color(255, 255, 255));
        btnMinimize.setText("Minimize FA");
        btnMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizeActionPerformed(evt);
            }
        });

        BtnTeststring.setBackground(new java.awt.Color(0, 0, 255));
        BtnTeststring.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        BtnTeststring.setForeground(new java.awt.Color(255, 255, 255));
        BtnTeststring.setText("Test string");
        BtnTeststring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTeststringActionPerformed(evt);
            }
        });

        Main2.setBackground(new java.awt.Color(255, 255, 255));

        btnBack.setBackground(new java.awt.Color(0, 0, 255));
        btnBack.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnTesting.setBackground(new java.awt.Color(0, 0, 255));
        btnTesting.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnTesting.setForeground(new java.awt.Color(255, 255, 255));
        btnTesting.setText("Test");
        btnTesting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestingActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel9.setText("Testing String");

        javax.swing.GroupLayout Main2Layout = new javax.swing.GroupLayout(Main2);
        Main2.setLayout(Main2Layout);
        Main2Layout.setHorizontalGroup(
            Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main2Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main2Layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119)
                        .addComponent(btnTesting, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(193, 193, 193))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Main2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(270, 270, 270))))
        );
        Main2Layout.setVerticalGroup(
            Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(244, 244, 244)
                .addGroup(Main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTesting, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(326, Short.MAX_VALUE))
        );

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/rsz_1rsz_1rsz_1rsz_1pencil.png"))); // NOI18N

        btnCheckFA.setBackground(new java.awt.Color(0, 0, 255));
        btnCheckFA.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnCheckFA.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckFA.setText("Check FA");
        btnCheckFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckFAActionPerformed(evt);
            }
        });

        btnConstruct.setBackground(new java.awt.Color(0, 0, 255));
        btnConstruct.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        btnConstruct.setForeground(new java.awt.Color(255, 255, 255));
        btnConstruct.setText("Construct FA");
        btnConstruct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConstructActionPerformed(evt);
            }
        });

        testStringfield.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        testStringfield.setForeground(new java.awt.Color(153, 153, 153));
        testStringfield.setText("aabb");
        testStringfield.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                testStringfieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                testStringfieldFocusLost(evt);
            }
        });
        testStringfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testStringfieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Main1Layout = new javax.swing.GroupLayout(Main1);
        Main1.setLayout(Main1Layout);
        Main1Layout.setHorizontalGroup(
            Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Main1Layout.createSequentialGroup()
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Main1Layout.createSequentialGroup()
                                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(Main1Layout.createSequentialGroup()
                                        .addComponent(btnCheckFA, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(50, 50, 50)
                                        .addComponent(btnConstruct, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(testStringfield))
                                .addGap(46, 46, 46)
                                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtnTeststring, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Main1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(headerName, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(116, 116, 116)))
                .addComponent(Main2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Main1Layout.setVerticalGroup(
            Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Main2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Main1Layout.createSequentialGroup()
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Main1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel10)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConstruct, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCheckFA, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(Main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnTeststring, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(testStringfield, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(Main1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 690));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Output");

        BtnRefresh.setBackground(new java.awt.Color(0, 0, 255));
        BtnRefresh.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        BtnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        BtnRefresh.setText("Refresh");
        BtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshActionPerformed(evt);
            }
        });

        OutputArea.setColumns(20);
        OutputArea.setFont(new java.awt.Font("Times New Roman", 0, 21)); // NOI18N
        OutputArea.setForeground(new java.awt.Color(0, 0, 204));
        OutputArea.setRows(5);
        jScrollPane2.setViewportView(OutputArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(BtnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(BtnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 500, 290));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RecentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Date"
            }
        ));
        RecentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RecentTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(RecentTable);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 47, 470, 210));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Recent FAs");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 6, 136, -1));

        Btnload.setBackground(new java.awt.Color(0, 0, 255));
        Btnload.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        Btnload.setForeground(new java.awt.Color(255, 255, 255));
        Btnload.setText("Load");
        Btnload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnloadActionPerformed(evt);
            }
        });
        jPanel3.add(Btnload, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 140, 50));

        Btnupdate.setBackground(new java.awt.Color(0, 0, 255));
        Btnupdate.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        Btnupdate.setForeground(new java.awt.Color(255, 255, 255));
        Btnupdate.setText("Update");
        Btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnupdateActionPerformed(evt);
            }
        });
        jPanel3.add(Btnupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 140, 50));

        Btndelete.setBackground(new java.awt.Color(0, 0, 255));
        Btndelete.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        Btndelete.setForeground(new java.awt.Color(255, 255, 255));
        Btndelete.setText("Delete");
        Btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtndeleteActionPerformed(evt);
            }
        });
        jPanel3.add(Btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 290, 130, 50));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 300, 500, 390));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void headerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_headerNameActionPerformed

    private void BtnTeststringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTeststringActionPerformed
        // TODO add your handling code here

         Automaton input = getUserInput();
        int numState =input.numberOfState;
        String setOfSymbol = input.setofSymbols;
        String transitionFunction = input.transitionFunctions;
      //  String transitionFunctionNew[] = transitionFunction.split(",",0);
        
        
        String startState = input.startState;
        String setFinalState = input.setofFinalStates;

        String stringToTest = testStringfield.getText();
        String contentTest = "";
         String[] transitions = {
            "q0=>q1,a", "q0=>q0,b", "q1=>q1,a", "q1=>q2,b", "q2=>q2,a", "q2=>q2,b"
        };
         
         String transitionFunctionNew[] = transitions;
         
         /// {"q0=>q1,a","q0=>q0,b","q1=>q1,a","q1=>q2,b","q2=>q2,a","q2=>q2,b"};
      
        boolean result = testString("abba", 3, transitions, "q0", "q2");
       // System.out.println("Result: " + (result ? "Accepted" : "Rejected"));
        

      
//        System.out.println("IsTrue : "+IsAcceptMyString);
        System.out.println("Result: " + (result ? "Accepted" : "Rejected"));
        if (result == true) {
            contentTest = "=> String \"" + stringToTest + "\" is accepted!";
        } else {
            contentTest = "=> String \"" + stringToTest + "\" is rejected!";
        }
        
        setTextToOutputArea(contentTest);
        
// 

    }//GEN-LAST:event_BtnTeststringActionPerformed

    private void btnMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinimizeActionPerformed
        // TODO add your handling code here:
        // Write code to get user input here!


    }//GEN-LAST:event_btnMinimizeActionPerformed

    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshActionPerformed
        // TODO add your handling code here:
        String s = "";
        setTextToOutputArea(s);

    }//GEN-LAST:event_BtnRefreshActionPerformed

    private void BtnloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnloadActionPerformed
        // TODO add your handling code here:

        String nameSelected = "";
        String dateSelected = "";
        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();

        int selectedRowIndex = RecentTable.getSelectedRow();

        if (selectedRowIndex >= 0) {
            // Access row data (replace with your model's logic)
            nameSelected = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
            dateSelected = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1

            // Optional: Database interaction based on data from the selected row
            // ...
            System.out.println("Selected row: Name - " + nameSelected + ", Date - " + dateSelected);
        } else {
            System.out.println("No row selected");
        }

        String name = nameSelected;
        String date = dateSelected;

        try {
            Connection con = DBConnection2.getConnection();

            int StateNum = 0;
            String symbols = "";
            String transitions = "";
            String startstate = "";
            String finalstart = "";

            PreparedStatement pst = con.prepareStatement("select * from automatadetails where name = ? and TheDate = ?");
            pst.setString(1, name);
            pst.setString(2, date);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // JOptionPane.showMessageDialog(this, "Connect Successfully!");

                StateNum = rs.getInt("StateNum");
                symbols = rs.getString("symbols");
                transitions = rs.getString("transitions");
                startstate = rs.getString("startstate");
                finalstart = rs.getString("finalstate");

                Automaton auto = new Automaton();

                auto.setName(name);
                auto.setDate(date);
                auto.setNumberOfState(StateNum);
                auto.setSetofSymbols(symbols);
                auto.setTransitionFunctions(transitions);
                auto.setStartState(startstate);
                auto.setSetofFinalStates(finalstart);

                System.out.println(auto.toString());

            } else {
                // JOptionPane.showMessageDialog(this, "Incorrect name or stateNum!");
            }

            // Set data into text fields
            headerName.setText(name);
            setOfStatesField.setText(String.valueOf(StateNum));
            setOfSymbolsField.setText(symbols);
            transitionFunctionField.setText(transitions);
            startStateField.setText(startstate);
            setOfFinalStatesFields.setText(finalstart);

            // this will return boolean
        } catch (Exception e) {
            e.printStackTrace();
        }
        // try to load data from SQL 
        // user name and date value from user select on table row
//        
//        String name = "Test 2";
//        String date = "2024-06-05";
//        
//      
//        try {
//            Connection con = DBConnection2.getConnection();
//            
//           
//            int StateNum = 0;
//            String symbols = "";
//            String transitions = "";
//            String startstate = "";
//            String finalstart  = "";
//            
//            PreparedStatement pst = con.prepareStatement("select * from automatadetails where name = ? and TheDate = ?");
//            pst.setString(1, name);
//            pst.setString(2, date);
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                JOptionPane.showMessageDialog(this, "Connect Successfully!");
//                
//                
//                StateNum = rs.getInt("StateNum");
//                symbols = rs.getString("symbols");
//                transitions  = rs.getString("transitions");
//                startstate = rs.getString("startstate");
//                finalstart = rs.getString("finalstate");
//                
//               Automaton auto = new Automaton();
//               
//               auto.setName(name);
//               auto.setDate(date);
//               auto.setNumberOfState(StateNum);
//               auto.setSetofSymbols(symbols);
//               auto.setTransitionFunctions(transitions);
//               auto.setStartState(startstate);
//               auto.setSetofFinalStates(finalstart);
//               
//                System.out.println( auto.toString());
//               
//
//            } else {
//                JOptionPane.showMessageDialog(this, "Incorrect name or stateNum!");
//            }
//
//            // this will return boolean
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_BtnloadActionPerformed

    private void BtnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnupdateActionPerformed
        // TODO add your handling code here:

        String oldName = "";
        String oldDate = "";

        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();

        int selectedRowIndex = RecentTable.getSelectedRow();

        if (selectedRowIndex >= 0) {
            // Access row data (replace with your model's logic)
            oldName = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
            oldDate = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1

            // Optional: Database interaction based on data from the selected row
            // ...
        } else {
            System.out.println("Data not found!");
        }

        int numState = Integer.parseInt(setOfStatesField.getText());
        String setOfSymbol = setOfSymbolsField.getText();
        String transitionFunction = transitionFunctionField.getText();
        String startState = startStateField.getText();
        String setFinalState = setOfFinalStatesFields.getText();

        String dfName = headerName.getText();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Automaton auto = new Automaton();
        auto.setNumberOfState(numState);
        auto.setSetofSymbols(setOfSymbol);
        auto.setTransitionFunctions(transitionFunction);
        auto.setStartState(startState);
        auto.setSetofFinalStates(setFinalState);
        auto.setName(dfName);
        auto.setDate(date);

        try {
            Connection con = DBConnection2.getConnection();
            String sqlStatement = "UPDATE automatadetails SET name = ?, TheDate = ?, StateNum = ?, symbols = ?, transitions = ?, startstate = ?, finalstate = ? WHERE name = ? and TheDate = ?";

            PreparedStatement pst = con.prepareStatement(sqlStatement);

            pst.setString(1, auto.getName()); // New name
            pst.setString(2, auto.getDate());
            pst.setInt(3, auto.getNumberOfState());
            pst.setString(4, auto.getSetofSymbols());
            pst.setString(5, auto.getTransitionFunctions());
            pst.setString(6, auto.getStartState());
            pst.setString(7, auto.getSetofFinalStates());
            pst.setString(8, oldName); // Old name to identify the record to update
            pst.setString(9, oldDate);

            int updateRowCount = pst.executeUpdate(); // check if update is successful

            if (updateRowCount > 0) {
                JOptionPane.showMessageDialog(null, "Record Updated Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Record Update Unsuccessful");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources if needed (assuming these are managed outside this block)
            // pst.close();
            // con.close();
        }

        UpdateTable();
        refreshField();

    }//GEN-LAST:event_BtnupdateActionPerformed

    private void BtndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtndeleteActionPerformed
        // TODO add your handling code here:

        String nameSelected = "";
        String dateSelected = "";

        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();

        int selectedRowIndex = RecentTable.getSelectedRow();

        if (selectedRowIndex >= 0) {
            // Access row data (replace with your model's logic)
            nameSelected = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
            dateSelected = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1

            // Optional: Database interaction based on data from the selected row
            // ...
        } else {
            System.out.println("Data not found!");
        }

        deleteFA(nameSelected, dateSelected);

        UpdateTable();
        refreshField();


    }//GEN-LAST:event_BtndeleteActionPerformed

    private void setOfSymbolsFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setOfSymbolsFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_setOfSymbolsFieldActionPerformed

    private void transitionFunctionFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionFunctionFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transitionFunctionFieldActionPerformed

    private void startStateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startStateFieldActionPerformed

    private void setOfFinalStatesFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setOfFinalStatesFieldsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_setOfFinalStatesFieldsActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btnBackActionPerformed

    private void btnTestingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTestingActionPerformed

    private void setOfStatesFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setOfStatesFieldActionPerformed
   
    }//GEN-LAST:event_setOfStatesFieldActionPerformed

    private void setOfStatesFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfStatesFieldFocusGained
        // TODO add your handling code here:
        if (setOfStatesField.getText().equals("2")) {
            setOfStatesField.setText("");
            setOfStatesField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfStatesFieldFocusGained

    private void setOfSymbolsFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfSymbolsFieldFocusGained
        // TODO add your handling code here:

        if (setOfSymbolsField.getText().equals("{a, b}")) {
            setOfSymbolsField.setText("");
            setOfSymbolsField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfSymbolsFieldFocusGained

    private void transitionFunctionFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_transitionFunctionFieldFocusGained
        // TODO add your handling code here:

        if (transitionFunctionField.getText().equals("{q0=>q1, a};{q0=>q0, b}")) {
            transitionFunctionField.setText("");
            transitionFunctionField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_transitionFunctionFieldFocusGained

    private void setOfStatesFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfStatesFieldFocusLost
        // TODO add your handling code here:
        if (setOfStatesField.getText().equals("")) {
            setOfStatesField.setText("2");
            setOfStatesField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfStatesFieldFocusLost

    private void setOfSymbolsFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfSymbolsFieldFocusLost
        // TODO add your handling code here:
        if (setOfSymbolsField.getText().equals("")) {
            setOfSymbolsField.setText("{a, b}");
            setOfSymbolsField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfSymbolsFieldFocusLost

    private void transitionFunctionFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_transitionFunctionFieldFocusLost
        // TODO add your handling code here:
        if (transitionFunctionField.getText().equals("")) {
            transitionFunctionField.setText("{q0=>q1, a};{q0=>q0, b}");
            transitionFunctionField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_transitionFunctionFieldFocusLost

    private void startStateFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startStateFieldFocusGained
        // TODO add your handling code here:
        if (startStateField.getText().equals("q0")) {
            startStateField.setText("");
            startStateField.setForeground(SetColor1());
        }


    }//GEN-LAST:event_startStateFieldFocusGained

    private void startStateFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_startStateFieldFocusLost
        // TODO add your handling code here:
        if (startStateField.getText().equals("")) {
            startStateField.setText("q0");
            startStateField.setForeground(SetColor1());
        }
    }//GEN-LAST:event_startStateFieldFocusLost

    private void setOfFinalStatesFieldsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfFinalStatesFieldsFocusGained
        // TODO add your handling code here:
        if (setOfFinalStatesFields.getText().equals("{q1}")) {
            setOfFinalStatesFields.setText("");
            setOfFinalStatesFields.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfFinalStatesFieldsFocusGained

    private void setOfFinalStatesFieldsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_setOfFinalStatesFieldsFocusLost
        // TODO add your handling code here:
        if (setOfFinalStatesFields.getText().equals("")) {
            setOfFinalStatesFields.setText("{q1}");
            setOfFinalStatesFields.setForeground(SetColor1());
        }
    }//GEN-LAST:event_setOfFinalStatesFieldsFocusLost

    
    
    private void btnCheckFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckFAActionPerformed
        // TODO add your handling code here:
        
        Automaton input = getUserInput();
        int numState =input.numberOfState;
        String setOfSymbol = input.setofSymbols;
        String transitionFunction = input.transitionFunctions;
        String startState = input.startState;
        String setFinalState = input.setofFinalStates;

        String name = input.name;
        String date = input.date;

        /// check FA here 
        //1. no epsilon transition
        //2. number of transitions equal to number of states x number of symbols
        // how to count number of symbol
        // how to count number of transition
     
        
        String messageType = checkType(numState, setOfSymbol, transitionFunction);
        String myContent = input.toString();
        String myRealMeassage = myContent + "\n" + messageType;
           
         // use function insert FA (database) [auto save]
        insertAutomaton(name, date, numState, setOfSymbol, transitionFunction, startState, setFinalState);
        // set message to area.
        setTextToOutputArea(myRealMeassage);
        // clear user input
        refreshField();

        // use function split string before insert into object of class

    }//GEN-LAST:event_btnCheckFAActionPerformed

    private void btnConstructActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConstructActionPerformed
        // TODO add your handling code here:
        int numState = Integer.parseInt(setOfStatesField.getText());
        String setOfSymbol = setOfSymbolsField.getText();
        String transitionFunction = transitionFunctionField.getText();
        String startState = startStateField.getText();
        String setFinalState = setOfFinalStatesFields.getText();

        // write code here 
        // FA: (Q, X, ₰, q0, F)
        // Generate States
        FA myDFA;
        myDFA = ConstructEquivalentDFA(setOfSymbol, numState, transitionFunction, startState, setFinalState);
        // Output here!
        System.out.println(myDFA);

    }//GEN-LAST:event_btnConstructActionPerformed

    private void testStringfieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_testStringfieldFocusGained
        // TODO add your handling code here:

        if (testStringfield.getText().equals("aabb")) {
            testStringfield.setText("");
            testStringfield.setForeground(SetColor1());
        }

    }//GEN-LAST:event_testStringfieldFocusGained

    private void testStringfieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_testStringfieldFocusLost
        // TODO add your handling code here:

        if (testStringfield.getText().equals("")) {
            testStringfield.setText("aabb");
            testStringfield.setForeground(SetColor1());
        }

    }//GEN-LAST:event_testStringfieldFocusLost

    private void testStringfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testStringfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_testStringfieldActionPerformed

    private void RecentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RecentTableMouseClicked
        // TODO add your handling code here:

//        String nameSelected = "";
//        String dateSelected = "";
//        DefaultTableModel model = (DefaultTableModel) RecentTable.getModel();
//
//        int selectedRowIndex = RecentTable.getSelectedRow();
//
//        if (selectedRowIndex >= 0) {
//            // Access row data (replace with your model's logic)
//            nameSelected = (String) model.getValueAt(selectedRowIndex, 0); // Assuming name is in column 0
//            dateSelected = (String) model.getValueAt(selectedRowIndex, 1); // Assuming date is in column 1
//
//            // Optional: Database interaction based on data from the selected row
//            // ...
//            System.out.println("Selected row: Name - " + nameSelected + ", Date - " + dateSelected);
//        } else {
//            System.out.println("No row selected");
//        }
//
//        String name = nameSelected;
//        String date = dateSelected;
//
//        try {
//            Connection con = DBConnection2.getConnection();
//
//            int StateNum = 0;
//            String symbols = "";
//            String transitions = "";
//            String startstate = "";
//            String finalstart = "";
//
//            PreparedStatement pst = con.prepareStatement("select * from automatadetails where name = ? and TheDate = ?");
//            pst.setString(1, name);
//            pst.setString(2, date);
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                // JOptionPane.showMessageDialog(this, "Connect Successfully!");
//
//                StateNum = rs.getInt("StateNum");
//                symbols = rs.getString("symbols");
//                transitions = rs.getString("transitions");
//                startstate = rs.getString("startstate");
//                finalstart = rs.getString("finalstate");
//
//                Automaton auto = new Automaton();
//
//                auto.setName(name);
//                auto.setDate(date);
//                auto.setNumberOfState(StateNum);
//                auto.setSetofSymbols(symbols);
//                auto.setTransitionFunctions(transitions);
//                auto.setStartState(startstate);
//                auto.setSetofFinalStates(finalstart);
//
//                System.out.println(auto.toString());
//
//            } else {
//                // JOptionPane.showMessageDialog(this, "Incorrect name or stateNum!");
//            }
//
//            // Set data into text fields
//            headerName.setText(name);
//            setOfStatesField.setText(String.valueOf(StateNum));
//            setOfSymbolsField.setText(symbols);
//            transitionFunctionField.setText(transitions);
//            startStateField.setText(startstate);
//            setOfFinalStatesFields.setText(finalstart);
//
//            // this will return boolean
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }//GEN-LAST:event_RecentTableMouseClicked

    /**
     * @param args the command line arguments
     */
    
    /// main function
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UI_automata1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
                for (GraphicsDevice device : devices) {
                    new UI_automata1().setVisible(true);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnRefresh;
    private javax.swing.JButton BtnTeststring;
    private javax.swing.JButton Btndelete;
    private javax.swing.JButton Btnload;
    private javax.swing.JButton Btnupdate;
    private javax.swing.JPanel Main1;
    private javax.swing.JPanel Main2;
    private javax.swing.JTextArea OutputArea;
    private javax.swing.JTable RecentTable;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCheckFA;
    private javax.swing.JButton btnConstruct;
    private javax.swing.JButton btnMinimize;
    private javax.swing.JButton btnTesting;
    private javax.swing.JTextField headerName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField setOfFinalStatesFields;
    private javax.swing.JTextField setOfStatesField;
    private javax.swing.JTextField setOfSymbolsField;
    private javax.swing.JTextField startStateField;
    private javax.swing.JTextField testStringfield;
    private javax.swing.JTextField transitionFunctionField;
    // End of variables declaration//GEN-END:variables
}
