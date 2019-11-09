package pl.dkostrzewa.fem.models;

public class Node {
    private double x;
    private double y;
    private double t;

    boolean bc = false; //warunek brzegowy

    public Node(){}

    public Node(double x, double y, boolean bc) {
        this.x = x;
        this.y = y;
        this.bc = bc;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isBc() {
        return bc;
    }

    public void setBc(boolean bc) {
        this.bc = bc;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", t=" + t +
                ", bc=" + bc +
                '}';
    }
}
