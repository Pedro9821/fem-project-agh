package pl.dkostrzewa.fem;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;
import pl.dkostrzewa.fem.features.FemGridGenerator;
import pl.dkostrzewa.fem.features.GaussInterpolation;
import pl.dkostrzewa.fem.features.MatrixForElement;
import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.models.Node;
import pl.dkostrzewa.fem.utils.GlobalConstants;

import java.util.List;
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
            MatrixForElement.combineH(element);
            MatrixForElement.generateP(element, globals);
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
        System.out.println("Select Finite Element <1," + (femGrid.getElements().size()) + ">");
        int elementNo = keyboard.nextInt() - 1;
        Element el = femGrid.getElements().get(elementNo);
        System.out.println(el);
        System.out.println("MATRIX [H]: ");
        GlobalConstants.printMatrixNxM(el.getH(), 4, 4);
        System.out.println("MATRIX [C]: ");
        GlobalConstants.printMatrixNxM(el.getC(), 4, 4);
        System.out.println("MATRIX [Hbc]: ");
        GlobalConstants.printMatrixNxM(el.getHbc(), 4, 4);

        System.out.println("VECTOR [P]: ");
        GlobalConstants.printVector(el.getP(), 4);

        //Agregacja
        int nodeCount = (int) (globals.getnH() * globals.getnW());
        double[][] H_Global = new double[nodeCount][nodeCount];
        double[][] C_Global = new double[nodeCount][nodeCount];
        double[] P_Global = new double[nodeCount];
        for (Element element : femGrid.getElements()) {
            List id = element.getiDs();

            for (int i = 0; i < 4; i++) {
                P_Global[(int) id.get(i) - 1] += element.getP()[i];
                for (int j = 0; j < 4; j++) {
                    H_Global[(int) id.get(i) - 1][(int) id.get(j) - 1] += element.getH()[i][j];
                    C_Global[(int) id.get(i) - 1][(int) id.get(j) - 1] += element.getC()[i][j];
                }
            }

        }

        // System.out.println("Matrix [H] Global");
        // GlobalConstants.printMatrixNxM(H_Global, nodeCount, nodeCount);

        // System.out.println("Matrix [C] Global");
        //GlobalConstants.printMatrixNxM(C_Global, nodeCount, nodeCount);

        // System.out.println("Matrix [P] Global");
        // GlobalConstants.printVector(P_Global, nodeCount);

        double[][] H_final = new double[nodeCount][nodeCount];
        double[][] C_final = new double[nodeCount][nodeCount];

        double[] t0 = new double[nodeCount];

        for (int i = 0; i < femGrid.getNodes().size(); i++) {
            t0[i] = femGrid.getNodes().get(i).getT();
        }


        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                H_final[i][j] = H_Global[i][j] + (C_Global[i][j] / globals.getStepTime());
                C_final[i][j] = (C_Global[i][j] / globals.getStepTime());
            }
        }
       // System.out.println("Matrix [H] + [C]/dT");
      //  GlobalConstants.printMatrixNxM(H_final, nodeCount, nodeCount);

        double[] CxT0 = GlobalConstants.MatrixXVector(C_final, t0);
        double[] P_Final = new double[nodeCount];

        for (int i = 0; i < nodeCount; i++) {
            P_Final[i] = CxT0[i] - P_Global[i];
        }

        System.out.println("Vector {P} = [C]/dT *{t0} + {P}");
        GlobalConstants.printVector(P_Final, nodeCount);
        System.out.println();
        System.out.println("Max and min temperature in each step");
        double tStep = globals.getStepTime();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < femGrid.getNodes().size(); j++) {
                t0[j] = femGrid.getNodes().get(j).getT();
            }
            Matrix H = Matrix.from2DArray(H_final);
            Matrix C = Matrix.from2DArray(C_final);
            Vector temperature = Vector.fromArray(t0);
            Vector P = Vector.fromArray(P_Global);

            Vector C_t = C.multiply(temperature);
            C_t = C_t.subtract(P);

            Vector nextTemperatures = C_t.multiply(H.withInverter(LinearAlgebra.GAUSS_JORDAN).inverse());

            for (int j = 0; j < femGrid.getNodes().size(); j++) {
                femGrid.getNodes().get(j).setT(nextTemperatures.get(j));
            }
            System.out.println("Time[s] " + (tStep*(i+1)) + " -> Min. temperature: " + nextTemperatures.min() + " Max tempreature: " + nextTemperatures.max());

        }


    }


}
