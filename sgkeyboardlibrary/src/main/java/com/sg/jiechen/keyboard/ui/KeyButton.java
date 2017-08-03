package com.sg.jiechen.keyboard.ui;


import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.sg.jiechen.keyboard.listener.KeyOnClickListener;

/**
 * author : ${CHENJIE}
 * created at  2017/8/1 20:32
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class KeyButton extends AppCompatButton {
  public KeyButton(Context context) {
    this(context, null);
  }

  public KeyButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public KeyButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);


  }


  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }


  private OnClickListener mOnClickListener;

  @Override
  public void setOnClickListener(OnClickListener l) {

    setOnClickListener(l, -1);
  }

  public void setOnClickListener(OnClickListener l, long controlClickTime) {
    if (l instanceof KeyOnClickListener) {
      super.setOnClickListener(l);
      return;
    }
    // 添加默认公共监听
    this.mOnClickListener = l;
    super.setOnClickListener(getDefaultCcbOnClickListener(controlClickTime));

  }

  private KeyOnClickListener getDefaultCcbOnClickListener(long controlClickTime) {


    return -1 == controlClickTime ? new KeyOnClickListener() {
      @Override
      public void ccbOnClick(View view) {

        if (null == mOnClickListener)
          return;
        mOnClickListener.onClick(view);
      }
    } : new KeyOnClickListener(controlClickTime) {
      @Override
      public void ccbOnClick(View view) {

        if (null == mOnClickListener)
          return;
        mOnClickListener.onClick(view);
      }
    };
  }


}
