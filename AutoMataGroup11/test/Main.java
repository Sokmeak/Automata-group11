
import UI.automata.main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Main {
 public static ArrayList<String> extractStates(String transition) {
         Set<String> statesSet = new HashSet<>();
        ArrayList<String> statesList = new ArrayList<>();

        // Define the regex pattern to match states in the format {q1=>q2,?}
        Pattern pattern = Pattern.compile("\\{(\\w+)=>(\\w+),\\?\\}");
        Matcher matcher = pattern.matcher(transition);

        while (matcher.find()) {
            // Add both states to the list
            statesList.add(matcher.group(1));
            statesList.add(matcher.group(2));
        }
      

    return statesList;
        
        
    };

  
    public static void main(String[] args) {
             String str = "{q0=>q1}";
             System.out.println(str.subSequence(1,3));
             
             
           

//
       }
 
 
}
   



 