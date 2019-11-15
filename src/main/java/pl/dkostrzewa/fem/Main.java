package pl.dkostrzewa.fem;

import pl.dkostrzewa.fem.features.FemGridGenerator;
import pl.dkostrzewa.fem.features.GaussInterpolation;
import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.utils.GlobalConstants;

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

        // System.out.println(femGrid.getNodes().get((Integer) femGrid.getElements().get(0).getiDs().get(1)));
        Element selectedElement = femGrid.getElements().get(10);
        double dxdksi = GlobalConstants.jacobianDerivativesX(ksi[1], selectedElement, femGrid.getNodes());
        double dxdeta = GlobalConstants.jacobianDerivativesX(eta[1], selectedElement, femGrid.getNodes());
        double dydksi = GlobalConstants.jacobianDerivativesY(ksi[1], selectedElement, femGrid.getNodes());
        double dydeta = GlobalConstants.jacobianDerivativesY(eta[1], selectedElement, femGrid.getNodes());

        System.out.println(selectedElement.getiDs());

        double[][] jacobian = new double[2][2];
        jacobian[0][0] = dxdksi;
        jacobian[0][1] = dydksi;
        jacobian[1][0] = dxdeta;
        jacobian[1][1] = dydeta;

        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                System.out.print(jacobian[i][j]+" ");
            }
            System.out.println();
        }

//        System.out.println("dxksi: " + dxdksi);
//        System.out.println("dxdeta: " + dxdeta);
//        System.out.println("dydksi: " + dydksi);
//        System.out.println("dydeta:" + dydeta);

    }


}
