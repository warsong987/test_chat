package ru.ivan.eremin.components

import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import androidx.core.text.util.LinkifyCompat
import java.util.regex.Pattern

@Composable
fun HtmlText(
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
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    uriHandler: UriHandler = LocalUriHandler.current,
    htmlMode: HtmlMode = HtmlMode.DEFAULT,
    urlClick: ((String) -> Unit)? = { uriHandler.openUri(it) }
) {
    val annotationText = annotatedString(text, htmlMode)
    val rememberOnClick: (Int) -> Unit = {
        annotationText.getStringAnnotations(ANNOTATED_TAG_URL, it,it).firstOrNull()?.let { tag ->
            urlClick?.invoke(tag.item)
        }
    }

    val layoutResult = remember{mutableStateOf<TextLayoutResult?>(null) }
    val clickable = remember(annotationText) {
        annotationText.getStringAnnotations(0, annotationText.length - 1).any {
            it.tag == ANNOTATED_TAG_URL
        }
    }
    val pressIndicator = Modifier.then(
        if (urlClick != null && clickable) {
            Modifier.pointerInput(rememberOnClick) {
                detectTapGestures { pos ->
                    layoutResult.value?.let { layoutResult ->
                        rememberOnClick(layoutResult.getOffsetForPosition(pos))
                    }
                }
            }
        } else {
            Modifier
        }
    )

    Text(
        text = annotationText,
        modifier = modifier.then(pressIndicator),
        skeleton = skeleton,
        skeletonPercent = skeletonPercent,
        skeletonLines = skeletonLines,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        minLines = minLines,
        maxLines = maxLines,
        onTextLayout = {
            layoutResult.value = it
            onTextLayout(it)
        },
        style = style
    )
}


@Immutable
enum class HtmlMode(internal val flags: Int, internal val saveNewLine: Boolean) {
    DEFAULT(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM or HtmlCompat.FROM_HTML_MODE_COMPACT, false),
    FULL(0, false),
    FULL_AND_NEW_LINE(0, true),
    LINKS_AND_NEW_LINE(0, true)
}

private val PHONE_NUMBER_PATTERN = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}\$")
private const val NEW_LINE = "\n"
private const val HTML_TAG_BR = "<br>"
private const val HTML_TAG_LI = "<li>"


fun String.asHtml(mode: HtmlMode): CharSequence {
    return this.asHtmlSpan(mode).trim()
}

private fun String.asHtmlSpan(mode: HtmlMode): Spanned {
    return try {
        val text = if (mode.saveNewLine) {
            this.replace(NEW_LINE, HTML_TAG_BR)
        } else {
            this
        }
            .replace(HTML_TAG_LI, "${HTML_TAG_LI}•\t")
        val result = HtmlCompat.fromHtml(text, mode.flags)
        if (mode == HtmlMode.LINKS_AND_NEW_LINE && result is Spannable) {
            val oldUrls = result.getSpans(0, result.length, URLSpan::class.java)
                .map { SpanData(it, result.getSpanStart(it), result.getSpanEnd(it)) }
            LinkifyCompat.addLinks(result, Linkify.WEB_URLS or Linkify.EMAIL_ADDRESSES)
            LinkifyCompat.addLinks(result, PHONE_NUMBER_PATTERN, "tel:")
            for (urlSpan in oldUrls) {
                result.setSpan(urlSpan.span, urlSpan.start, urlSpan.end, 0)
            }
        }
        result
    } catch (e: Exception) {
        this.toSpanned()
    }
}

@Immutable
private class SpanData(
    val span: URLSpan,
    val start: Int,
    val end: Int
)

@Composable
fun annotatedString(htmlString: String, mode: HtmlMode = HtmlMode.DEFAULT): AnnotatedString {
    val color = MaterialTheme.colorScheme.primary
    val density = LocalDensity.current
    return remember(htmlString, color, mode) {
        spannableStringToAnnotatedString(htmlString.asHtml(mode), color, density)
    }
}

@Suppress("ComplexMethod")
private fun spannableStringToAnnotatedString(
    text: CharSequence,
    linkColor: Color,
    density: Density
): AnnotatedString {
    return if (text is Spanned) {
        with(density) {
            buildAnnotatedString {
                append((text.toString()))
                text.getSpans(0, text.length, Any::class.java).forEach {
                    val start = text.getSpanStart(it)
                    val end = text.getSpanEnd(it)
                    when (it) {
                        is StyleSpan -> when (it.style) {
                            Typeface.NORMAL -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Normal
                                ),
                                start,
                                end
                            )

                            Typeface.BOLD -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontStyle = FontStyle.Normal
                                ),
                                start,
                                end
                            )

                            Typeface.ITALIC -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontStyle = FontStyle.Italic
                                ),
                                start,
                                end
                            )

                            Typeface.BOLD_ITALIC -> addStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontStyle = FontStyle.Italic
                                ),
                                start,
                                end
                            )
                        }

                        is BulletSpan -> {
                            addStyle(SpanStyle(), start, end)
                        }

                        is AbsoluteSizeSpan -> addStyle(
                            SpanStyle(fontSize = if (it.dip) it.size.dp.toSp() else it.size.toSp()),
                            start,
                            end
                        )

                        is RelativeSizeSpan -> addStyle(
                            SpanStyle(fontSize = it.sizeChange.em),
                            start,
                            end
                        )

                        is StrikethroughSpan -> addStyle(
                            SpanStyle(textDecoration = TextDecoration.LineThrough),
                            start,
                            end
                        )

                        is UnderlineSpan -> addStyle(
                            SpanStyle(textDecoration = TextDecoration.Underline),
                            start,
                            end
                        )

                        is SuperscriptSpan -> addStyle(
                            SpanStyle(baselineShift = BaselineShift.Superscript),
                            start,
                            end
                        )

                        is SubscriptSpan -> addStyle(
                            SpanStyle(baselineShift = BaselineShift.Subscript),
                            start,
                            end
                        )

                        is ForegroundColorSpan -> addStyle(
                            SpanStyle(color = Color(it.foregroundColor)),
                            start,
                            end
                        )

                        is URLSpan -> addUrlSpan(it.url, linkColor, start, end)
                        is ImageSpan -> addImageSpan(it.source.orEmpty(), start, end)
                        else -> addStyle(SpanStyle(), start, end)
                    }
                }
            }
        }
    } else {
        AnnotatedString(text.toString())
    }
}


private fun AnnotatedString.Builder.addUrlSpan(url: String, linkColor: Color, start: Int, end: Int) {
    addStyle(
        SpanStyle(color = linkColor),
        start,
        end
    )
    addStringAnnotation(
        tag = ANNOTATED_TAG_URL,
        annotation = url,
        start = start,
        end = end
    )
}

private fun AnnotatedString.Builder.addImageSpan(url: String, start: Int, end: Int) {
    addStringAnnotation(
        tag = ANNOTATED_TAG_IMAGE,
        annotation = url,
        start = start,
        end = end
    )
    addStyle(
        ParagraphStyle(),
        start,
        end
    )
}

const val ANNOTATED_TAG_URL = "URL"
const val ANNOTATED_TAG_IMAGE = "androidx.compose.foundation.text.inlineContent"