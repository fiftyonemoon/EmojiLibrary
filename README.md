# EmojiLibrary
Hello World!

FiftyoneMoon provide you EmojiLibrary which have all latest ios emoji and inspired by [SuperNova-Emoji](https://github.com/hani-momanii/SuperNova-Emoji).
FiftyoneMoon says you can use this libaray in your project with full authority.

Here some information about EmojiLibrary,

minSdkVersion: 16
maxSdkVersion: 30 (Latest)

# Sample
<img src="./Images/ios_emoji.jpeg" alt="Normal Keyboard" width="270">

- [`SampleActivity`](app/src/main/java/com/fiftyonemoon/SampleActivity.java)

## Java Usage

### Initialize Emoji Action

```groovy
EmojiActions emojiActions = new EmojiActions(Context c ,View view, EmojiEditText emojiEditText, EmojiButton emojiButton);

//SET KEYBOARD LISTENER
emojiActions.setKeyboardListener(this);

//SET ON CLICK EDITTEXT OPEN SOFT KEYBOARD & HIDE EMOJI ---->>>
emojiActions.setOnClickEditTextOpenSoftKeyboard(emojiEditText);

//YOU CAN CHANGE EMOJI ICON WITH DRAWABLE ICON  --->>> 1. FOR KEYBOARD 2. FOR EMOJI ICON
emojiActions.setDrawableIcon(R.drawable.ic_baseline_keyboard_24, R.drawable.ic_baseline_emoji_emotions_24);

```



