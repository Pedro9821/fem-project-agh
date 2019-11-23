package pl.dkostrzewa.fem.models;

import java.util.List;

public class Element {
    private List<Integer> iDs;
    private double[][] H = new double[4][4];
    private Double[][] C = new Double[4][4];
    private Double[][] Hbc = new Double[4][4];


    public Element() {
    }

    public List getiDs() {
        return iDs;
    }

    public void setiDs(List iDs) {
        this.iDs = iDs;
    }

    public double[][] getH() {
        return H;
    }

    public void setH(double[][] h) {
        H = h;
    }

    @Override
    public String toString() {
        return "Element{" +
                "iDs=" + iDs +
                '}';
    }
}

