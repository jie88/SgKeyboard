package com.sg.jiechen.keyboard.ui;


/**
 * author : ${CHENJIE}
 * created at  2017/8/2 11:02
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public enum SgKeyboardLayer {
  /**
   * 小写 默认图层
   */
  LOW("Abc"),
  /**
   * 大写
   */
  CAP("ABC"),
  /**
   * 数字
   */
  DIG("123"),
  /**
   * 字符
   */
  SYM("符");

  private String keyType;

  SgKeyboardLayer(String keyType) {
    this.keyType = keyType;
  }

  @Override
  public String toString() {
    return keyType;
  }
}
