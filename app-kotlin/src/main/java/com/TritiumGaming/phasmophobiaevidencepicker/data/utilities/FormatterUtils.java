package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;

import androidx.annotation.ColorInt;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class FormatterUtils {

    public static String millisToTime(String format, long millis) {

        long minutes = millis / 60L;
        long seconds = millis % 60L;
        return String.format(
                format,
                new DecimalFormat("0").format(minutes),
                new DecimalFormat("00").format(seconds));

    }

    public static String wrapCData(String raw) {
        return String.format("%s%s%s", "<![CDATA[", raw, "]]>");
    }

    public static SpannableString obfuscateEmailSpannable(@NotNull String accountEmail,
                                                          @ColorInt int obfuscationColor) {

        int startSpan = Math.min(4, (int)(accountEmail.indexOf('@')*.8));
        int endSpan = (int)(accountEmail.indexOf('@'));
        endSpan = Math.max(0, Math.max(startSpan, endSpan));

        SpannableString email_obfuscated =
                new SpannableString(accountEmail);
        email_obfuscated.setSpan(
                new BackgroundColorSpan(obfuscationColor),
                startSpan,
                endSpan,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        email_obfuscated.setSpan(
                new ForegroundColorSpan(obfuscationColor),
                startSpan,
                endSpan,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        return email_obfuscated;

    }
}
