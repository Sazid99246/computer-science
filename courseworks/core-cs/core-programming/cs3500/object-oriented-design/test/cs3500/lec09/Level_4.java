package cs3500.lec09;

import java.io.InputStreamReader;

import cs3500.lec09.controller.BasicPuralaxController;
import cs3500.lec09.controller.PuralaxController;
import cs3500.lec09.model.BasicModelBuilder;
import cs3500.lec09.model.ModelBuilder;
import cs3500.lec09.model.PuralaxModel;
import cs3500.lec09.model.tile.BasicTileFactory;

public class Level_4 {

  public static void main(String[] args) {
    ModelBuilder builder = new BasicModelBuilder(new BasicTileFactory());
    PuralaxModel model = builder.parseInputString(
            "3 3 B\n" +
                    "0 1 M G 1\n" +
                    "1 1 P B\n" +
                    "2 1 I G"
    );
    PuralaxController controller =
            new BasicPuralaxController(new InputStreamReader(System.in), System.out);
    controller.playGame(model);
  }
}
