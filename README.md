# SgKeyboard
自定义加密键盘

依赖方法
Step 1. Add the JitPack repository to your build file
Gradle

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.jie88:SgKeyboard:V1.0.1'
	}

调用方法：


edit=(EditText)findViewById(R.id.edit);

// 注册密码键盘
    SgKeyboardManager.registerEditText(edit);

  // 注册乱序密码键盘
    SgKeyboardManager.registerEditText(edit,true);
