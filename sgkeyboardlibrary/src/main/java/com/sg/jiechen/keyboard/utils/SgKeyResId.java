package com.sg.jiechen.keyboard.utils;


import com.sg.keyboard.R;

/**
 * author : ${CHENJIE}
 * created at  2017/8/2 15:09
 * e_mail : chenjie_goodboy@163.com
 * describle :
 */
public class SgKeyResId {
  /** 大写字符 默认顺序,这些顺序与布局资源顺序相对应 */
  public static final String CHAR_CAP = "QWERTYUIOP" + "ASDFGHJKL" + "ZXCVBNM";

  /** 小写字符 默认顺序 */
  public static final String CHAR_LOW = "qwertyuiop" + "asdfghjkl" + "zxcvbnm";

  /** 特殊字符 默认顺序 */
  public static final String CHAR_SYM = "&\";^,|$*:'"+ "?{[~#}.]\\!"+"(%-_+/)=<`" +">@";

  /** 数字 默认顺序 */
  public static final String CHAR_DIG = "1234567890.";

  // 默认图层10个数字id
  public static final int[] resIdDefaultDigits = new int[] {
      R.id.default_row_one_1, //
      R.id.default_row_one_2, //
      R.id.default_row_one_3, //
      R.id.default_row_one_4, //
      R.id.default_row_one_5, //
      R.id.default_row_one_6, //
      R.id.default_row_one_7, //
      R.id.default_row_one_8, //
      R.id.default_row_one_9, //
      R.id.default_row_one_10,//

  };

  // 默认图层26个字母id
  public static final int[] resIdDefaultChar = new int[] {
      // qwertyuiop
      R.id.default_row_two_1, //
      R.id.default_row_two_2, //
      R.id.default_row_two_3, //
      R.id.default_row_two_4, //
      R.id.default_row_two_5, //
      R.id.default_row_two_6, //
      R.id.default_row_two_7, //
      R.id.default_row_two_8, //
      R.id.default_row_two_9,
      R.id.default_row_two_10,

      // asdfghjkl
      R.id.default_row_three_2, //
      R.id.default_row_three_3, //
      R.id.default_row_three_4, //
      R.id.default_row_three_5, //
      R.id.default_row_three_6, //
      R.id.default_row_three_7, //
      R.id.default_row_three_8, //
      R.id.default_row_three_9, //
      R.id.default_row_three_10,

      // zxcvbnm
      R.id.default_row_four_2, //
      R.id.default_row_four_3, //
      R.id.default_row_four_4, //
      R.id.default_row_four_5, //
      R.id.default_row_four_6, //
      R.id.default_row_four_7, //
      R.id.default_row_four_8, //
  };

  //新版去除数字 2016-08-19
	/*// 字符图层10个数字id
	static final int[] resIdSymbolDigits = new int[] {
            R.id.symbol_row_one_1, //
			R.id.symbol_row_one_2, //
			R.id.symbol_row_one_3, //
			R.id.symbol_row_one_4, //
			R.id.symbol_row_one_5, //
			R.id.symbol_row_one_6, //
			R.id.symbol_row_one_7, //
			R.id.symbol_row_one_8, //
			R.id.symbol_row_one_9, //
			R.id.symbol_row_one_10,//

	};*/

  // 字符图层32个字符id
  public static final int[] resIdSymbolSymbols = new int[] {
      // &";^,|$*:'
      R.id.symbol_row_two_1, //
      R.id.symbol_row_two_2, //
      R.id.symbol_row_two_3, //
      R.id.symbol_row_two_4, //
      R.id.symbol_row_two_5, //
      R.id.symbol_row_two_6, //
      R.id.symbol_row_two_7, //
      R.id.symbol_row_two_8, //
      R.id.symbol_row_two_9,
      R.id.symbol_row_two_10,

      // ?{[~#}.]\!
      R.id.symbol_row_three_1, //
      R.id.symbol_row_three_2, //
      R.id.symbol_row_three_3, //
      R.id.symbol_row_three_4, //
      R.id.symbol_row_three_5, //
      R.id.symbol_row_three_6, //
      R.id.symbol_row_three_7, //
      R.id.symbol_row_three_8, //
      R.id.symbol_row_three_9, //
      R.id.symbol_row_three_10,

      // (%-_+/)=
      R.id.symbol_row_four_1, //
      R.id.symbol_row_four_2, //
      R.id.symbol_row_four_3, //
      R.id.symbol_row_four_4, //
      R.id.symbol_row_four_5, //
      R.id.symbol_row_four_6, //
      R.id.symbol_row_four_7, //
      R.id.symbol_row_four_8, //
      R.id.symbol_row_four_9,//
      R.id.symbol_row_four_10, //


      //>@
      R.id.symbol_row_five_1, //
      R.id.symbol_row_five_2 //
  };

  // 9个数字+点符号对应的id
  public static final int[] resIdDigital = new int[] {
      R.id.digit_row_one_1, //
      R.id.digit_row_one_2, //
      R.id.digit_row_one_3, //
      R.id.digit_row_two_1, //
      R.id.digit_row_two_2, //
      R.id.digit_row_two_3, //
      R.id.digit_row_three_1, //
      R.id.digit_row_three_2, //
      R.id.digit_row_three_3, //
      R.id.digit_row_four_2, //
      R.id.digit_row_four_space

  };

}
