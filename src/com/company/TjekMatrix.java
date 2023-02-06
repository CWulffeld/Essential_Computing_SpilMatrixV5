package com.company;

public class TjekMatrix {

    //Gennemløber vandret
    public boolean gennemløbRows(String findValue, String[][] array) {
        int tempMax = 0;

        for (int row = 0; row < array.length; row++) {
            int count = 0;

            String indexValue;

            //Starter på 1 for ikke at tage kolonne index 0 med, samt -1 for ikke at tage kolonne 9 med.
            for (int column = 1; column < array[row].length-1 ; column++) {
                indexValue = array[row][column];
                if (indexValue == findValue) { // find value er X eller Y, alt om hvad værdien fra parameteren er angivet.
                    count++; //bliver talt op hvis indexValue er det samme som findValue
                    if (count > tempMax) {
                        tempMax = count; //Hvis count er større end tempMax, skal tempMax have værdien af count
                    }
                } else {
                    count = 0; //sætter count til 0 for at starte count forfra
                }
            }
        }

        return tjekVundetSpil(tempMax); //tempMax vidersendes som argument til tjekVundetSpil
    }

    //Gennemløber lodret
    public boolean gennemløbColumns (String findValue, String[][] array, int højde, int bredde) {
        int tempMax = 0;
        for (int i = 1; i < bredde-1; i++) { //starter på 1 og bredde-1 for ikke at tage kolonne 0 og  9 med
            int count = 0;
            for (int j = 0; j < højde; j++) {
                String indexValue = array[j][i];
                if (indexValue.equals(findValue)) { //Hvis indexValue som er værdien på det givne indeks, er lig med værdien fra parameteren
                    count++;
                    if (count > tempMax) {
                        tempMax = count;
                    }
                } else {
                    count = 0; //sætter count til 0 for at starte count forfra
                }

            }
        }
        return tjekVundetSpil(tempMax);
    }

    //Gennemløber diagonalt retning
    public boolean gennemløbDiagonal1 (String findValue, String [][] array, int højde, int bredde){
        int tempMax = 0;
        for (int k = 0; k < (højde + bredde) * 2; k++) {
            int count = 0;
            for (int j = 1; j <= k && j<bredde-1; j++) { //Starter på 1 da colum 0 ikke skal med, kører indtil bredde-1 for ikke få sidste colum med
                int i = k - j;
                if (i < højde && j < bredde) {
                    String indexValue =  array[i][j];
                    if (indexValue == findValue) {
                        count++;
                        if (count > tempMax) {
                            tempMax = count;
                        }
                    } else {
                        count = 0; //sætter count til 0 for at starte count forfra
                    }
                }
            }
        }
        return tjekVundetSpil(tempMax);
    }

    //Ud fra placering, kigger metoden på det næste index der er en kolonne større samt en row større: tjekker fra placering op til ned
    public int diagonalVenstreOpTilNed(String findValue, String [][] array, int columnNr, int rowNr) {
        int column = columnNr;
       // int row = række.getRække();
        int row = rowNr;
        int tempMax  = 1;
        boolean run = true;
        while (run){
            column = column + 1;
            row = row + 1;


            if (column >=8 || row >=6) {
                break;
            }
            if (array[row][column] == findValue){
                tempMax++;
            }else {
                break;
            }
        }
        return tempMax;
    }

    //Kigger på spillerens nuværende placering og minusser en column og minusser en row.
    public int diagonalVenstreNedTilOp(String findValue, String [][] array, int columnNr, int rowNr) {
        int column = columnNr;
        int row = rowNr;
        int tempMax  = 1;
        boolean run = true;
        while (run){
            column = column - 1;
            row = row - 1;


            if (column < 1|| row <=0){
                break;
            }
            if (array[row][column] == findValue){
                tempMax++;
            }else {
                break;
            }
        }

        return tempMax;

    }

    public boolean udregnDiagonalernesLængde(String findValue, String [][] array, int columnNr, int rowNr){
        int tempMax1 = diagonalVenstreNedTilOp(findValue, array, columnNr,  rowNr);
        int tempMax2 = diagonalVenstreOpTilNed(findValue, array, columnNr,  rowNr);

        int tempMax = tempMax1 + tempMax2; //plusser de to tempMax, da vi skal have den fulde længde i den diagonale retning
       tempMax = tempMax-1;
        return tjekVundetSpil(tempMax);
    }

    public boolean tjekVundetSpil(int tempMax){
        if (tempMax >= 5) {
            System.out.println("Tillykke, du har vundet!");
            return true;
        } else {
            return false;
        }
    }

    public boolean erSpilBrætFyldt(String[][] array) {
        int count = 0;

        //Løber igennem alle brikkerne i array ved at anvende nested for-each loop
        for (String[] brikker: array) { //Deklære at det er String []
            for (String brik: brikker) { //For at få fat i hver brik i array brikker.

                if (brik == "O") { //hvis brik er det samme som "0", skal count tælles en op.
                    count++;
                }

                if (count > 0) { //hvis count er større end 0, skal metoden returnere false, da brættet ikke er fyldt op
                    return false;
                }
            }
        }
        System.out.println("Brættet blev fyldt, ingen har vundet.");
        return true;
    }
}
