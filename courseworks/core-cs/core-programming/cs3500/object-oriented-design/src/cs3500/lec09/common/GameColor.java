package cs3500.lec09.common;

public enum GameColor implements IColor{
  RED("Red", "Rd"),    // 红
  YELLOW("Yellow", "Yl"), // 黄
  BLUE("Blue", "Bl"),   // 蓝
  GREEN("Green", "Gn"),  // 绿
  PURPLE("Purple", "Pu"), // 紫
  GRAY("Gray", "Gy");   // 灰


  private final String fullName;  // 全称
  private final String shortName; // 简称

  GameColor(String fullName, String shortName) {
    this.fullName = fullName;
    this.shortName = shortName;
  }

  @Override
  public String getFullName() {
    return fullName;
  }

  @Override
  public String getShortName() {
    return shortName;
  }
}
