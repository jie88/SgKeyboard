package com.sg.jiechen.keyboard;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.sg.jiechen.keyboard.listener.SgKeyboardListener;
import com.sg.jiechen.keyboard.ui.SgKeyboardLayer;
import com.sg.jiechen.keyboard.ui.SgKeyboardView;
import com.sg.jiechen.keyboard.utils.SgKeyboardUtils;
import com.sg.keyboard.R;

/**
 * author : ${CHENJIE}
 * created at  2017/8/2 10:58
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgKeyboardManager {

  private static SgKeyboardListener mKeyBoardListener;
  private static Context mContext;

  private static EditText mCurrEditText;
  /**
   * 获取当前edittext
   *
   * @return
   */
  public static EditText getCurrEditText() {

    return mCurrEditText;
  }

  private static PopupWindow mKeyboardContent = null;

  private static boolean needRandom;

  /**
   * 注册键盘
   *
   * @param et
   *            需要注册键盘的edittext
   */
  public static void registerEditText(EditText et) {

    setListeners(et);
    et.setTag(R.id.tag_edit_text_input_control, SgKeyboardLayer.LOW);


  }
  /**
   * 注册键盘
   *
   * @param et
   *            需要注册键盘的edittext
   */
  public static void registerEditText(EditText et,boolean random) {
   needRandom=random;
    setListeners(et);
    et.setTag(R.id.tag_edit_text_input_control, SgKeyboardLayer.LOW);
  }

  /**
   * 设置监听
   *
   * @param et
   */
  private static void setListeners(EditText et) {
    et.setOnTouchListener(LISTENER_TOUCH);
    et.setOnKeyListener(LISTENER_BACK_KEY);
    et.setOnFocusChangeListener(getFocusListener(et));

  }

  /**
   * 触摸监听
   */
  private static boolean isUserMoveEvent = false;
  private static float eventX = 0f;
  private static float eventY = 0f;

  private static final View.OnTouchListener LISTENER_TOUCH=new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
      SgKeyboardUtils.hideSystemBoard((EditText) v);
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          isUserMoveEvent = false;
          eventX = event.getX();
          eventY = event.getY();
          break;
        case MotionEvent.ACTION_MOVE:
          int defaultTouchSlop = ViewConfiguration.get(SgKeyboardUtils.getActFromContext(v.getContext())).getScaledTouchSlop();

          if (Math.abs(event.getX() - eventX )> defaultTouchSlop || Math.abs(event.getY() - eventY) > defaultTouchSlop) {
            isUserMoveEvent = true;
          }
          break;

        case MotionEvent.ACTION_UP:

          if (isUserMoveEvent)
            return false;

          if (null != mCurrEditText && mCurrEditText == v && null != mKeyboardContent && mKeyboardContent.isShowing())
            return false;

          if (null != mKeyboardContent && mKeyboardContent.isShowing()) {
            mKeyboardContent.dismiss();
          }
          final EditText focusEt = (EditText) v;
          mCurrEditText = focusEt;
          show(focusEt);
          break;

      }

      return false;
    }
  };


  /**
   * 弹出键盘
   *
   * @param focusEt
   *            当前edittext
   */
  private synchronized static void show(final EditText focusEt) {
    View keyboardView = new SgKeyboardView((SgKeyboardLayer) focusEt.getTag(R.id.tag_edit_text_input_control),
        focusEt.getContext(),needRandom).getKeyboardView();

    if (null == mKeyboardContent) {
      mKeyboardContent = new PopupWindow(keyboardView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
      //尝试修复键盘被虚拟按键挡住的问题 在华为部分机型上有出现
      mKeyboardContent.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
      mKeyboardContent.setAnimationStyle(R.style.ccb_keyboard_style);
    } else {
      ViewGroup popViewGroup = SgKeyboardUtils.getPopuWindowViewGroup(mKeyboardContent);
      if (null == popViewGroup) {
        hideKeyboard();
        mKeyboardContent.setContentView(keyboardView);
      } else {
        popViewGroup.removeAllViews();
        popViewGroup.addView(keyboardView);
      }
    }
    if (mKeyboardContent.isShowing()) {
      mKeyboardContent.dismiss();
    }
    Activity currentAct = SgKeyboardUtils.getActFromContext(focusEt.getContext());
    View parent = currentAct.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
    mKeyboardContent.showAtLocation(parent, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
    mKeyboardContent.setOnDismissListener(ON_DISMISS_LISTENER);
    Object objHeight=focusEt.getTag(R.id.tag_keyboard_height);
    SgKeyboardUtils.adjustLayout(focusEt, null!=objHeight? -(int) objHeight : SgKeyboardUtils.getAdjustValue(keyboardView, focusEt));
    if (null != mKeyBoardListener && isListenerActOnTop()) {
      mKeyBoardListener.onShow(mCurrEditText);
    }
  }


  /**
   * 焦点转移收起键盘
   */
  private static final View.OnFocusChangeListener LISTENER_FOCUS = new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
      if (!hasFocus) {
        hideKeyboard();
      }else{
        showKeyboard((EditText) v);
      }
    }
  };


  /**
   * 键盘关闭监听
   */
  private static final PopupWindow.OnDismissListener ON_DISMISS_LISTENER = new PopupWindow.OnDismissListener() {
    @Override
    public void onDismiss() {
      if (null == mCurrEditText)
        return;
      SgKeyboardUtils.restoreLayout(mCurrEditText);
    }
  };

  /**
   * 关闭键盘
   */
  public static void hideKeyboardOnFinishClick() {
    hideKeyboard();
    callBackHide();
    if (null != mKeyBoardListener && isListenerActOnTop()) {
      mKeyBoardListener.onFinishClickHidden(mCurrEditText);
    }
  }

  /**
   * 回退监听
   */

  private static final View.OnKeyListener LISTENER_BACK_KEY=new View.OnKeyListener() {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
      if (keyCode != KeyEvent.KEYCODE_BACK)
        return false;

      if (mKeyboardContent != null && mKeyboardContent.isShowing()) {
        mKeyboardContent.dismiss();
        callBackHide();
        return true;
      }
      return false;
    }
  };

  static void callBackHide() {
    if (null != mKeyBoardListener && isListenerActOnTop()) {
      mKeyBoardListener.onHide(mCurrEditText);
    }
  }


  /**
   * 检测当前顶层Act与监听器Act是不是同一个Act
   *
   * @return
   */
  private static boolean isListenerActOnTop() {
    if (!(mContext instanceof Activity))
      return false;
    if (null == mContext || ((Activity) mContext).isFinishing()) {
      unregisterKeyboardListener();
    }
    return true;
    //return mContext == ActivityManager.getInstance().getTopActivity();
  }



  /**
   * 关闭键盘
   */
  public static void hideKeyboard() {
    if (null == mKeyboardContent || !mKeyboardContent.isShowing())
      return;
    mKeyboardContent.dismiss();
  }

  private static View.OnFocusChangeListener getFocusListener(EditText et) {
    final View.OnFocusChangeListener originListener = et.getOnFocusChangeListener();
    if (originListener == null) {
      return LISTENER_FOCUS;
    }

    return new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        // MbsLogManager.logI("焦点事件处理");
        originListener.onFocusChange(v, hasFocus);// 调用原键盘的输入监听事件;
        if (!hasFocus) {
          hideKeyboard();
        }else{
          showKeyboard((EditText) v);
        }
      }
    };
  }


  /**
   * 注册键盘监听
   */
  public static void registerKeyboardListener(Context context, SgKeyboardListener l) {
    mContext = context;
    mKeyBoardListener = l;
  }

  /**
   * 取消键盘监听注册
   */
  public static void unregisterKeyboardListener() {
    mContext = null;
    mKeyBoardListener = null;
  }
  /**
   * 自动弹框键盘 需要先注册键盘{@link #registerEditText(EditText)}
   *
   * @param et
   *            需要弹出的弹出框
   */
  public synchronized static void showKeyboard(final EditText et) {

    //  UiTool.autoShowKeyboard(et);
  }

}
