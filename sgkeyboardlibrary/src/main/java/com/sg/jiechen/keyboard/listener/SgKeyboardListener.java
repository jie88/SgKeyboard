package com.sg.jiechen.keyboard.listener;


import android.widget.EditText;

/**
 * author : ${CHENJIE}
 * created at  2017/8/3 08:41
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public abstract class SgKeyboardListener {
  /**
   * 键盘弹出
   */
  public abstract void onShow(EditText v);

  /**
   * 键盘收起
   */
  public abstract void onHide(EditText v);

  /**
   * 键盘完成事件
   */
  public void onFinishClickHidden(EditText v) {}
}
