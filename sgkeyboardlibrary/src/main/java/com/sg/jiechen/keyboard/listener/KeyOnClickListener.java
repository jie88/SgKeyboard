package com.sg.jiechen.keyboard.listener;


import android.view.View;

/**
 * author : ${CHENJIE}
 * created at  2017/8/1 20:37
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public abstract class KeyOnClickListener implements View.OnClickListener {



  public KeyOnClickListener() {
  }

  public KeyOnClickListener(long MIN_CLICK_DELAY_TIME) {
    this.MIN_CLICK_DELAY_TIME = MIN_CLICK_DELAY_TIME;
  }



  public long MIN_CLICK_DELAY_TIME = 800;
  private long lastClickTime = 0;

  @Override
  public void onClick(View v) {
    long currentTime = System.currentTimeMillis();
    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {

      lastClickTime = currentTime;
      ccbOnClick(v);
    }
  }



  public abstract void ccbOnClick(View view);
}

