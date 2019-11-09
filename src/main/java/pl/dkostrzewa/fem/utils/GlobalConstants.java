package pl.dkostrzewa.fem.utils;

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

    public static double shapeFunctionNiDerivative1(double ksi) {
        return -(1 - ksi) / 4;
    }

    public static double shapeFunctionNiDerivative2(double ksi) {
        return -(1 + ksi) / 4;
    }

    public static double shapeFunctionNiDerivative3(double ksi) {
        return (1 + ksi) / 4;
    }

    public static double shapeFunctionNiDerivative4(double ksi) {
        return (1 - ksi) / 4;
    }

}
