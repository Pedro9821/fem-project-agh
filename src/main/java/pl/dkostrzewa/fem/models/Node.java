package pl.dkostrzewa.fem.models;

public class Node {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", t=" + t +
                ", bc=" + bc +
                '}';
    }
}
