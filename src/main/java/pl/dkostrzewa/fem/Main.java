package pl.dkostrzewa.fem;

import pl.dkostrzewa.fem.Features.FemGridGenerator;
import pl.dkostrzewa.fem.models.FemGrid;
import pl.dkostrzewa.fem.models.Globals;

public class Main {

    public static void main(String[] args) {

        Globals globals = new Globals();
        System.out.println(globals);

        FemGridGenerator femGridGenerator = new FemGridGenerator();
        FemGrid femGrid = femGridGenerator.generateFemGrid(globals);

        femGrid.showFemNodes();
        femGrid.showFemElements();


    }


}
