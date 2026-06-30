package cs3500.lec09.controller;

// 退出游戏异常
public class ExitGameException extends RuntimeException {

  public ExitGameException(String message) {
    super(message);
  }
}
