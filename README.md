# EmojiLibrary
Hello World!

FiftyoneMoon provide you EmojiLibrary which have all latest ios emoji and inspired by [SuperNova-Emoji](https://github.com/hani-momanii/SuperNova-Emoji).
FiftyoneMoon says you can use this libaray in your project with full authority.

Here some information about EmojiLibrary,

[![minSdkVersion: 16]]
[![maxSdkVersion: 30 (Latest)]]
[![](https://jitpack.io/v/fiftyonemoon/EmojiLibrary.svg)](https://jitpack.io/#fiftyonemoon/EmojiLibrary)

# Implementation
```groovy
implementation 'com.github.fiftyonemoon:EmojiLibrary:Tag'
```

# SampleActivity
- [`SampleActivity`](app/src/main/java/com/fiftyonemoon/SampleActivity.java) This is sample activity of library. 

<img src="./Images/ios_emoji.jpeg" alt="Normal Keyboard" width="270">


## Java Usage

### Initialize Emoji Action

```groovy
//DEFAULT EMOJI BACKGROUND
EmojiActions emojiActions = new EmojiActions(Context c ,View view, EmojiEditText emojiEditText, ImageButton emojiButton);
```

### Change background color

```groovy
//CHANGE EMOJI BACKGROUND
EmojiActions emojiActions = new EmojiActions(Context c,View view,EmojiEditText emojiEditText,ImageButton emojiButton,String iconPressedColor,String tabsColor,String backgroundColor);

//Example
EmojiActions emojiActions = new EmojiActions(this,rootView,emojiEditText,emojiButton,"#495C66","#FF4081","#FFFFFF");
```

### On Keyboard Listener
Set your Emoji keyboard on listener

```groovy
implements EmojiActions.KeyboardListener in your Activity
```

```groovy
//SET KEYBOARD LISTENER
emojiActions.setKeyboardListener(this);
```
### On EditText Click Open Soft Keyboard
Simply transfer from Emoji Popup to Soft Keyboard 

```groovy
//SET ON CLICK EDITTEXT OPEN SOFT KEYBOARD
emojiActions.setOnClickEditTextOpenSoftKeyboard(emojiEditText);
```

### Change Emoji Drawable
Change Emoji and Keyboard button icon with drawable icon

```groovy
//YOU CAN CHANGE EMOJI ICON WITH DRAWABLE ICON  --->>> 1. FOR KEYBOARD 2. FOR EMOJI ICON
emojiActions.setDrawableIcon(R.drawable.ic_baseline_keyboard_24, R.drawable.ic_baseline_emoji_emotions_24);
```

