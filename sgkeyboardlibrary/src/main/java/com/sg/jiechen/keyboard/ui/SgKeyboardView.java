package com.sg.jiechen.keyboard.ui;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sg.jiechen.keyboard.SgKeyboardManager;
import com.sg.jiechen.keyboard.utils.SgKeyResId;
import com.sg.jiechen.keyboard.utils.SgKeyboardThemeTool;
import com.sg.keyboard.R;

import java.util.Random;

/**
 * author : ${CHENJIE}
 * created at  2017/8/2 14:42
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgKeyboardView {

  /**
   * 键盘视图container
   */
  private ViewGroup keyboardView = null;

  /**
   * 当前图层 默认为 {@link SgKeyboardLayer#LOW}
   */
  private SgKeyboardLayer mLayer;

  private Context mContext;

  private boolean needRandom=true;

  /**
   * 构造
   *
   * @param mLayer
   *            需要显示的图层
   * @param mContext
   *            上下文
   */
  public SgKeyboardView(SgKeyboardLayer mLayer, Context mContext) {
    this(mLayer, mContext, false);
  }
  /**
   * 构造
   *
   * @param mLayer
   *            需要显示的图层
   * @param mContext
   *            上下文
   */
  public SgKeyboardView(SgKeyboardLayer mLayer, Context mContext, boolean needRandom) {
    this.mLayer = mLayer;
    this.mContext = mContext;
    this.needRandom = needRandom;
  }


  /**
   * 获取键盘视图
   *
   * @return
   */
 public View getKeyboardView() {
    if (null == keyboardView) {
      initLayer();
    }

    return keyboardView;
  }


  /**
   * 初始化布局
   */
  private void initLayer() {
    if (null == keyboardView) {
      keyboardView = (ViewGroup) View.inflate(mContext, R.layout.sg_keyboard_layer, null);
    }
    // 使用viewstub减少内存开销
    ViewStub keyboardStub = new ViewStub(mContext);
    if (null != keyboardView.getChildAt(2)) {
      keyboardView.removeViewAt(2);
    }
    // 加入根布局
    keyboardView.addView(keyboardStub, 2);

    String layerString = null;
    String digitString = SgKeyResId.CHAR_DIG;
    int[] keyIds = null;
    int[] keyDigitIds = null;
    int deleteId = -1;
    int spaceId = -1;
    String oneSwitchText = null;
    String twoSwitchText = null;
    String deleteKey = "";
    String spaceKey = "";
    int keyBackgroundResid = R.drawable.keyboard_key_selector_portrait;
    // 判断当前图层 获取对应视图布局
    switch (getKeyboardLayer()) {
      case LOW:// 小写
        keyboardStub.setLayoutResource(R.layout.sg_keyboard_layer_default);
        layerString = SgKeyResId.CHAR_LOW;
        keyIds = SgKeyResId.resIdDefaultChar;
        keyDigitIds = SgKeyResId.resIdDefaultDigits;
        deleteId = R.id.default_row_four_delete;
        spaceId = R.id.default_row_four_space;
        oneSwitchText = "符";
        twoSwitchText = "123";
        deleteKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_DEFAULT_DELETE;
        spaceKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_DEFAULT_SPACE;
        break;
      case CAP:// 大写
        keyboardStub.setLayoutResource(R.layout.sg_keyboard_layer_default);
        layerString = SgKeyResId.CHAR_CAP;
        keyIds = SgKeyResId.resIdDefaultChar;
        keyDigitIds = SgKeyResId.resIdDefaultDigits;
        deleteId = R.id.default_row_four_delete;
        spaceId = R.id.default_row_four_space;
        oneSwitchText = "符";
        twoSwitchText = "123";
        deleteKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_DEFAULT_DELETE;
        spaceKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_DEFAULT_SPACE;
        break;
      case DIG:// 数字
        keyboardStub.setLayoutResource(R.layout.sg_keyboard_layer_digit);
        layerString = SgKeyResId.CHAR_DIG;
        keyIds = SgKeyResId.resIdDigital;
        deleteId = R.id.digit_row_four_delete;
        keyBackgroundResid = R.drawable.keyboard_key_selector_landscape;
        // spaceId = R.id.digit_row_four_space;
        oneSwitchText = "符";
        twoSwitchText = "Abc";
        deleteKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_DIGIT_DELETE;
        spaceKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_DIGIT_SPACE;
        break;
      case SYM:// 字符
        keyboardStub.setLayoutResource(R.layout.sg_keyboard_layer_symbol);
        layerString = SgKeyResId.CHAR_SYM;
        keyIds = SgKeyResId.resIdSymbolSymbols;
        // 新版去除数字 2016-08-19
        // keyDigitIds = SgKeyResId.resIdSymbolDigits;
        deleteId = R.id.symbol_row_five_delete;
        spaceId = R.id.symbol_row_five_space;
        oneSwitchText = "Abc";
        twoSwitchText = "123";
        deleteKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_SYMBOL_DELETE;
        spaceKey = SgKeyboardThemeTool.KEYBOARD_SELECTOR_SYMBOL_SPACE;
        break;
    }
    // viewstub加入布局
    keyboardStub.inflate();


    // 事件监听 切换、完成按键
    TextView ct_switch_one = (TextView) keyboardView.findViewById(R.id.ct_switch_one);
    ct_switch_one.setTextColor(Color.parseColor(SgKeyboardThemeTool.getInstance().getThemeValue(mContext, SgKeyboardThemeTool.KEYBOARD_SWITCH_COLOR)));
    TextView ct_switch_two = (TextView) keyboardView.findViewById(R.id.ct_switch_two);
    ct_switch_two.setTextColor(Color.parseColor(SgKeyboardThemeTool.getInstance().getThemeValue(mContext, SgKeyboardThemeTool.KEYBOARD_SWITCH_COLOR)));
    TextView ct_submit = (TextView) keyboardView.findViewById(R.id.ct_submit);
    ct_submit.setTextColor(Color.parseColor(SgKeyboardThemeTool.getInstance().getThemeValue(mContext, SgKeyboardThemeTool.KEYBOARD_SWITCH_COLOR)));
    ct_switch_one.setText(oneSwitchText);
    ct_switch_two.setText(twoSwitchText);
    ct_submit.setOnClickListener(switchLayerClickListener);
    ct_switch_one.setOnClickListener(switchLayerClickListener);
    ct_switch_two.setOnClickListener(switchLayerClickListener);


    //title
    TextView ccb_keyboard_title = (TextView) keyboardView.findViewById(R.id.ccb_keyboard_title);
    ccb_keyboard_title.setTextColor(Color.parseColor(SgKeyboardThemeTool.getInstance().getThemeValue(mContext, SgKeyboardThemeTool.KEYBOARD_TITLE_COLOR)));

    //分割线
    View ccb_keyboard_divider = keyboardView.findViewById(R.id.ccb_keyboard_divider);
    ccb_keyboard_divider.setBackgroundColor(Color.parseColor(SgKeyboardThemeTool.getInstance().getThemeValue(mContext, SgKeyboardThemeTool.KEYBOARD_DIVIDER_COLOR)));

    //背景颜色
    View cll_keyboard_parent = keyboardView.findViewById(R.id.cll_keyboard_parent);
    cll_keyboard_parent.setBackgroundColor(Color.parseColor(SgKeyboardThemeTool.getInstance().getThemeValue(mContext, SgKeyboardThemeTool.KEYBOARD_BACKGROUND)));


    if (needRandom) {
      layerString = getRandomString(layerString);
      digitString = getRandomString(digitString);
    }


    //键盘文字颜色
    int keyColor = Color.parseColor(SgKeyboardThemeTool.getInstance().getThemeValue(mContext, SgKeyboardThemeTool.KEYBOARD_KEY_COLOR));
    // 设置文字以及监听
    if (null != keyIds) {
      for (int i = 0, count = keyIds.length; i < count; i++) {
        Drawable keyBackground = (keyBackgroundResid == R.drawable.keyboard_key_selector_landscape) ? SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext, SgKeyboardThemeTool.KEYBOARD_SELECTOR_KEY_LANDSCAPE) : SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext, SgKeyboardThemeTool.KEYBOARD_SELECTOR_KEY_PORTRAIT);
        KeyButton keyButton = (KeyButton) keyboardView.findViewById(keyIds[i]);
        keyButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.ccb_keyboard_txt_size_normal));
        keyButton.setTextColor(keyColor);
        keyButton.setText(layerString.substring(i, i + 1));
        keyButton.setBackgroundDrawable(keyBackground);
        keyButton.setOnClickListener(mKeyOnClickListener,0);
        keyButton.setPadding(0,0,0,0);
        keyButton.setOnLongClickListener(mKeyOnLongClickListener);
        keyButton.setOnTouchListener(deleteTouchListener);
      }
    }

    // 设置数字文字以及监听
    if (null != keyDigitIds) {
      for (int i = 0, count = keyDigitIds.length; i < count; i++) {
        Drawable keyBackground = (keyBackgroundResid == R.drawable.keyboard_key_selector_landscape) ? SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext, SgKeyboardThemeTool.KEYBOARD_SELECTOR_KEY_LANDSCAPE) : SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext, SgKeyboardThemeTool.KEYBOARD_SELECTOR_KEY_PORTRAIT);
        KeyButton keyDigitButton = (KeyButton) keyboardView.findViewById(keyDigitIds[i]);
        keyDigitButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.ccb_keyboard_txt_size_normal));
        keyDigitButton.setTextColor(keyColor);
        keyDigitButton.setText(digitString.substring(i, i + 1));
        keyDigitButton.setBackgroundDrawable(keyBackground);
        keyDigitButton.setOnClickListener(mKeyOnClickListener,0);
        keyDigitButton.setPadding(0,0,0,0);
        keyDigitButton.setOnLongClickListener(mKeyOnLongClickListener);
        keyDigitButton.setOnTouchListener(deleteTouchListener);
      }
    }
    // 删除键监听
    KeyButton deleteButton = (KeyButton) keyboardView.findViewById(deleteId);
    if (null != deleteButton) {
      deleteButton.setBackgroundDrawable(SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext,deleteKey));
      deleteButton.setOnLongClickListener(deleteLongClickListener);
      deleteButton.setOnClickListener(deleteOnClickListener,0);
      deleteButton.setOnTouchListener(deleteTouchListener);
    }


    // 空格事件监听
    KeyButton spaceButton = (KeyButton) keyboardView.findViewById(spaceId);
    if (null != spaceButton) {
      spaceButton.setBackgroundDrawable(SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext,spaceKey));
      spaceButton.setText(" ");
      spaceButton.setOnClickListener(mKeyOnClickListener,0);
      spaceButton.setOnLongClickListener(mKeyOnLongClickListener);
      spaceButton.setOnTouchListener(deleteTouchListener);
    }

