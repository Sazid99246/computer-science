package cs3500.lec09.common;

/**
 * 游戏中同一使用的颜色。
 * 在文字视图中使用颜色的首字母代表图块，
 *  例如: R1 (红色图块，1点数)
 *       B  (蓝色图块，0点数)
 */
public interface IColor {

  /**
   * 获取全称， 比如: "Blue", "Red".
   * @return 颜色全称
   */
  public String getFullName();

  /**
   * 获取简称，比如: "B", "R".
   * @return 颜色简称
   */
  public String getShortName();
}
