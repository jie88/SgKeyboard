package com.sg.jiechen.keyboard.utils;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author : ${CHENJIE}
 * created at  2017/8/2 11:30
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgKeyboardUtils {

  /**
   * 根据系统版本处理自定义键盘光标问题
   */
 public static void hideSystemBoard(EditText focusEt) {
    Context ctx = focusEt.getContext();
    if (ctx instanceof Activity) {
      Activity act = (Activity) ctx;
      if (act.isChild()) {
        while (act.isChild()) {
          act = act.getParent();
        }
        ctx = act;
      }
    }

    if (android.os.Build.VERSION.SDK_INT <= 10) {
      InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(focusEt.getWindowToken(), 0);
    } else {
      InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(focusEt.getWindowToken(), 0);
      try {
        Class<EditText> cls = EditText.class;
        Method setSoftInputShownOnFocus;
        setSoftInputShownOnFocus = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
        setSoftInputShownOnFocus.setAccessible(true);
        setSoftInputShownOnFocus.invoke(focusEt, false);
      } catch (Exception e) {
        // e.printStackTrace();
      }
      try {
        Class<EditText> cls = EditText.class;
        Method setShowSoftInputOnFocus;
        setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
        setShowSoftInputOnFocus.setAccessible(true);
        setShowSoftInputOnFocus.invoke(focusEt, false);
      } catch (Exception e) {
        // e.printStackTrace();
      }
    }
  }


  /**
   * 从Popuwindow中获取cintainer
   *
   * @param popupWindow
   * @return
   */
  public static ViewGroup getPopuWindowViewGroup(PopupWindow popupWindow) {

    ViewGroup viewGroup = null;
    try {
      Field popuWindowField = popupWindow.getClass().getDeclaredField("mPopupView");
      popuWindowField.setAccessible(true);
      viewGroup = (ViewGroup) popuWindowField.get(popupWindow);
      viewGroup.removeAllViews();
    } catch (Exception e) {
      // MbsLogManager.logW(e.toString());
    }

    return viewGroup;
  }
  /**
   * 调整布局 避免输入框被遮挡
   *
   * @param v
   *            当前输入框
   * @param h
   *            需要调整的高度
   */
  public static void adjustLayout(View v, int h) {
    Activity act = getActFromContext(v.getContext());
    View contentView = act.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
    if (h > 0)
      return;
    ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "TranslationY", h).setDuration(250);
    animator.setInterpolator(new DecelerateInterpolator());
    animator.start();
  }

  /**
   * 恢复布局
   *
   * @param v
   */
  public static void restoreLayout(View v) {
    Activity act = getActFromContext(v.getContext());
    View contentView = act.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
    ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "TranslationY", 0).setDuration(250);
    animator.setInterpolator(new DecelerateInterpolator());
    animator.start();

  }

  /**
   * 获取调节高度大小
   *
   * @param
   * @return
   */
  public static int getAdjustValue(View keyboardView, EditText et) {
    Activity act = getActFromContext(et.getContext());
    ViewGroup contentView = (ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content);

    int availHeight = contentView.getHeight();// 整个Activity 视图的高度

    int etHeight = et.getHeight();// 输入框高度
    int etTop = SgKeyboardUtils.calcViewPositionWithoutAdjust(et);// 输入框在整个视图中的高度

    int etBtmCoord = etTop + etHeight;// et的baseline坐标
    int etToBtm = availHeight - etBtmCoord;// et下的空间

    // 如果etToBtm > popupwindow的高度,则不需调整,否则调整
    int keyboardHeight = keyboardView.getHeight();
    if (0 == keyboardHeight) {
      // 初始化:测量高度
      int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
      int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
      keyboardView.measure(w, h);
      keyboardHeight = keyboardView.getMeasuredHeight();
    }
    return etToBtm - keyboardHeight;

  }


  /**
   * 获取一个视图的(left,top) 点, 在屏幕上的 位置坐标
   *
   * @param view
   * @return
   */
  public static int getViewTopAtScreen(View view) {
    int[] viewLocation = new int[2];
    view.getLocationOnScreen(viewLocation);
    return viewLocation[1];
  }

  /**
   * 计算无调节情况下, 视图的左上坐标@屏幕坐标系
   *
   * @param v
   * @return
   */
  public static int calcViewPositionWithoutAdjust(View v) {
    Activity act = getActFromContext(v.getContext());
    ViewGroup contentView = (ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content);
    View rootView = contentView.getChildAt(0);
    int currAdjustValue = getViewTopAtScreen(rootView);// 当前已经调整的位置
    int currViewPosi = getViewTopAtScreen(v);// 当前视图位置
    return currViewPosi - currAdjustValue;
  }


  /**
   * 从Context中获取Activity
   *
   * @param mContext
   * @return
   */
  public static Activity getActFromContext(Context mContext) {
    if (mContext == null)
      return null;
    else if (mContext instanceof Activity)
      return (Activity) mContext;
    else if (mContext instanceof ContextWrapper)
      return getActFromContext(((ContextWrapper) mContext).getBaseContext());
    return null;
  }


  /**
   * 获取asset目录下文件
   *
   * @param fileName
   *            文件全称,包含后缀名 例如a.png , x.9.png , yy.jpg
   * @return inputStream
   */
  public static InputStream asset_getFile(Context c, String fileName) {
    try {
      InputStream inStream = c.getApplicationContext().getAssets().open(fileName);
      return inStream;
    } catch (IOException e) {
      Log.d("asset_getFile", "asset:" + fileName + ",no exist");
    }
    return null;
  }

  /**
   * 将InputStream 转换为String
   *
   * @param is
   * @param encoding
   *            编码格式,可以为null,null表示适用utf-8
   */
  public static String stream_2String(InputStream is, String encoding) throws IOException {
    if (is == null)
      return null;

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    int i = -1;
    while ((i = is.read()) != -1) {
      baos.write(i);
    }
    baos.close();
    String result = null;
    if (encoding == null) {
      encoding = "utf-8";
    }
    result = baos.toString(encoding);
    return result;
  }



}
