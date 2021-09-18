[![Android API](https://img.shields.io/badge/api-21%2B-brightgreen.svg?style=for-the-badge)](https://android-arsenal.com/api?level=21)
[![kotlin](https://img.shields.io/github/languages/top/adrielcafe/voyager.svg?style=for-the-badge&color=blueviolet)](https://kotlinlang.org/)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg?style=for-the-badge)](https://ktlint.github.io/)

Here is my small addon for [Voyager](https://github.com/adrielcafe/voyager) library to use navigator
to open and close dialogs.

```kotlin
DialogNavigator {
    Navigator(BackScreen())
}

class BackScreen : Screen {

    @Composable
    override fun Content() {
        val dialogNavigator = LocalDialogNavigator.current

        Button(
            onClick = {
                dialogNavigator.show(DialogScreen())
            }
        ) {
            Text(text = "Show dialog")
        }
    }
}
```

### How to use

First add jitpack repository

```gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

Then add this to your dependencies

```gradle
dependencies {
    implementation 'com.github.Dussim:voyager-addon-dialog-navigator:1.0.0-alpha01'
}
```