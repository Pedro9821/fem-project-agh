package pl.dkostrzewa.fem;

public class Globals {
    private double H;
    private double W;

    private int nH;
    private int nW;


    private double nN;
    private double nE;

    public Globals(){}


    public void setnN(){
        this.nN = this.nH * this.nW;
    }

    public void setnE(){
        this.nE = (nH-1)*(nW-1);
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public int getnH() {
        return nH;
    }

    public void setnH(int nH) {
        this.nH = nH;
    }

    public int getnW() {
        return nW;
    }

    public void setnW(int nW) {
        this.nW = nW;
    }

    public double getnN() {
        return nN;
    }

    public double getnE() {
        return nE;
    }

    @Override
    public String toString() {
        return "Globals{" +
                "H=" + H +
                ", W=" + W +
                ", nH=" + nH +
                ", nW=" + nW +
                ", nN=" + nN +
                ", nE=" + nE +
                '}';
    }
}
