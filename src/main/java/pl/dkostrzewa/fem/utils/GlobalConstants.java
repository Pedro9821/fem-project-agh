package pl.dkostrzewa.fem.utils;

import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.Node;

import java.util.List;

public class GlobalConstants {

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

    public static double jacobianDerivativesX(double[] shapeFunctionDeratives, Element element, List<Node> nodes) {
        double result = 0.0;
        List<Integer> elementIds = element.getiDs();

        double[] x = new double[4];
        int id1 = elementIds.get(0) - 1;
        double x1 = nodes.get(elementIds.get(0) - 1).getX();
        double x2 = nodes.get(((Integer) elementIds.get(1)) - 1).getX();
        double x3 = nodes.get(((Integer) elementIds.get(2)) - 1).getX();
        double x4 = nodes.get(((Integer) elementIds.get(3)) - 1).getX();

        x[0] = x1;
        x[1] = x2;
        x[2] = x3;
        x[3] = x4;

        for (int i = 0; i < 4; i++) {
            result += shapeFunctionDeratives[i] * x[i];
        }

        return result;
    }

    public static double jacobianDerivativesY(double[] shapeFunctionDeratives, Element element, List<Node> nodes) {
        double result = 0;
        List elementIds = element.getiDs();

        double[] y = new double[4];
        double y1 = nodes.get((Integer) elementIds.get(0) - 1).getY();
        double y2 = nodes.get((Integer) elementIds.get(1) - 1).getY();
        double y3 = nodes.get((Integer) elementIds.get(2) - 1).getY();
        double y4 = nodes.get((Integer) elementIds.get(3) - 1).getY();

        y[0] = y1;
        y[1] = y2;
        y[2] = y3;
        y[3] = y4;

        for (int i = 0; i < 4; i++) {
            result += shapeFunctionDeratives[i] * y[i];
        }

        return result;
    }


}
