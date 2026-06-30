package cs3500.lec09.model.tile;

import cs3500.lec09.common.IColor;
import java.util.List;

public class BasicTileFactory implements TileFactory{

  @Override
  public Tile createEmptyTile() {
    return new EmptyTile();
  }

  @Override
  public Tile createMobileTile(IColor color, int dots) {
    return new SimpleTile(color, dots);
  }

  @Override
  public Tile createImmobileTile(IColor color) {
    return new SimpleTile(color, 0);
  }

  @Override
  public Tile createWall() {
    return new WallTile();
  }

  @Override
  public Tile createNestedTile(List<IColor> colors) {
    return new NestedTile(colors);
  }

  @Override
  public Tile createPainterTile(IColor barColor) {
    return new PainterTile(barColor);
  }
}