// 大小写切换
    Button switchCapOrLowButton = (Button) keyboardView.findViewById(R.id.default_row_three_switch);
    if (null != switchCapOrLowButton) {
      switchCapOrLowButton.setBackgroundDrawable(mLayer == SgKeyboardLayer.LOW ?SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext, SgKeyboardThemeTool.KEYBOARD_SELECTOR_DEFAULT_LOW): SgKeyboardThemeTool.getInstance().getKeyboardDrawable(mContext, SgKeyboardThemeTool.KEYBOARD_SELECTOR_DEFAULT_CAP));
      switchCapOrLowButton.setOnClickListener(switchLayerClickListener);
    }
  }

  /**
   * 单个删除监听
   */
  private View.OnTouchListener deleteTouchListener = new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
          clickHandler.removeCallbacksAndMessages(null);
          deleteTime = 0;
          break;
      }
      return false;
    }
  };


  /**
   * 删除放开监听
   */
  private View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      clickHandler.sendEmptyMessage(DELETE);
    }
  };
  /**
   * 长按删除监听
   */
  private View.OnLongClickListener deleteLongClickListener = new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
      clickHandler.sendMessage(getDeleteMessage(v,DELETE_AUTO));
      return false;
    }
  };

  /**
   * 按键输入监听
   */
  private View.OnClickListener mKeyOnClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      clickHandler.sendMessage(getInsertMessage(v,INSERT));
    }
  };

  private View.OnLongClickListener mKeyOnLongClickListener=new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
      clickHandler.sendMessage(getInsertMessage(v,INSERT_AUTO));
      return false;
    }
  };
  /**
   * 获取删除消息
   * @param v 按钮
   * @param what 标识
   * @return
   */
  private Message getDeleteMessage(View v ,int what){
    Message msg = clickHandler.obtainMessage();
    msg.arg1 = -1;
    msg.what = what;
    return msg;
  }

  /**
   * 获取插入消息
   * @param v 按钮
   * @param what 标识
   * @return
   */
  private Message getInsertMessage(View v,int what){
    Message message=clickHandler.obtainMessage();
    message.obj=v;
    message.what=what;
    return message;
  }

  /**
   * 图层切换监听
   */
  private View.OnClickListener switchLayerClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {

      if (v.getId() == R.id.ct_submit) {// 完成
        SgKeyboardManager.hideKeyboardOnFinishClick();
        return;
      }
      if (v.getId() == R.id.default_row_three_switch) {// 大小写切换

        SgKeyboardLayer switchLayer = null;
        if (mLayer == SgKeyboardLayer.LOW) {
          switchLayer = SgKeyboardLayer.CAP;
        }
        if (mLayer == SgKeyboardLayer.CAP) {
          switchLayer = SgKeyboardLayer.LOW;
        }
        if (null == switchLayer)
          return;
        mLayer = switchLayer;
        initLayer();
        return;
      }
      // 获取是否能切换图层标识
      EditText currEt = SgKeyboardManager.getCurrEditText();
      if (null != currEt.getTag(R.id.tag_layer_switch_flag)
          && !(Boolean) (currEt.getTag(R.id.tag_layer_switch_flag)))
        return;
      // 其他按键
      TextView switchButton = (TextView) v;
      for (SgKeyboardLayer layer : SgKeyboardLayer.values()) {
        if (switchButton.getText().equals(layer.toString())) {
          mLayer = layer;
          break;
        }
      }
      initLayer();
    }
  };


  /**
   * 删除对应标识
   */
  private final int DELETE = 200;
  private final int DELETE_AUTO = 400;
  /**
   * 插入对应标识
   */
  private final int INSERT = 300;
  private final int INSERT_AUTO = 600;

  private final int CLEAR_ALL = -1;

  /**
   * 删除速度
   */
  private final int DELETE_SPEED = 50;
  /**
   * 插入速度
   */
  private final int INSERT_SPEED = 200;

  private final int CLEAR_ALL_TIME_OFFSET = 3 * 1000;// 5s
  private int deleteTime = 0;
  /**
   * 删除处理
   */

  private Handler clickHandler = new Handler() {

    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        // 删除
        case DELETE:
          deleteText();
          break;
        // 长按删除
        case DELETE_AUTO:
          deleteTime += DELETE_SPEED;
          //MbsLogManager.logI("=============time============" + deleteTime);
          deleteText();
          if (msg.arg1 == -1) {
            msg.arg1 = DELETE_SPEED;
          }
          int speed = msg.arg1 > 0 ? --msg.arg1 : 0;
          //MbsLogManager.logI("speed===============>>" + speed);
          Message message = obtainMessage();
          if (deleteTime >= CLEAR_ALL_TIME_OFFSET) {
            message.what = CLEAR_ALL;
          } else {
            message.what = DELETE_AUTO;
          }
          message.arg1 = speed;
          sendMessageDelayed(message, speed);
          break;
        // 插入
        case INSERT:
          insertText((View) msg.obj);
          break;
        // 长按插入
        case INSERT_AUTO:
          View v = (View) msg.obj;
          insertText(v);
          sendMessageDelayed(getInsertMessage(v, INSERT_AUTO), INSERT_SPEED);
          break;
        // 清除所有
        case CLEAR_ALL:
          clearAllText();
          break;
      }
    }
  };

  /**
   * 从输入框删除文字
   */
  private void deleteText() {
    EditText currEt = SgKeyboardManager.getCurrEditText();
    int currPos = currEt.getSelectionEnd();
    if (-1 == currPos)
      return; // 错误
    if (0 == currPos)
      return; // 已经删完
    Editable text = currEt.getText();
    int selectStart = currEt.getSelectionStart();
    int selectEnd = currEt.getSelectionEnd();
    if (-1 != selectStart && -1 != selectEnd && selectStart != selectEnd) {
      text.delete(selectStart, selectEnd);
      deleteHiddenText(currEt, selectStart, selectEnd);
    } else {
      int start = currPos - 1;
      int end = currPos;
      text.delete(start, end);
      deleteHiddenText(currEt, start, end);
    }
    currEt.setSelection(currEt.getSelectionEnd());
  }

  /**
   * 删除隐藏的文字
   *
   * @param currEditText
   *            输入框
   * @param selectStart
   *            开始位置
   * @param selectEnd
   *            结束位置
   */
  private void deleteHiddenText(EditText currEditText, int selectStart, int selectEnd) {

    if (null == currEditText)
      return;
    Editable hiddenEditable = (Editable) currEditText.getTag(R.id.tag_hidden_text_flag);
    if (null == hiddenEditable || 0 == hiddenEditable.toString().length())
      return;
    hiddenEditable.delete(selectStart, selectEnd);
  }

  /**
   * 清除所有隐藏的文字
   *
   * @param currEditText
   *            输入框
   */
  private void clearAllHiddenText(EditText currEditText) {

    if (null == currEditText)
      return;
    currEditText.setTag(R.id.tag_hidden_text_flag, createEditable());
  }

  /**
   * 创建文字输入对象
   *
   * @return {@link Editable}
   */
  private Editable createEditable() {

    return Editable.Factory.getInstance().newEditable("");
  }

  /**
   * 插入隐藏的文字信息
   *
   * @param currEditText
   *            输入框
   * @param position
   *            位置
   * @param text
   *            文字
   */
  private void insertHiddenText(EditText currEditText, int position, CharSequence text) {
    if (null == currEditText)
      return;
    Editable hiddenEditable = (Editable) currEditText.getTag(R.id.tag_hidden_text_flag);
    if (null == hiddenEditable) {
      hiddenEditable = createEditable();
      currEditText.setTag(R.id.tag_hidden_text_flag, hiddenEditable);
    }
    if (currEditText.getText().length() == hiddenEditable.length())
      return;
    hiddenEditable.insert(position, text);
    Selection.setSelection(hiddenEditable, Selection.getSelectionEnd(hiddenEditable));
  }

  /**
   * 清除输入框
   */
  private void clearAllText() {
    deleteTime = 0;
    EditText currEt = SgKeyboardManager.getCurrEditText();
    currEt.setText(null);
    clearAllHiddenText(currEt);
  }


  /**
   * 插入
   * @param v 按钮
   */
  private void insertText(View v) {
    if (null == SgKeyboardManager.getCurrEditText())
      return;

    EditText currEt = SgKeyboardManager.getCurrEditText();
    int currPos = currEt.getSelectionEnd();
    if (-1 == currPos)
      return;

    // 在光标之后加入字符
    Editable text = currEt.getText();
    CharSequence cs = ((Button) v).getText();
//    boolean isPassword = UiTool.isPasswordEditText(currEt) || CcbKeyboardUtils.isCustomPassword(currEt);
//
//    if (!isPassword) {
    text.insert(currPos, cs);
//    } else {
//      text.insert(currPos, CcbKeyboardUtils.getSafePasswordChar(currEt));
//      insertHiddenText(currEt, currPos, cs);
//    }
    currEt.setSelection(currEt.getSelectionEnd());
  }


  /**
   * 获取对应视图类型
   *
   * @return {@link SgKeyboardLayer}
   */
  private SgKeyboardLayer getKeyboardLayer() {
    if (null == mLayer)
      return SgKeyboardLayer.LOW;
    for (SgKeyboardLayer layer : SgKeyboardLayer.values()) {
      if (mLayer.toString().equals(layer.toString())) {

        return layer;
      }
    }
    return SgKeyboardLayer.LOW;
  }
  /**
   * 生成随机字符串
   *
   * @param s
   * @return
   */
  private String getRandomString(String s) {

    Random random = new Random();
    StringBuffer randomSb = new StringBuffer(s);
    for (int i = 0, length = s.length(); i < length; i++) {
      int order = random.nextInt(length);
      String charAtI = randomSb.substring(i, i + 1);
      String charAtOrder = randomSb.substring(order, order + 1);
      randomSb.replace(i, i + 1, charAtOrder);
      randomSb.replace(order, order + 1, charAtI);
    }

    return randomSb.toString();
  }

}
