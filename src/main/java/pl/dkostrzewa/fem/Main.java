package pl.dkostrzewa.fem;

import pl.dkostrzewa.fem.features.FemGridGenerator;
import pl.dkostrzewa.fem.features.GaussInterpolation;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;

public class Main {

    public static void main(String[] args) {

        Globals globals = new Globals();
        System.out.println(globals);

        FemGridGenerator femGridGenerator = new FemGridGenerator();
        FemGrid femGrid = femGridGenerator.generateFemGrid(globals);

        femGrid.showFemNodes();
        femGrid.showFemElements();


        GaussInterpolation gaussInterpolation = new GaussInterpolation();

        double[][] ksi = gaussInterpolation.countKsiDerivatives(globals);
        System.out.println("Macierz N/dKsi");
        for (int i = 0; i < ksi.length; i++) {
            for (int j = 0; j < ksi[i].length; j++) {
                System.out.print(ksi[i][j] + "\t");
            }
            System.out.println();
        }

        double[][] eta = gaussInterpolation.countEtaDerivatives(globals);
        System.out.println("Macierz N/dEta");
        for (int i = 0; i < eta.length; i++) {
            for (int j = 0; j < eta[i].length; j++) {
                System.out.print(eta[i][j] + "\t");
            }
            System.out.println();
        }

        double[][] shapeFunctions = gaussInterpolation.countShapeFunctionValues(globals);
        System.out.println("Funkcje ksztaltu ");
        for (int i = 0; i < shapeFunctions.length; i++) {
            for (int j = 0; j < shapeFunctions[i].length; j++) {
                System.out.print(shapeFunctions[i][j] + "\t");
            }
            System.out.println();
        }

    }


}
