package pl.dkostrzewa.fem.features;


import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.utils.GlobalConstants;

import java.util.List;

public class GaussInterpolation {
    private final static int N = 4; //liczba pochodnych N1/dKsi N2/dksi ... N4/dKsi

    public double[][] countKsiDerivatives(Globals globals) {
        int numberPc = globals.getCountPc() * globals.getCountPc();
        List pc = globals.getPc();

        double[][] ksiDerivatives = new double[numberPc][N];

        for (int i = 0; i < numberPc; i++) {
            int index = i < 2 ? 0 : 1; //wspolredne eta
            //double selectedPc = (Double) pc.get(index);
            double selectedPc = (Double) globals.getIntegrationPoints().get(i).getEta();
            ksiDerivatives[i][0] = GlobalConstants.shapeFunctionKsiDerivative1(selectedPc);
            ksiDerivatives[i][1] = GlobalConstants.shapeFunctionKsiDerivative2(selectedPc);
            ksiDerivatives[i][2] = GlobalConstants.shapeFunctionKsiDerivative3(selectedPc);
            ksiDerivatives[i][3] = GlobalConstants.shapeFunctionKsiDerivative4(selectedPc);
        }

        return ksiDerivatives;
    }

    public double[][] countEtaDerivatives(Globals globals) {
        int numberPc = globals.getCountPc() * globals.getCountPc();
        List pc = globals.getPc();

        double[][] etaDerivatives = new double[numberPc][N];

        for (int i = 0; i < numberPc; i++) {
            int index = i == 0 || i == 3 ? 0 : 1; //wspolrzednia ksi
            //double selectedPc = (Double) pc.get(index);
            double selectedPc = (Double) globals.getIntegrationPoints().get(i).getKsi();
            etaDerivatives[i][0] = GlobalConstants.shapeFunctionEtaDerivative1(selectedPc);
            etaDerivatives[i][1] = GlobalConstants.shapeFunctionEtaDerivative2(selectedPc);
            etaDerivatives[i][2] = GlobalConstants.shapeFunctionEtaDerivative3(selectedPc);
            etaDerivatives[i][3] = GlobalConstants.shapeFunctionEtaDerivative4(selectedPc);
        }

        return etaDerivatives;
    }

    public double[][] countShapeFunctionValues(Globals globals) {
        int numberPc = globals.getCountPc() * globals.getCountPc();
        List pc = globals.getPc();
        double[][] shapeFunctionValues = new double[numberPc][N];

        for (int i = 0; i < numberPc; i++) {
            int indexKsi = i == 0 || i == 3 ? 0 : 1;
            int indexEta = i < 2 ? 0 : 1;
            double eta = (Double) globals.getIntegrationPoints().get(i).getEta();
            double ksi = (Double) globals.getIntegrationPoints().get(i).getKsi();
            shapeFunctionValues[i][0] = GlobalConstants.shapeFunction1(ksi, eta);
            shapeFunctionValues[i][1] = GlobalConstants.shapeFunction2(ksi, eta);
            shapeFunctionValues[i][2] = GlobalConstants.shapeFunction3(ksi, eta);
            shapeFunctionValues[i][3] = GlobalConstants.shapeFunction4(ksi, eta);

        }
        return shapeFunctionValues;

    }

    public static double[][] countNfunctionForSurface(double[] ksi, double[] eta) {
        double[][] dN = new double[2][4];
        for (int i = 0; i < 2; i++) {
            dN[i][0] = GlobalConstants.shapeFunction1(ksi[i], eta[i]);
            dN[i][1] = GlobalConstants.shapeFunction2(ksi[i], eta[i]);
            dN[i][2] = GlobalConstants.shapeFunction3(ksi[i], eta[i]);
            dN[i][3] = GlobalConstants.shapeFunction4(ksi[i], eta[i]);

        }

        return dN;
    }
}
