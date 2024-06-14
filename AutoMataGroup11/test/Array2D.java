
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class Array2D {

    public static void main(String[] args) {

        ArrayList<ArrayList<String>> twoDArr = new ArrayList<>();
        // while there is exist new state, create a new one.
        int i = 0;
        while (i < 5) {
            twoDArr.add(new ArrayList<>()); // initial first
            for (int j = 0; j <= i; j++) {

                twoDArr.get(i).add("" + i);
            }
            i++;
        }
        
        int numColumn = 0;

        int Array2Dsize = twoDArr.size();
        System.out.println("Array Size: " + Array2Dsize);
        for (ArrayList<String> row : twoDArr) {

            System.out.println("Element in row: " + " column" + numColumn);
             int sizeForEachCol = twoDArr.get(numColumn).size();
             System.out.println("sizeForEachCol: "+sizeForEachCol);

            for (String s : row) {
               
                

                System.out.println(s);
            }
            numColumn++;

        }

    }

}
