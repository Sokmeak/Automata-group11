/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UIforAutomatav1;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class TestString {
   
    public static void main(String[] args) {
        
        String strNew = "bbb";
        int numStateNew = 3;
        
         String[] stateGenerate = new String [numStateNew];
        for(int i = 0 ; i< numStateNew; i++){
            stateGenerate[i] = "q" + i;
        }
        
        String symbolsNew[] = {"a","b"};
        
       
        String tranFunctionNew [] = {"q0=>q1,a","q0=>q0,b","q1=>q1,a","q1=>q2,b","q2=>q2,a","q2=>q2,b"};
        String startStateNew = "q0";
        String finalStartNew= "q2";
        
        String testSymbol = "";
        boolean Isaccept = false;
        
        String currentState = startStateNew;
        char currentSymbol;
        
        String myTransitionWithSymbol = "";
        
        
        for(int i =0 ; i<strNew.length(); i++){
            testSymbol = String.valueOf( strNew.charAt(i));
            
            for(int j = 0; j < numStateNew ; j++){
                
                myTransitionWithSymbol = startStateNew +"=>"+stateGenerate[j] +","+testSymbol;
                for (String tran : tranFunctionNew) {
                    if (myTransitionWithSymbol.equals(tran)) {
                        currentState = stateGenerate[j];
                          System.out.println(currentState);
                        break;
                    }
                }  
                if(finalStartNew.contains(currentState)){
                    Isaccept = true;
                }
                
            }
            
            startStateNew = currentState;  }
        
      if(Isaccept == true){
            System.out.println("Accept!");
      }else {
            System.out.println("Reject!");
      }
        
       
       
     
    }
    
}
