package ru.ivan.eremin.components.phone

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.absoluteValue

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != MASK_SYMBOL }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        while (specialSymbolsIndices.contains(maskIndex)) {
            out += mask[maskIndex]
            maskIndex++
        }
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == MASK_SYMBOL) numberOfHashtags++
                (numberOfHashtags == 0) || (numberOfHashtags < offsetValue)
            }
            return masked.length + if (offset.absoluteValue > 0) 1 else 0
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == MASK_SYMBOL }
        }
    }
}

private const val MASK_SYMBOL = '#'