package com.TritiumGaming.phasmophobiaevidencepicker.data.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils

object ColorUtils {

    @JvmStatic
    fun getRGB(color: Int): IntArray {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return intArrayOf(a, r, g, b)
    }

    @JvmStatic
    fun setColor(color: Int, alpha: Int, red: Int, green: Int, blue: Int): Int {
        var a = alpha
        var r = red
        var g = green
        var b = blue

        val rgb = getRGB(color)

        if (a < 0) { a = rgb[ARGB.A.ordinal] }
        if (r < 0) { r = rgb[ARGB.R.ordinal] }
        if (g < 0) { g = rgb[ARGB.G.ordinal] }
        if (b < 0) { b = rgb[ARGB.B.ordinal] }

        return Color.argb(
            a.also { rgb[ARGB.A.ordinal] = it },
            r.also { rgb[ARGB.R.ordinal] = it },
            g.also { rgb[ARGB.G.ordinal] = it },
            b.also { rgb[ARGB.B.ordinal] = it }
        )
    }

    @JvmStatic
    fun interpolate(
        @ColorInt startColor: Int, @ColorInt endColor: Int,
        @FloatRange(from = 0.0, to = 1.0) ratio: Float
    ): Int {
        return ColorUtils.blendARGB(endColor, startColor, ratio)
    }

    @JvmStatic
    fun intToHex(color: Int): String {
        return if (color.toString().length < 6) {
            "#FFFFFF"
        } else String.format("#%06X", 0xFFFFFF and color)
    }

    internal enum class ARGB {
        A, R, G, B
    }

    @JvmStatic
    fun getColorFromAttribute(context: Context, attribute: Int) : Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attribute, typedValue, true)
        return (
            if (typedValue.resourceId != 0)
                context.theme.resources.getColor(typedValue.resourceId)
            else
                typedValue.data
        )
    }

}
