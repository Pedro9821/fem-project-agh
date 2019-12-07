package pl.dkostrzewa.fem.utils;

import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.Node;

import java.text.DecimalFormat;
import java.util.List;

public class GlobalConstants {

    private static double pc = 1 / Math.sqrt(3);

    public static double[][] ksiEtaSurface = {{-pc, -1, pc, -1}, {1, -pc, 1, pc}, {pc, 1, -pc, 1}, {-1, pc, -1, -pc}};

    public static double shapeFunction1(double ksi, double ni) {
        return (1 - ksi) * (1 - ni) / 4;
    }

    public static double shapeFunction2(double ksi, double ni) {
        return (1 + ksi) * (1 - ni) / 4;
    }

    public static double shapeFunction3(double ksi, double ni) {
        return (1 + ksi) * (1 + ni) / 4;
    }

    public static double shapeFunction4(double ksi, double ni) {
        return (1 - ksi) * (1 + ni) / 4;
    }

    public static double shapeFunctionKsiDerivative1(double ni) {
        return -(1 - ni) / 4;
    }

    public static double shapeFunctionKsiDerivative2(double ni) {
        return (1 - ni) / 4;
    }

    public static double shapeFunctionKsiDerivative3(double ni) {
        return (1 + ni) / 4;
    }

    public static double shapeFunctionKsiDerivative4(double ni) {
        return -(1 + ni) / 4;
    }

    public static double shapeFunctionEtaDerivative1(double ksi) {
        return -(1 - ksi) / 4;
    }

    public static double shapeFunctionEtaDerivative2(double ksi) {
        return -(1 + ksi) / 4;
    }

    public static double shapeFunctionEtaDerivative3(double ksi) {
        return (1 + ksi) / 4;
    }

    public static double shapeFunctionEtaDerivative4(double ksi) {
        return (1 - ksi) / 4;
    }

    public static Double jacobianDerivativesX(double[] shapeFunctionDerivatives, Element element) {
        double result = 0.0;
        List<Integer> elementIds = element.getiDs();
        List<Node> elementNodes = element.getNodes();

        double[] x = new double[4];
        int id1 = elementIds.get(0) - 1;
        double x1 = elementNodes.get(0).getX();
        double x2 = elementNodes.get(1).getX();
        double x3 = elementNodes.get(2).getX();
        double x4 = elementNodes.get(3).getX();

        x[0] = x1;
        x[1] = x2;
        x[2] = x3;
        x[3] = x4;

        for (int i = 0; i < 4; i++) {
            result += shapeFunctionDerivatives[i] * x[i];
        }

        return result;
    }

    public static double jacobianDerivativesY(double[] shapeFunctionDerivatives, Element element) {
        double result = 0;
        List elementIds = element.getiDs();
        List<Node> elementNodes = element.getNodes();

        double[] y = new double[4];
        double y1 = elementNodes.get(0).getY();
        double y2 = elementNodes.get(1).getY();
        double y3 = elementNodes.get(2).getY();
        double y4 = elementNodes.get(3).getY();

        y[0] = y1;
        y[1] = y2;
        y[2] = y3;
        y[3] = y4;

        for (int i = 0; i < 4; i++) {
            result += shapeFunctionDerivatives[i] * y[i];
        }

        return result;
    }

    public static double dNdx(double dYdKsi, double dYdEta, double dNdKsi, double dNdEta) {
        return dYdEta * dNdKsi + dYdKsi * dNdEta;
    }

    public static double dNdy(double dXdKsi, double dXdEta, double dNdKsi, double dNdEta) {
        return dXdEta * dNdKsi + dXdKsi * dNdEta;
    }

    public static void printMatrixNxM(double[][] m, int N, int M) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(m[i][j] + "\t");
            }
            System.out.println();
        }
    }


}
