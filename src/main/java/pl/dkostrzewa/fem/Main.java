package pl.dkostrzewa.fem;

import pl.dkostrzewa.fem.features.FemGridGenerator;
import pl.dkostrzewa.fem.features.GaussInterpolation;
import pl.dkostrzewa.fem.features.HMatrixForElement;
import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.utils.GlobalConstants;

import java.util.List;

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
        // Element selectedElement = femGrid.getElements().get(7);
//
//        for (Element selectedElement : femGrid.getElements()) {
//            double[][] jacobianPC1 = HMatrixForElement.generateJacobianForElement(selectedElement, femGrid.getNodes(), ksi[0], eta[0]);
//            double[][] jacobianPC2 = HMatrixForElement.generateJacobianForElement(selectedElement, femGrid.getNodes(), ksi[1], eta[1]);
//            double[][] jacobianPC3 = HMatrixForElement.generateJacobianForElement(selectedElement, femGrid.getNodes(), ksi[2], eta[2]);
//            double[][] jacobianPC4 = HMatrixForElement.generateJacobianForElement(selectedElement, femGrid.getNodes(), ksi[3], eta[3]);
//
//
//
//            selectedElement.setJacobian1(jacobianPC1);
//            selectedElement.setJacobian2(jacobianPC2);
//            selectedElement.setJacobian3(jacobianPC3);
//            selectedElement.setJacobian4(jacobianPC4);
//
//        }
//
//        Element el = femGrid.getElements().get(3);
//        double[][] j1 = el.getJacobian3();
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                System.out.print(j1[i][j] + " ");
//            }
//            System.out.println();
//        }

        for(Element element : femGrid.getElements()){
            HMatrixForElement.generateH(element,femGrid.getNodes(),ksi,eta,globals);
            System.out.println();
            System.out.println("MATRIX [H]: ");
            GlobalConstants.printMatrixNxM(element.getH(),4,4);
        }

        System.out.println();
        System.out.println("MATRIX [H]: ");
        Element el = femGrid.getElements().get(4);

        GlobalConstants.printMatrixNxM(el.getH(),4,4);

//        double[][] H = HMatrixForElement.generateH(el,femGrid.getNodes(),ksi,eta);
//
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//               System.out.print(H[i][j]+" ");
//            }
//            System.out.println();
//        }


    }


}
