package com.tritiumgaming.core.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.tritiumgaming.core.common.util.FormatterUtils.replaceHTMLFontColor

/**
 * FontStyler class
 *
 * @author TritiumGamingStudios
 */
object FontUtils {

    fun replaceHTMLFontColor(msg: String, oldColor: String, newColor: Color): String {
        return replaceHTMLFontColor(
            msg, oldColor, "#${newColor.toArgb().toHexString().substring(2)}"
        )
    }

    @JvmName("selfReplaceHTMLFontColor")
    fun String.replaceHTMLFontColor(oldColor: String, newColor: Color): String {
        return replaceHTMLFontColor(
            this, oldColor, "#${newColor.toArgb().toHexString().substring(2)}"
        )
    }

    @Composable
    fun htmlToSpannable(msg: String = "", oldColor: String, newColor: Color): AnnotatedString {

        val startElement = "<font color="
        val endElement = ">"
        val terminateTagImplicit = "</font>"
        val terminateTagExplicit = "/>"
        val targetTag = "$startElement$oldColor$endElement"
        //val msg = msg.replace(targetTag.toRegex(), startTag + oldColor + endTag)

        val split = msg.split(targetTag, terminateTagExplicit, terminateTagImplicit)

        return buildAnnotatedString {

            var innerFlag = false
            for (s in split) {
                when (s) {
                    targetTag -> {
                        innerFlag = true
                        continue
                    }

                    terminateTagExplicit -> {
                        innerFlag = false
                        break
                    }

                    terminateTagImplicit -> {
                        innerFlag = false
                        break
                    }
                }

                if (innerFlag) {
                    withStyle(style = SpanStyle(color = newColor)) {
                        append(s)
                    }
                } else {
                    append(s)
                }
            }
        }
    }

}