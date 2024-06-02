/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Main {
    public static void main(String[] args) {
        String str = "g@f@ kss";
        String[] arrOfStr = str.split("[s@ ]", 0); // Use a regex to split by 's' or '@'

        for (String a : arrOfStr) {
            System.out.print(a + " ");
        }
        
         String joinedStr = String.join("", arrOfStr);
        System.out.println(joinedStr.length());
    }
}
