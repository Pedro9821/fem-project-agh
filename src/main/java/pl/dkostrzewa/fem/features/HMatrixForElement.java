package pl.dkostrzewa.fem.features;

import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.Node;
import pl.dkostrzewa.fem.utils.GlobalConstants;

import java.util.List;

public class HMatrixForElement {

    public static double[][] generateJacobianForElement(Element element, List<Node> nodes, double[] ksi, double[] eta) {
        double dxdksi = GlobalConstants.jacobianDerivativesX(ksi, element, nodes);
        double dxdeta = GlobalConstants.jacobianDerivativesX(eta, element, nodes);
        double dydksi = GlobalConstants.jacobianDerivativesY(ksi, element, nodes);
        double dydeta = GlobalConstants.jacobianDerivativesY(eta, element, nodes);

        //System.out.println(element.getiDs());

        double[][] jacobian = new double[2][2];
        jacobian[0][0] = dxdksi;
        jacobian[0][1] = dydksi;
        jacobian[1][0] = dxdeta;
        jacobian[1][1] = dydeta;

        return jacobian;
    }


    public static double[][] generateH(Element element, List<Node> nodes, double[][] dNdKsi, double[][] dNdEta) {
        double[][] H = new double[4][4];

        for (int p = 0; p < 4; p++) {
            double[][] dNdXY = new double[2][4];

            double[] dNdX = new double[4];
            double[] dNdY = new double[4];

            //obliczamy jacobian dla kazdego punktu calkowania
            double[][] jacobian = generateJacobianForElement(element, nodes, dNdKsi[p], dNdEta[p]);
            double detJacobian = (jacobian[0][0] * jacobian[1][1]) - (jacobian[0][1] * jacobian[1][0]);

            System.out.println("Jacobian for: " + (p + 1) + " integration point");
            GlobalConstants.printMatrixNxM(jacobian, 2, 2);
            System.out.println("Det[J]=" + detJacobian);
            System.out.println();


            double[][] reverseJacobian = new double[2][2];
            reverseJacobian[0][0] = jacobian[1][1];
            reverseJacobian[0][1] = -jacobian[0][1];
            reverseJacobian[1][0] = -jacobian[1][0];
            reverseJacobian[1][1] = jacobian[0][0];

            reverseJacobian[0][0] = (1 / detJacobian) * reverseJacobian[0][0];
            reverseJacobian[0][1] = (1 / detJacobian) * reverseJacobian[0][1];
            reverseJacobian[1][0] = (1 / detJacobian) * reverseJacobian[1][0];
            reverseJacobian[1][1] = (1 / detJacobian) * reverseJacobian[1][1];

            System.out.println("Reverse Jacobian for: " + (p + 1) + " integration point");
            GlobalConstants.printMatrixNxM(reverseJacobian, 2, 2);
            System.out.println();

            double[][] dNdKsidEta = new double[2][4];
            dNdKsidEta[0][0] = dNdKsi[p][0];
            dNdKsidEta[0][1] = dNdKsi[p][1];
            dNdKsidEta[0][2] = dNdKsi[p][2];
            dNdKsidEta[0][3] = dNdKsi[p][3];

            dNdKsidEta[1][0] = dNdEta[p][0];
            dNdKsidEta[1][1] = dNdEta[p][1];
            dNdKsidEta[1][2] = dNdEta[p][2];
            dNdKsidEta[1][3] = dNdEta[p][3];

            dNdXY = calculateMatrix(reverseJacobian, dNdKsidEta);

            dNdX[0] = dNdXY[0][0];
            dNdX[1] = dNdXY[0][1];
            dNdX[2] = dNdXY[0][2];
            dNdX[3] = dNdXY[0][3];

            dNdY[0] = dNdXY[1][0];
            dNdY[1] = dNdXY[1][1];
            dNdY[2] = dNdXY[1][2];
            dNdY[3] = dNdXY[1][3];

            double[][] X = new double[4][4];

            X = VxV(dNdX, dNdX);

            double[][] Y = new double[4][4];

            Y = VxV(dNdY, dNdY);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    H[i][j] += 25 * (X[i][j] + Y[i][j]) * detJacobian;
                }
            }

        }

        element.setH(H);

        return H;
    }


    public static double[][] calculateMatrix(double[][] A, double[][] B) {
        double[][] C = new double[2][4];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                C[i][j] = 0;
            }
        }

        for (int k = 0; k < 2; k++) {
            for (int l = 0; l < 4; l++) {
                for (int i = 0; i < 2; i++) {
                    C[k][l] += (A[k][i] * B[i][l]);
                }
            }
        }

        return C;

    }

    public static double[][] VxV(double[] A, double[] B) {
        double[][] C = new double[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                C[i][j] = 0;
            }
        }

        for (int l = 0; l < 4; l++) {
            for (int i = 0; i < 4; i++) {
                C[l][i] += (A[l] * B[i]);
            }
        }

        return C;
    }
}
