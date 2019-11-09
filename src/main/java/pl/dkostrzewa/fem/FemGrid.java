package pl.dkostrzewa.fem;

import java.util.ArrayList;
import java.util.List;

public class FemGrid {
    private List<Node> nodes = new ArrayList<>();
    private List<Element> elements = new ArrayList<>();

    public FemGrid() {
    }

    public FemGrid(List<Node> nodes, List<Element> elements) {
        this.nodes = nodes;
        this.elements = elements;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void showFemNodes() {
        int i = 0;
        for (Node node : nodes) {
            i++;
            System.out.println("Node: no." + i + " " + node);
        }
    }

    public void showFemElements() {
        int i = 0;
        for (Element element : elements) {
            i++;
            System.out.println("Element no." + i + " " + element);

        }
    }
}
