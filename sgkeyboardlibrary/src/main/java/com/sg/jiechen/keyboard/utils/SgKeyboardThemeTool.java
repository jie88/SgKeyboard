package com.sg.jiechen.keyboard.utils;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.Iterator;

/**
 * author : ${CHENJIE}
 * created at  2017/8/2 15:19
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgKeyboardThemeTool {

  private static final ArrayMap<String,String> mKeyboardDrawables = new ArrayMap<String,String>();


  private static SgKeyboardThemeTool mInstance = null;
  private static SgKeyboardTheme mKeyboardTheme;

  public static  synchronized SgKeyboardThemeTool getInstance() {
    if(null == mInstance) {
      mInstance = new SgKeyboardThemeTool();
      mKeyboardTheme=SgKeyboardTheme.DARK;
      // mThemePreferences = new MbsSharedPreferences(CcbApplication.getInstance(),KEYBOARD_PRES,Context.MODE_PRIVATE);
    }
    return mInstance;
  }

  public String getThemeValue(Context context, String key ){

    String value = mKeyboardDrawables.get(key);
    if(TextUtils.isEmpty(value)){
      addIconByTheme(context);
    }
    return  mKeyboardDrawables.get(key);
  }

  private void addIconByTheme(Context context){

    InputStream keyboardStream = SgKeyboardUtils.asset_getFile(context,String.format("%s.json", CCB_KEYBOARD_THEME + "_" + mKeyboardTheme.getThemeName()));
    try {
      String keyboardString = SgKeyboardUtils.stream_2String(keyboardStream,null);
      JSONObject keyboardJson =  new JSONObject(keyboardString);
      Iterator<String> allKeys = keyboardJson.keys();
      while (allKeys.hasNext()) {
        String key = allKeys.next();
        mKeyboardDrawables.put(key, keyboardJson.optString(key));
      }
    } catch (Exception e) {
      // MbsLogManager.logE(e.toString());
    }

  }

  public static String defType = "drawable";
  public static String defTypeMipmap = "mipmap";


  public int getDrawableId(Context context,String key){
    String imageName = mKeyboardDrawables.get(key);
    if (TextUtils.isEmpty(imageName)) {
      addIconByTheme(context);
    }

    int resId = context.getResources().getIdentifier(imageName, defType, context.getPackageName());
    if (0 == resId) {
      resId =  context.getResources().getIdentifier(imageName, defTypeMipmap, context.getPackageName());
    }
    return  resId;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public Drawable getKeyboardDrawable(Context context, String key) {

    int resId = getDrawableId(context,key);

    Drawable keyDrawable = context.getResources().getDrawable(resId);
    return keyDrawable;
  }




  public static final  String KEYBOARD_PRES = "CCB_KEYBOARD_THEME";

  /**
   * 键盘主题名字 命名此字段开头+枚举{@link SgKeyboardTheme}+.json结尾
   */
  public static final String CCB_KEYBOARD_THEME="ccb_keyboard_theme";
  /**
   * 键盘背景颜色
   */
  public static final String KEYBOARD_BACKGROUND="keyboard_background";
  /**
   * 键盘title颜色 也就是中国建设银行安全键盘字体颜色
   */
  public static final String KEYBOARD_TITLE_COLOR="keyboard_title_color";
  /**
   * 键盘分隔线颜色 title和键盘之间的分隔线
   */
  public static final String KEYBOARD_DIVIDER_COLOR="keyboard_divider_color";
  /**
   * 键盘切换键颜色 包括{符、123、abc、完成}
   */
  public static final String KEYBOARD_SWITCH_COLOR="keyboard_switch_color";
  /**
   * 键盘默认图层大写切换图标
   */
  public static final String KEYBOARD_SELECTOR_DEFAULT_CAP="keyboard_selector_default_cap";
  /**
   * 键盘默认图层小写切换图标
   */
  public static final String KEYBOARD_SELECTOR_DEFAULT_LOW="keyboard_selector_default_low";
  /**
   * 键盘默认图层删除图标
   */
  public static final String KEYBOARD_SELECTOR_DEFAULT_DELETE="keyboard_selector_default_delete";
  /**
   * 键盘默认图层空格图标
   */
  public static final String KEYBOARD_SELECTOR_DEFAULT_SPACE="keyboard_selector_default_space";
  /**
   * 键盘数字图层删除图标
   */
  public static final String KEYBOARD_SELECTOR_DIGIT_DELETE="keyboard_selector_digit_delete";
  /**
   * 键盘数字图层空格图标
   */
  public static final String KEYBOARD_SELECTOR_DIGIT_SPACE="keyboard_selector_digit_space";
  /**
   * 键盘横向key图标
   */
  public static final String KEYBOARD_SELECTOR_KEY_LANDSCAPE="keyboard_selector_key_landscape";
  /**
   * 键盘纵向key图标
   */
  public static final String KEYBOARD_SELECTOR_KEY_PORTRAIT="keyboard_selector_key_portrait";
  /**
   * 键盘字符图层删除图标
   */
  public static final String KEYBOARD_SELECTOR_SYMBOL_DELETE="keyboard_selector_symbol_delete";
  /**
   * 键盘字符图层空格图标
   */
  public static final String KEYBOARD_SELECTOR_SYMBOL_SPACE="keyboard_selector_symbol_space";
  /**
   * 键盘文字颜色
   */
  public static final String KEYBOARD_KEY_COLOR="keyboard_key_color";

  public enum SgKeyboardTheme {
    DARK("dark"), WHITE("white");

    private String themeName;

    SgKeyboardTheme(String themeName) {
      this.themeName = themeName;
    }

    public String getThemeName() {
      return themeName;
    }
  }

}
