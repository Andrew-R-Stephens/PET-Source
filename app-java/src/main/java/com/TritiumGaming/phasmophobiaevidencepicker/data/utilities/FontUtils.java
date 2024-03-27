package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * FontStyler class
 *
 * @author TritiumGamingStudios
 */
public class FontUtils {

    /**
     * @param plainStr
     * @param colorHex
     * @return
     */
    @NonNull
    public static String setColor(String plainStr, String colorHex) {
        return "<font color=#" + colorHex + ">" + plainStr + "</font>";
    }

    /**
     * @param view
     * @param dpActual
     * @return
     */
    public static float dpToSp(@NonNull View view, float dpActual) {
        return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dpActual, view.getContext().
                getResources().getDisplayMetrics())) /
                view.getContext().getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * @param str
     * @return
     */
    public static Spanned htmlToStyled(String str) {
        return Html.fromHtml("<![CDATA[" + str + "]]>");
    }

    /**
     * @param msg
     * @return
     */
    @NonNull
    public static String replaceHTMLFontColor(@Nullable String msg, String oldColor, String newColor) {
        if (msg == null) {
            return "";
        }

        String startTag = "<font color=", endTag = ">", targetTag = startTag + oldColor + endTag;
        msg = msg.replaceAll(targetTag, startTag + newColor + endTag);

        return msg;
    }

    /**
     * cleanupHTML
     * <p>
     * Removes img sources and the surrounding tags.
     * Pretty inefficient, but fine for now.
     *
     * @param msg - raw HTML content
     * @return trimmedHTML
     */
    @NonNull
    public static String removeXMLImgSrcTags(@Nullable String msg) {
        if (msg == null) {
            return "";
        }

        int indexStart = msg.indexOf("<img src=");

        while (indexStart >= 0) {
            String newStr = msg.substring(indexStart);
            int indexEnd = newStr.indexOf("/>");
            newStr = msg.substring(indexStart, indexStart + indexEnd + 2);
            msg = msg.replaceFirst(newStr, "");

            indexStart = msg.indexOf("<img src=");
        }

        return msg;
    }

    /**
     * cleanupHTML
     * <p>
     * Removes pubDate's Time (which starts with '+', followed by digits).
     *
     * @param msg - raw HTML content
     * @return trimmed content
     */
    @NonNull
    public static String removeXMLPubDateClockTime(@Nullable String msg) {
        if (msg == null) {
            return "";
        }

        int endIndex = msg.indexOf('+');

        if (endIndex < 0) {
            return msg;
        }

        return msg.substring(0, endIndex).trim();
    }

    @NonNull
    public static String toTitle(@NonNull String s) {

        StringBuilder titleCase = new StringBuilder(s.length());
        boolean nextTitleCase = true;

        for (char c : s.toLowerCase().toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }

        return titleCase.toString().replace("_", " ");

    }

}
