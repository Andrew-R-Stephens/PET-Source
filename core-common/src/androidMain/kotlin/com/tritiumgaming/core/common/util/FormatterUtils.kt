package com.tritiumgaming.core.common.util

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong

object FormatterUtils {

    private const val SECOND_IN_MILLIS = 1000L

    fun formatMillisToTime(millis: Long): String {
        val breakdown = millis / SECOND_IN_MILLIS
        return millisToTime(
            "%s:%s",
            breakdown
        )
    }

    fun Long.roundMillisToDuration(
        roundMillis: Long
    ): Long {
        val half = (roundMillis * .5f).roundToLong()
        return ((this + half) / roundMillis) * roundMillis
    }

    private fun millisToTime(format: String, millis: Long): String {
        val minutes = millis / 60L
        val seconds = millis % 60L
        return String.format(
            format,
            DecimalFormat("0").format(minutes),
            DecimalFormat("00").format(seconds)
        )
    }

    fun wrapCData(raw: String?): String {
        return String.format("%s%s%s", "<![CDATA[", raw, "]]>")
    }

    @Deprecated("Unused")
    fun obfuscateEmailSpannable(
        accountEmail: String,
        @ColorInt obfuscationColor: Int
    ): SpannableString {
        val startSpan = min(
            4.0,
            (accountEmail.indexOf('@') * .8).toInt().toDouble()
        )
            .toInt()
        var endSpan = accountEmail.indexOf('@')
        endSpan = max(0.0, max(startSpan.toDouble(), endSpan.toDouble())).toInt()

        val emailObfuscated =
            SpannableString(accountEmail)
        emailObfuscated.setSpan(
            BackgroundColorSpan(obfuscationColor),
            startSpan,
            endSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        emailObfuscated.setSpan(
            ForegroundColorSpan(obfuscationColor),
            startSpan,
            endSpan,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return emailObfuscated
    }

    fun Float.toPercentageString(placeholder: Boolean = true): String {
        val format = if(placeholder) { "%3d%%" } else { "%d%%" }
        return String.format(
            Locale.ROOT,
            format,
            (this * 100).toInt()
        )
    }

    fun Float.toDecimalString(): String {
        val format = "%.1f"
        return String.format(Locale.ROOT, format, this)
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

    @JvmStatic
    fun replaceHTMLFontColor(msg: String?, oldColor: String, newColor: String): String {
        var msg = msg ?: return ""

        val startTag = "<font color="
        val endTag = ">"
        val targetTag = "$startTag#$oldColor$endTag"
        msg = msg.replace(targetTag.toRegex(), "$startTag$newColor$endTag")

        return msg
    }

}