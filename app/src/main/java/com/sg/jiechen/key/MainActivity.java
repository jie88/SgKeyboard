package com.sg.jiechen.key;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


import com.sg.jiechen.keyboard.SgKeyboardManager;

public class MainActivity extends AppCompatActivity {

  EditText edit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    edit=(EditText)findViewById(R.id.edit);

    SgKeyboardManager.registerEditText(edit,true);

  }
}
