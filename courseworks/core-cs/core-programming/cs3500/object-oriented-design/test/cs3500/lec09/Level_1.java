package cs3500.lec09;

import java.io.InputStreamReader;

import cs3500.lec09.controller.BasicPuralaxController;
import cs3500.lec09.controller.PuralaxController;
import cs3500.lec09.model.BasicModelBuilder;
import cs3500.lec09.model.ModelBuilder;
import cs3500.lec09.model.PuralaxModel;
import cs3500.lec09.model.tile.BasicTileFactory;

public class Level_1 {

  public static void main(String[] args) {
    ModelBuilder builder = new BasicModelBuilder(new BasicTileFactory());
    PuralaxModel model = builder.parseInputString(
            "3 3 B\n" +
                    "1 1 M B 1\n" +
                    "0 2 I G\n" +
                    "1 2 I G"
    );
    PuralaxController controller =
            new BasicPuralaxController(new InputStreamReader(System.in), System.out);
    controller.playGame(model);
  }
}
