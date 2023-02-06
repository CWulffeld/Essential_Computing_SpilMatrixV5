package com.company;

import java.util.Random;

public class RandomTokens {
    Random random = new Random();

    public void indsætRandomTokens(String [][] startArrayMatrix, int column, int row){
        //Inspiration:https://www.tutorialspoint.com/how-to-populate-a-2d-array-with-random-alphabetic-values-from-a-range-in-java
        if (column == 8 || column == 0) { //Hvis vi er på kollonne  0 eller 8, skal vi indsætte random char i stedet for
            int num = random.nextInt(2); //bounds er 2, laver et random tal mellem 0 og 1
            switch (num) { //hvis det random tal er 0 = X og hvis random tal er 1 = Y
                case 0: {
                    startArrayMatrix[row][column] = "X";
                    break;
                }
                case 1: {
                    startArrayMatrix[row][column] = "Y";
                    break;
                }
            }
        }
    }

}
