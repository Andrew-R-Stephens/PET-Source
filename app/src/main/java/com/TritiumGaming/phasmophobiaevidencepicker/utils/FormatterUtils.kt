package com.TritiumGaming.phasmophobiaevidencepicker.utils

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.min

object FormatterUtils {
    @JvmStatic
    fun millisToTime(format: String, millis: Long): String {
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

    @JvmStatic
    fun obfuscateEmailSpannable(
        accountEmail: String,
        @ColorInt obfuscationColor: Int
    ): SpannableString {
        val startSpan = min(
            4.0,
            (accountEmail.indexOf('@') * .8).toInt().toDouble())
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
}
