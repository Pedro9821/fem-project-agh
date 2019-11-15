package pl.dkostrzewa.fem.models;

import java.util.List;

public class Element {
    private List<Integer> iDs;
    private Double[][] H = new Double[4][4];
    private Double[][] C = new Double[4][4];
    private Double[][] Hbc = new Double[4][4];
    private Double[][] jacobian = new Double[2][2];

    public Element(){}

    public List getiDs() {
        return iDs;
    }

    public void setiDs(List iDs) {
        this.iDs = iDs;
    }

    @Override
    public String toString() {
        return "Element{" +
                "iDs=" + iDs +
                '}';
    }
}

