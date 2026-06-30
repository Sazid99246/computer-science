package cs3500.lec09.view;

import java.io.InputStreamReader;

import cs3500.lec09.controller.BasicPuralaxController;
import cs3500.lec09.controller.PuralaxController;
import cs3500.lec09.model.BasicModelBuilder;
import cs3500.lec09.model.ModelBuilder;
import cs3500.lec09.model.PuralaxModel;
import cs3500.lec09.model.tile.BasicTileFactory;

public class Level_5 {
  public static void main(String[] args) {
    ModelBuilder builder = new BasicModelBuilder(new BasicTileFactory());
    PuralaxModel model = builder.parseInputString(
            "3 3 B\n" +
                    "0 0 W\n" +
                    "0 1 M Y 1\n" +
                    "0 2 M G 1\n" +
                    "1 1 N [R,G,B]\n" +
                    "1 2 P B\n" +
                    "2 1 M G 1"
    );
    PuralaxController controller =
            new BasicPuralaxController(new InputStreamReader(System.in), System.out);
    controller.playGame(model);
  }
}
