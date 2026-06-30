package cs3500.lec09.view;

import java.io.IOException;

public interface PuralaxView {

  /**
   * 以某种方式渲染模型
   * @throws IOException 如果模型渲染错误
   */
  void render() throws IOException;
}
