package pl.dkostrzewa.fem.Features;

import pl.dkostrzewa.fem.models.Element;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;
import pl.dkostrzewa.fem.models.Node;

import java.util.ArrayList;
import java.util.List;

public class FemGridGenerator {
    public FemGrid generateFemGrid(Globals globals){
        FemGrid femGrid = new FemGrid();
        List<Node> femGridNodes = femGrid.getNodes();
        List<Element> femGridElements = femGrid.getElements();

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

        for (double i = 0; i < nW; i++) {

            for (double j = 0; j < nH; j++) {

                Node node = new Node();
                double x = i * dX;
                double y = j * dY;

                if (x == 0 || y == 0 || y == H || x == W) {
                    node.setBc(true);
                }

                node.setX(x);
                node.setY(y);
                femGridNodes.add(node);
            }
        }
    }

    private static void generateElements(Globals globals, List femGridElements) {
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

            int id1 = i;
            int id2 = (int) (id1 + nH);
            int id3 = id2 + 1;
            int id4 = id1 + 1;

            iDs.add(id1);
            iDs.add(id2);
            iDs.add(id3);
            iDs.add(id4);

            element.setiDs(iDs);

            femGridElements.add(element);

        }

    }

}
