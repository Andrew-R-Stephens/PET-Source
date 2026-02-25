package com.tritiumgaming.core.common.util

import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import java.util.Locale

/**
 * FontStyler class
 *
 * @author TritiumGamingStudios
 */
object FontUtils {

    fun setColor(plainStr: String, colorHex: String): String {
        return "<font color=#$colorHex>$plainStr</font>"
    }

    fun dpToSp(view: View, dpActual: Float): Float {
        return (TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            dpActual,
            view.context.resources.displayMetrics
        )) /
                view.context.resources.displayMetrics.scaledDensity
    }

    fun htmlToStyled(str: String): Spanned {
        return Html.fromHtml("<![CDATA[$str]]>")
    }

    @JvmStatic
    fun replaceHTMLFontColor(msg: String?, oldColor: String, newColor: String): String {
        var msg = msg ?: return ""

        val startTag = "<font color="
        val endTag = ">"
        val targetTag = "$startTag#$oldColor$endTag"
        msg = msg.replace(targetTag.toRegex(), "$startTag$newColor$endTag")

        return msg
    }

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

    /**
     * Removes img sources and the surrounding tags.
     * Pretty inefficient, but fine for now.
     *
     * @param msg - raw HTML content
     * @return trimmedHTML
     */
    fun removeXMLImgSrcTags(msg: String?): String {
        var msg = msg ?: return ""

        var indexStart = msg.indexOf("<img src=")

        while (indexStart >= 0) {
            var newStr = msg.substring(indexStart)
            val indexEnd = newStr.indexOf("/>")
            newStr = msg.substring(indexStart, indexStart + indexEnd + 2)
            msg = msg.replaceFirst(newStr.toRegex(), "")

            indexStart = msg.indexOf("<img src=")
        }

        return msg
    }

    /**
     * Removes pubDate's Time (which starts with '+', followed by digits).
     *
     * @param msg - raw HTML content
     * @return trimmed content
     */
    fun removeXMLPubDateClockTime(msg: String?): String {
        if (msg == null) {
            return ""
        }

        val endIndex = msg.indexOf('+')

        if (endIndex < 0) {
            return msg
        }

        return msg.substring(0, endIndex).trim { it <= ' ' }
    }

    fun toTitle(s: String): String {
        val titleCase = StringBuilder(s.length)
        var nextTitleCase = true

        for (c in s.lowercase(Locale.getDefault()).toCharArray()) {
            var char = c
            if (!Character.isLetterOrDigit(char)) {
                nextTitleCase = true
            } else if (nextTitleCase) {
                char = char.titlecaseChar()
                nextTitleCase = false
            }
            titleCase.append(char)
        }

        return titleCase.toString().replace("_", " ")
    }
}