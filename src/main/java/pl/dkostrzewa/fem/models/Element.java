package pl.dkostrzewa.fem.models;

import java.util.List;

public class Element {
    private List<Integer> iDs;
    private List<Node> nodes;
    private double[][] H = new double[4][4];
    private double[][] C = new double[4][4];
    private double[][] Hbc = new double[4][4];

    public double[][] getC() {
        return C;
    }

    public void setC(double[][] c) {
        C = c;
    }

    public double[][] getHbc() {
        return Hbc;
    }

    public void setHbc(double[][] hbc) {
        Hbc = hbc;
    }



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

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Element{" +
                "iDs=" + iDs +
                ", nodes=" + nodes +
                '}';
    }
}

