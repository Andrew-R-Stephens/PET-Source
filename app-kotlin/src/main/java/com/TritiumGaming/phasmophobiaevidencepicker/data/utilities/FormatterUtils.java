package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class FormatterUtils {

    @NonNull
    public static String millisToTime(@NonNull String format, long millis) {

        long minutes = millis / 60L;
        long seconds = millis % 60L;
        return String.format(
                format,
                new DecimalFormat("0").format(minutes),
                new DecimalFormat("00").format(seconds));

    }

    @NonNull
    public static String wrapCData(String raw) {
        return String.format("%s%s%s", "<![CDATA[", raw, "]]>");
    }

    @NonNull
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
