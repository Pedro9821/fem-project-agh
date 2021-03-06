package pl.dkostrzewa.fem.features;

import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.models.Node;

import java.util.ArrayList;
import java.util.List;

public class FemGridGenerator {

    private List<Node> femGridNodes;
    List<Element> femGridElements;

    public FemGrid generateFemGrid(Globals globals){
        FemGrid femGrid = new FemGrid();
        femGridNodes = femGrid.getNodes();
        femGridElements = femGrid.getElements();

        generateNodes(globals, femGridNodes);
        //femGrid.showFemNodes();

        generateElements(globals, femGridElements);
        //femGrid.showFemElements();

        return femGrid;
    }

    private static void generateNodes(Globals globals, List femGridNodes) {
        double H = globals.getH();
        double W = globals.getW();
        double nW = globals.getnW();
        double nH = globals.getnH();

        double dX = W / (nW - 1);
        double dY = H / (nH - 1);
        int nodeId = 0;
        for (double i = 0; i < nW; i++) {

            for (double j = 0; j < nH; j++) {
                nodeId++;
                Node node = new Node();
                double x = i * dX;
                double y = j * dY;

                if (x == 0 || y == 0 || y == H || x == W) {
                    node.setBc(true);
                }

                node.setX(x);
                node.setY(y);
                node.setId(nodeId);

                double initialT = globals.getInitialTemperature();
                node.setT(initialT);
                femGridNodes.add(node);
            }
        }
    }

    private void generateElements(Globals globals, List femGridElements) {
        double nH = globals.getnH();
        double nE = globals.getnE();
        double nN = globals.getnN();

        int missingElements = 0;
        for (int i = 1; i <= nE + 2; i++) {
            if (i % nH == 0)
                missingElements++;
        }


        for (int i = 1; i <= nE + missingElements; i++) {
            if (i % nH == 0) {
                continue;
            }

            Element element = new Element();
            List<Integer> iDs = new ArrayList<>();
            List<Node> nodes = new ArrayList<>();


            int id1 = i;
            int id2 = (int) (id1 + nH);
            int id3 = id2 + 1;
            int id4 = id1 + 1;

            iDs.add(id1);
            iDs.add(id2);
            iDs.add(id3);
            iDs.add(id4);

            nodes.add(this.femGridNodes.get(id1-1));
            nodes.add(this.femGridNodes.get(id2-1));
            nodes.add(this.femGridNodes.get(id3-1));
            nodes.add(this.femGridNodes.get(id4-1));

            element.setiDs(iDs);
            element.setNodes(nodes);

            femGridElements.add(element);

        }

    }

}
