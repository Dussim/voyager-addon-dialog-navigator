package xyz.dussim.voyager.navigator.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

public enum class DialogStateValue {
    Hidden, Shown
}

public class DialogState(
    initialValue: DialogStateValue = DialogStateValue.Hidden
) {
    public val isVisible: Boolean get() = currentValue == DialogStateValue.Shown

    public var currentValue: DialogStateValue by mutableStateOf(initialValue)
        private set

    public fun show() {
        currentValue = DialogStateValue.Shown
    }

    public fun hide() {
        currentValue = DialogStateValue.Hidden
    }

    public companion object {
        internal val Saver: Saver<DialogState, Any> = listSaver(
            save = { dialogState: DialogState -> listOf(dialogState.currentValue) },
            restore = { (state) -> DialogState(state) }
        )
    }
}

@Composable
public fun rememberDialogState(
    initialValue: DialogStateValue = DialogStateValue.Hidden
): DialogState = rememberSaveable(saver = DialogState.Saver) {
    DialogState(initialValue)
}
