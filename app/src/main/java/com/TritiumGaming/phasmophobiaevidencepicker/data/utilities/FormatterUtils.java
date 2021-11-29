package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities;

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
}
