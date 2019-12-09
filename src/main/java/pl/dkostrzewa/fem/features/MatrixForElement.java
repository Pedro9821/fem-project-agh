package pl.dkostrzewa.fem.features;

import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.models.Node;
import pl.dkostrzewa.fem.utils.GlobalConstants;

import java.util.List;

public class MatrixForElement {

    public static double[][] generateJacobianForElement(Element element, double[] ksi, double[] eta) {
        double dxdksi = GlobalConstants.jacobianDerivativesX(ksi, element);
        double dxdeta = GlobalConstants.jacobianDerivativesX(eta, element);
        double dydksi = GlobalConstants.jacobianDerivativesY(ksi, element);
        double dydeta = GlobalConstants.jacobianDerivativesY(eta, element);

        //System.out.println(element.getiDs());

        double[][] jacobian = new double[2][2];
        jacobian[0][0] = dxdksi;
        jacobian[0][1] = dydksi;
        jacobian[1][0] = dxdeta;
        jacobian[1][1] = dydeta;

        return jacobian;
    }

    public static double[][] generateP(Element element, Globals globals) {
        double[][] P = new double[2][4];
        double pc = 1 / Math.sqrt(3);

        List<Node> nodes = element.getNodes();
        for (int i = 0; i < 4; i++) {
            double[] ksiEtaPow = GlobalConstants.ksiEtaSurface[i];
            double[] ksiPow = {ksiEtaPow[0], ksiEtaPow[2]};
            double[] etaPow = {ksiEtaPow[1], ksiEtaPow[3]};

            double[][] pow = new double[2][4];
            for (int j = 0; j < 2; j++) {
                pow = GaussInterpolation.countNfunctionForSurface(ksiPow, etaPow);
            }
            double lPow;

            //pow 4
            if (i == 3) {
                lPow = Math.sqrt(Math.pow(nodes.get(0).getX() - nodes.get(3).getX(), 2) + Math.pow(nodes.get(0).getY() - nodes.get(3).getY(), 2));
            }
            else{
                lPow = Math.sqrt(Math.pow(nodes.get(i+1).getX() - nodes.get(i).getX(), 2) + Math.pow(nodes.get(i+1).getY() - nodes.get(i).getY(), 2));
            }
            double detPow = lPow / 2;

            for (int k = 0; k < pow.length; k++) {
                for (int j = 0; j < pow[k].length; j++) {
                    P[k][j] += pow[k][j]*globals.getAlfa()*globals.getT()*detPow;
                }
            }
        }
        return P;
    }

    public static double[][] generateHbc(Element element, Globals globals) {
        double pc = 1 / Math.sqrt(3);

        List<Node> nodes = element.getNodes();

        double[][] Hbc = new double[4][4];
        boolean[] isBc = new boolean[4];
        boolean[] isBcPow = new boolean[4];

        for (int i = 0; i < nodes.size(); i++) {
            isBc[i] = nodes.get(i).isBc();
        }
        for (int i = 0; i <= 3; i++) {
            if (i == 3) {
                if (isBc[3] && isBc[0]) {
                    isBcPow[3] = true;
                }
            } else if (isBc[i] && isBc[i + 1]) {
                isBcPow[i] = true;
            }

        }

        for (int i = 0; i < 4; i++) {
            if (isBcPow[i]) {
                double[] ksiEtaPow = GlobalConstants.ksiEtaSurface[i];
                double[] ksiPow = {ksiEtaPow[0], ksiEtaPow[2]};
                double[] etaPow = {ksiEtaPow[1], ksiEtaPow[3]};

                double[][] pow = new double[2][4];
                for (int j = 0; j < 2; j++) {
                    pow = GaussInterpolation.countNfunctionForSurface(ksiPow, etaPow);
                }
                double lPow;

                //pow 4
                if (i == 3) {
                    lPow = Math.sqrt(Math.pow(nodes.get(0).getX() - nodes.get(3).getX(), 2) + Math.pow(nodes.get(0).getY() - nodes.get(3).getY(), 2));
                }
                else{
                    lPow = Math.sqrt(Math.pow(nodes.get(i+1).getX() - nodes.get(i).getX(), 2) + Math.pow(nodes.get(i+1).getY() - nodes.get(i).getY(), 2));
                }
                double detPow = lPow / 2;

                double[][] POW1_1 = VxV(pow[0], pow[0]);
                double[][] POW1_2 = VxV(pow[1], pow[1]);


                for (int k = 0; k < POW1_1.length; k++) {
                    for (int j = 0; j < POW1_1[k].length; j++) {
                        POW1_1[k][j] *= globals.getAlfa();
                    }
                }

                for (int k = 0; k < POW1_2.length; k++) {
                    for (int j = 0; j < POW1_2[k].length; j++) {
                        POW1_2[k][j] *= globals.getAlfa();
                    }
                }

                double[][] POW1 = sumMatrix(POW1_1, POW1_2);
                for (int k = 0; k < POW1.length; k++) {
                    for (int j = 0; j < POW1[i].length; j++) {
                        POW1[k][j] *= detPow;
                    }
                }

                for (int k = 0; k < POW1.length; k++) {
                    for (int j = 0; j < POW1[i].length; j++) {
                        Hbc[k][j] += POW1[k][j];
                    }
                }
            }
        }


//        double[] ksiPow1 = new double[2];
//        double[] etaPow1 = new double[2];
//
//        double[] ksiPow2 = new double[2];
//        double[] etaPow2 = new double[2];
//
//        double[] ksiPow3 = new double[2];
//        double[] etaPow3 = new double[2];
//
//        double[] ksiPow4 = new double[2];
//        double[] etaPow4 = new double[2];
//
//        ksiPow1[0] = -pc;
//        ksiPow1[1] = pc;
//        etaPow1[0] = -1;
//        etaPow1[1] = -1;
//
//        ksiPow2[0] = 1;
//        ksiPow2[1] = 1;
//        etaPow2[0] = -pc;
//        etaPow2[1] = pc;
//
//        ksiPow3[0] = pc;
//        ksiPow3[1] = -pc;
//        etaPow3[0] = 1;
//        etaPow3[1] = 1;
//
//        ksiPow4[0] = -1;
//        ksiPow4[1] = -1;
//        etaPow4[0] = pc;
//        etaPow4[1] = -pc;
//
//
//        double[][] pow1 = new double[2][4];
//        double[][] pow2 = new double[2][4];
//        double[][] pow3 = new double[2][4];
//        double[][] pow4 = new double[2][4];
//
//
//        for (int i = 0; i < 2; i++) {
//            pow1 = GaussInterpolation.countNfunctionForSurface(ksiPow1, etaPow1);
//            pow2 = GaussInterpolation.countNfunctionForSurface(ksiPow2, etaPow2);
//            pow3 = GaussInterpolation.countNfunctionForSurface(ksiPow3, etaPow3);
//            pow4 = GaussInterpolation.countNfunctionForSurface(ksiPow4, etaPow4);
//        }
//
//        double lPow1 = Math.sqrt(Math.pow(nodes.get(1).getX() - nodes.get(0).getX(), 2) + Math.pow(nodes.get(1).getY() - nodes.get(0).getY(), 2));
//        double detPow1 = lPow1 / 2;
//        double[][] POW1_1 = VxV(pow1[0], pow1[0]);
//        double[][] POW1_2 = VxV(pow1[1], pow1[1]);
//
//
//        for (int i = 0; i < POW1_1.length; i++) {
//            for (int j = 0; j < POW1_1[i].length; j++) {
//                POW1_1[i][j] *= globals.getAlfa();
//            }
//        }
//
//        for (int i = 0; i < POW1_2.length; i++) {
//            for (int j = 0; j < POW1_2[i].length; j++) {
//                POW1_2[i][j] *= globals.getAlfa();
//            }
//        }
//
//        double[][] POW1 = sumMatrix(POW1_1, POW1_2);
//        for (int i = 0; i < POW1.length; i++) {
//            for (int j = 0; j < POW1[i].length; j++) {
//                POW1[i][j] *= detPow1;
//            }
//        }

//        double[][] dN = GaussInterpolation.countNfunctionForSurface(ksi, eta);

        element.setHbc(Hbc);
        return Hbc;
    }

