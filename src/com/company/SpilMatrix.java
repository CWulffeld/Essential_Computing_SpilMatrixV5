package com.company;

import java.util.Scanner;

public class SpilMatrix {
    RandomTokens tokens = new RandomTokens();
    TjekMatrix tjekMatrix = new TjekMatrix();

    private int højde;
    private int bredde;
    private boolean xTur; // sat til at starte med true i main.
    private boolean førsteRunde = true;
    private boolean run = true;
    private int rowNr;


    private String[][] arrayMatrix;
    private String[][] xArrayMatrix;
    private String[][] yArrayMatrix;

    public SpilMatrix(int højde, int bredde, boolean xTur) {
        this.højde = højde;
        this.bredde = bredde;
        this.xTur = xTur;
    }

    public void setup() {
        arrayMatrix = new String[højde][bredde];
        xArrayMatrix = arrayMatrix;
        yArrayMatrix = arrayMatrix;

        System.out.println("Velkommen til spillet. Handler om at få 5 på stribe");
        System.out.println("Player 1: X");
        System.out.println("Player 2: Y");
        System.out.println();

        while (run) { //while-loop kører så længe run er true
            run = spilRunde(arrayMatrix, xArrayMatrix, yArrayMatrix); //spilRunde returnerer true eller false - fortsætter så længe true
        }

    }

    //Metode til at printe spil matrixen (array) ud.
    public int indtastBrik() {
        Scanner scanner = new Scanner(System.in);
        int rykBrik;

        //xTur er sat globalt til true, derfor player 1 starter.
        if (xTur) {
            System.out.println("(X)  - Player 1: Indtast et tal fra 1-7, hvor du ønsker at smide brikken");
        } else {
            System.out.println("(Y)  - Player 2: Indtast et tal fra 1-7, hvor du ønsker at smide brikken");
        }
        System.out.println();


        //try-catch så der skal skrives en int, og string modtages ikke
        try {
            rykBrik = Integer.parseInt(scanner.next()); //konverterer til en int
            System.out.println(rykBrik);

            if (rykBrik < 1 || rykBrik > 7) {
                System.out.println("Prøv igen, fra 1-7");
            }
        } catch (Exception e) {
            System.out.println("Du har indtastet forkert, start forfra");
            rykBrik = 11; //Skulle vælge 0 eller 8 i stedet for
        }
        return rykBrik;
    }

    public boolean spilRunde(String[][] startArrayMatrix, String[][] xArrayMatrix, String[][] yArrayMatrix) {
        for (int row = 0; row < startArrayMatrix.length; row++) { //hver row, kører columns igennem.
            for (int column = 0; column < startArrayMatrix[row].length; column++) { //for loop der kører igennem columns med det givne index row

                if (førsteRunde) { //førsterunde er en boolean sat til true globalt, hvis det første runde, skal O indsættes
                    startArrayMatrix[row][column] = "O";
                }

                if (xArrayMatrix[row][column].equals("X")) { //Hvis nuværende row og col i xArray indeholder "X", så tegner vi det
                    System.out.print(xArrayMatrix[row][column] + "  ");
                } else if (yArrayMatrix[row][column].equals("Y")) {
                    System.out.print(xArrayMatrix[row][column] + "  ");
                } else {
                    startArrayMatrix[row][column] = "O";

                    tokens.indsætRandomTokens(startArrayMatrix, column, row);
                    System.out.print(startArrayMatrix[row][column] + "  "); //Printer værdierne samt mellemrum mellem værdierne
                }
            }
            System.out.println(); //linje for at få 9 columns x 6 rows (konsol udseende)
        }
        System.out.println();
        return turErIgang(); //turErIgang returnerer enten en true eller false
    }

    public boolean turErIgang() {
        boolean turErIgang = true;

        if (xTur) { //Tjekker hvis xTur er true (X's tur er igang)
            while (turErIgang) {
                int rykBrikX = indtastBrik();
                //placerBrik returnerer false eller true -> bestemmer om turErIgang er true eller false
                turErIgang = placerBrik(rykBrikX, xArrayMatrix); // TurErIgang skal have værdi fra placerBrik, om brikken kan ligges eller ej

                if (tjekMatrix.gennemløbRows("X", xArrayMatrix) //Hver metode -> tjekker 5 på stribe -> returnerer true hvis ja
                        || tjekMatrix.gennemløbColumns("X", xArrayMatrix, this.højde, this.bredde)
                        || tjekMatrix.gennemløbDiagonal1("X", xArrayMatrix, this.højde, this.bredde)
                        || tjekMatrix.udregnDiagonalernesLængde("X", xArrayMatrix, rykBrikX, this.rowNr)) {
                    return false; //hvis der er 5 på stribe -> metoden returnerer false for at afslutte spillet
                }
            }
        } else { // Ellers er det Y's tur
            while (turErIgang) {
                int rykBrikY = indtastBrik();
                turErIgang = placerBrik(rykBrikY, yArrayMatrix);

                if (tjekMatrix.gennemløbRows("Y", yArrayMatrix)
                        || tjekMatrix.gennemløbColumns("Y", yArrayMatrix, this.højde, this.bredde)
                        || tjekMatrix.gennemløbDiagonal1("Y", yArrayMatrix, this.højde, this.bredde)
                        || tjekMatrix.udregnDiagonalernesLængde("Y", yArrayMatrix, rykBrikY, this.rowNr)) {
                    return false;
                }
            }
        }

        førsteRunde = false; //Sætter første runde til false efter første runde -> false resten af tiden
        if (tjekMatrix.erSpilBrætFyldt(xArrayMatrix) || tjekMatrix.erSpilBrætFyldt(yArrayMatrix)) { //hvis en af disse er true
            return false;  //turErIgang er sat til false -> metoden returnerer false
        }
        return true;
    }

    //Metode retunerer enten en true eller false, alt efter om brikken kan placeres
    public boolean placerBrik(int brikColumnNummer, String[][] array) {
        for (int row = 0; row < array.length; row++) {
            String brikVærdi = array[row][brikColumnNummer];

            //Tjekker hvis der er fyldt op (om der er en på index 0)
            if (!brikVærdi.equals("O") && row == 0) {
                return true; //Turen fortsætter - Der kan ikke tegnes en brik
            }

            //Tjekker hvis der allerede står X eller Y på placeringen
            //Tjekker om pladsen ikke har "O" som værdi, så står der X eller Y
            if (!brikVærdi.equals("O")) { //Hvis der allerede står X eller Y
                tegnBrik(array, row - 1, brikColumnNummer);
                this.rowNr = row - 1;
                return false; //Turen slutter og brik er tegnet
            }

            //Tjekker om det er den sidste row - array -> 6 (-1 for at få index 5)
            if (row == array.length - 1) { //length tæller fra 1, derfor -1
                tegnBrik(array, row, brikColumnNummer);
                this.rowNr = row;
                return false; //Turen slutter og brik er tegnet
            }
        }
        return true;
    }

    public void tegnBrik(String[][] array, int row, int column) {
        if (xTur) { //Hvis det er X's tur
            array[row][column] = "X"; //Indsæt "X" ved placeringen i array
            xArrayMatrix = array; //Kopier indholdet i array til xArrayMatrix -> kan slettes
        } else {
            array[row][column] = "Y";
            yArrayMatrix = array; // kan slettes
        }
        xTur = !xTur; //Sætter xtur til det modsatte af hvad den er nu, for at skifte mellem player 1 og 2
    }


}
