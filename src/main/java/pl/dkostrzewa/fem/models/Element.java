package pl.dkostrzewa.fem.models;

import java.util.List;

public class Element {
    private List iDs;

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

