package xyz.dussim.voyager.navigator.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.Stack
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator

public typealias DialogNavigatorContent = @Composable (dialogNavigator: DialogNavigator) -> Unit

public val LocalDialogNavigator: ProvidableCompositionLocal<DialogNavigator> =
    staticCompositionLocalOf {
        error("DialogNavigator not initialized")
    }

@ExperimentalComposeUiApi
@Composable
public fun DialogNavigator(
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    dialogContent: DialogNavigatorContent = { CurrentScreen() },
    content: DialogNavigatorContent
) {
    val dialogState = rememberDialogState()

    Navigator(EmptyDialogScreen, onBackPressed = null) { navigator ->
        val dialogNavigator = remember(navigator, dialogState) {
            DialogNavigator(navigator, dialogState)
        }

        CompositionLocalProvider(LocalDialogNavigator.provides(dialogNavigator)) {
            if (dialogState.isVisible) {
                Dialog(
                    onDismissRequest = { dialogNavigator.hide() },
                    properties = properties
                ) {
                    dialogContent(dialogNavigator)
                }
            }
            content(dialogNavigator)
        }
    }
}

public class DialogNavigator internal constructor(
    navigator: Navigator,
    private val dialogState: DialogState
) : Stack<Screen> by navigator {

    public val lastItem: Screen by derivedStateOf {
        lastItemOrNull ?: error("Navigator has no screen")
    }

    public val isVisible: Boolean get() = dialogState.isVisible

    public fun show(screen: Screen) {
        replaceAll(screen)
        dialogState.show()
    }

    public fun hide() {
        dialogState.hide()
        replaceAll(EmptyDialogScreen)
    }
}

private object EmptyDialogScreen : Screen {
    @Composable
    override fun Content() = Unit
}
