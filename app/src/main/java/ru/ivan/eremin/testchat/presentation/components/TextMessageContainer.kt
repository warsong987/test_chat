package ru.ivan.eremin.testchat.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextMessageContainer(
    message: String,
    time: String,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 8.dp,
    verticalPadding: Dp = 8.dp,
) {
    val textMessageDimens = remember{ TextMessageDimens() }
    val content = @Composable {
        val onTextLayout: (TextLayoutResult) -> Unit = {textResult ->
            textMessageDimens.textResult.value = textResult
        }

        HtmlText(
            text = message,
            onTextLayout = onTextLayout,
            modifier = modifier,
            htmlMode = HtmlMode.LINKS_AND_NEW_LINE
        )
        Text(
            text = time
        )
    }
    CustomTextMessageLayout(
        content = content,
        modifier = modifier,
        textMessageDimens = textMessageDimens,
        horizontalPadding = horizontalPadding,
        verticalPadding = verticalPadding,
    )
}


@Composable
private fun CustomTextMessageLayout(
    modifier: Modifier,
    textMessageDimens: TextMessageDimens,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    content: @Composable () -> Unit,
) {
    Layout(content = content, modifier = modifier) { measurable, constraints ->
        val itemConstraints = constraints.copy(minHeight = 0, minWidth = 0)
        val placeables: List<Placeable> = measurable.map { it.measure(itemConstraints) }
        require(placeables.size == 2)

        val message = placeables.first()
        val time = placeables.last()
        val horizontalPaddingPx = horizontalPadding.roundToPx()
        val verticalPaddingPx = verticalPadding.roundToPx()

        val parentWidth = constraints.maxWidth
        val resultWidth: Int
        val resultHeight: Int
        val timeY: Int
        when {
            (message.width + time.width + horizontalPaddingPx) < parentWidth -> {
                resultWidth = message.width + time.width + horizontalPaddingPx
                resultHeight = maxOf(message.height, time.height)
                timeY = message[LastBaseline] - time[LastBaseline]
            }

            (textMessageDimens.lastLineWidth + time.width + horizontalPaddingPx) < parentWidth -> {
                resultWidth = maxOf((textMessageDimens.lastLineWidth + time.width + horizontalPaddingPx), message.width)
                resultHeight = maxOf(message.height, time.height)
                timeY = message[LastBaseline] - time[LastBaseline]
            }

            else -> {
                resultWidth = maxOf(message.width, time.width)
                resultHeight = (message.height + time.height + verticalPaddingPx)
                timeY = resultHeight - time.height
            }
        }

        layout(resultWidth, resultHeight) {
            message.placeRelative(0, 0)
            time.placeRelative(
                resultWidth - time.width,
                timeY
            )
        }
    }
}

@Stable
private class TextMessageDimens {
    val textResult = mutableStateOf<TextLayoutResult?>(null)
    val lineCount by derivedStateOf {
        textResult.value?.lineCount ?: 0
    }
    val lastLineWidth by derivedStateOf {
        textResult.value?.getLineRight((lineCount - 1).coerceAtLeast(0))?.toInt() ?: 0
    }
}