    public static double[][] generateC(Element element, double[][] dNdKsi, double[][] dNdEta, double[][] dN, Globals globals) {
        double[][] C = new double[4][4];

        for (int p = 0; p < 4; p++) {
            double[][] jacobian = generateJacobianForElement(element, dNdKsi[p], dNdEta[p]);
            double detJacobian = (jacobian[0][0] * jacobian[1][1]) - (jacobian[0][1] * jacobian[1][0]);

            double[][] N = VxV(dN[p], dN[p]);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    C[i][j] += globals.getC() * globals.getRo() * N[i][j] * detJacobian;
                }
            }

        }

        element.setC(C);

        return C;
    }


    public static double[][] generateH(Element element, double[][] dNdKsi, double[][] dNdEta, Globals globals) {
        double[][] H = new double[4][4];

        for (int p = 0; p < 4; p++) {
            double[][] dNdXY = new double[2][4];

            double[] dNdX = new double[4];
            double[] dNdY = new double[4];

            //obliczamy jacobian dla kazdego punktu calkowania
            double[][] jacobian = generateJacobianForElement(element, dNdKsi[p], dNdEta[p]);
            double detJacobian = (jacobian[0][0] * jacobian[1][1]) - (jacobian[0][1] * jacobian[1][0]);

//            System.out.println("Jacobian for: " + (p + 1) + " integration point");
//            GlobalConstants.printMatrixNxM(jacobian, 2, 2);
//            System.out.println("Det[J]=" + detJacobian);
//            System.out.println();


            double[][] reverseJacobian = new double[2][2];
            reverseJacobian[0][0] = jacobian[1][1];
            reverseJacobian[0][1] = -jacobian[0][1];
            reverseJacobian[1][0] = -jacobian[1][0];
            reverseJacobian[1][1] = jacobian[0][0];

            reverseJacobian[0][0] = (1 / detJacobian) * reverseJacobian[0][0];
            reverseJacobian[0][1] = (1 / detJacobian) * reverseJacobian[0][1];
            reverseJacobian[1][0] = (1 / detJacobian) * reverseJacobian[1][0];
            reverseJacobian[1][1] = (1 / detJacobian) * reverseJacobian[1][1];

//            System.out.println("Reverse Jacobian for: " + (p + 1) + " integration point");
//            GlobalConstants.printMatrixNxM(reverseJacobian, 2, 2);
//            System.out.println();

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
                    H[i][j] += globals.getK() * (X[i][j] + Y[i][j]) * detJacobian;
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

    public static double[][] sumMatrix(double[][] A, double[][] B) {
        double[][] C = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }
}
