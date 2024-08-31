package ru.ivan.eremin.testchat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    skeleton: Boolean = false,
    skeletonPercent: Float = 1f,
    skeletonLines: Int = 1,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val localContentColor = LocalContentColor.current
    val overrideColorOrUnspecified: Color = if (color.isSpecified) {
        color
    } else if (style.color.isSpecified) {
        style.color
    } else {
        localContentColor
    }
    val mergedStyle = style.merge(
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        lineHeight = lineHeight,
        fontFamily = fontFamily,
        textDecoration = textDecoration,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing
    )

    if (skeleton) {
        TextSkeleton(modifier, skeleton, skeletonLines, skeletonPercent, mergedStyle)
    } else {
        BasicText(
            text = text,
            modifier = modifier,
            style = mergedStyle,
            onTextLayout = onTextLayout,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            color = { overrideColorOrUnspecified }
        )
    }
}

@Composable
fun Text(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    skeleton: Boolean = false,
    skeletonPercent: Float = 1f,
    skeletonLines: Int = 1,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    val localContentColor = LocalContentColor.current
    val overrideColorOrUnspecified = if (color.isSpecified) {
        color
    } else if (style.color.isSpecified) {
        style.color
    } else {
        localContentColor
    }

    val mergedStyle = style.merge(
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        lineHeight = lineHeight,
        fontFamily = fontFamily,
        textDecoration = textDecoration,
        fontStyle = fontStyle,
        letterSpacing = letterSpacing
    )
    if (skeleton) {
        TextSkeleton(modifier, skeleton, skeletonLines, skeletonPercent, mergedStyle)
    } else {
        BasicText(
            text = text,
            modifier = modifier,
            style = mergedStyle,
            onTextLayout = onTextLayout,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            inlineContent = inlineContent,
            color = { overrideColorOrUnspecified }
        )
    }
}

@Composable
private fun TextSkeleton(
    modifier: Modifier = Modifier,
    skeleton: Boolean = false,
    skeletonLines: Int = 1,
    skeletonPercent: Float = 1f,
    style: TextStyle = LocalTextStyle.current
) {
    val padding = ((style.lineHeight.value - style.fontSize.value) / 2).dp
    val height = style.lineHeight.value.dp
    val horizontalAlignment = when (style.textAlign) {
        TextAlign.Center -> Alignment.CenterHorizontally
        TextAlign.End, TextAlign.Right -> Alignment.End
        else -> Alignment.Start
    }
    Column(modifier, horizontalAlignment = horizontalAlignment) {
        repeat(skeletonLines) {
            Spacer(
                modifier = Modifier
                    .height(height)
                    .fillMaxWidth(skeletonPercent)
                    .padding(vertical = padding)
                    .then(
                        if (skeleton) {
                            Modifier.background(Color.LightGray, shape = RoundedCornerShape(2.dp))
                        } else Modifier
                    )
            )
        }
    }
}