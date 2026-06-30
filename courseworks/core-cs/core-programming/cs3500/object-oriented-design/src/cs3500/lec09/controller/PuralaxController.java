package cs3500.lec09.controller;

import cs3500.lec09.model.PuralaxModel;

public interface PuralaxController {

  /**
   * 主要方法去开始和进行游戏
   *
   * @param model 开始游戏的游戏模型
   * @throws IllegalArgumentException 如果参数为 null
   * @throws IllegalStateException    如果游戏模型未开始。或控制器无法与玩家交互。
   */
  void playGame(PuralaxModel model);
}
