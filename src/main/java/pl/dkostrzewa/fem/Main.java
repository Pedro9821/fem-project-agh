package pl.dkostrzewa.fem;

import pl.dkostrzewa.fem.features.FemGridGenerator;
import pl.dkostrzewa.fem.features.GaussInterpolation;
import pl.dkostrzewa.fem.features.MatrixForElement;
import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.utils.GlobalConstants;

import java.util.Scanner;

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


        for (Element element : femGrid.getElements()) {
            MatrixForElement.generateH(element, ksi, eta, globals);
            MatrixForElement.generateC(element, ksi, eta, shapeFunctions, globals);
            MatrixForElement.generateHbc(element, globals);
           // System.out.println();
           // System.out.println("MATRIX [H]: ");
           // GlobalConstants.printMatrixNxM(element.getH(), 4, 4);
            //System.out.println("MATRIX [C]: ");
            //GlobalConstants.printMatrixNxM(element.getC(), 4, 4);




        }

        System.out.println();
        System.out.println();
        System.out.println();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Select Finite Element <1,"+(femGrid.getElements().size())+">");
        int elementNo = keyboard.nextInt()-1;
        Element el = femGrid.getElements().get(elementNo);
        System.out.println(el);
        System.out.println("MATRIX [H]: ");
        GlobalConstants.printMatrixNxM(el.getH(), 4, 4);
        System.out.println("MATRIX [C]: ");
        GlobalConstants.printMatrixNxM(el.getC(), 4, 4);
        System.out.println("MATRIX [Hbc]: ");
        GlobalConstants.printMatrixNxM(el.getHbc(), 4, 4);


    }


}
