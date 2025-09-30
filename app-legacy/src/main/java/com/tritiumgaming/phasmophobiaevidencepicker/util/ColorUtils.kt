package com.tritiumgaming.phasmophobiaevidencepicker.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.StyleRes
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils

object ColorUtils {

    fun getRGB(color: Int): IntArray {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return intArrayOf(a, r, g, b)
    }

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

    fun interpolate(
        @ColorInt startColor: Int, @ColorInt endColor: Int,
        @FloatRange(from = 0.0, to = 1.0) ratio: Float
    ): Int {
        return ColorUtils.blendARGB(endColor, startColor, ratio)
    }

    fun intToHex(color: Int): String {
        return if (color.toString().length < 6) {
            "#FFFFFF"
        } else String.format("#%06X", 0xFFFFFF and color)
    }

    internal enum class ARGB {
        A, R, G, B
    }

    fun getColorFromAttribute(context: Context, @AttrRes attribute: Int) : Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attribute, typedValue, true)
        return (
            if (typedValue.resourceId != 0)
                context.theme.resources.getColor(typedValue.resourceId)
            else
                typedValue.data
        )
    }

    fun getDrawableFromAttribute(context: Context, @AttrRes attribute: Int) : Int? {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attribute, typedValue, true)
        val drawableRes = typedValue.resourceId
        return (if (drawableRes != 0) { drawableRes } else { null })
    }

    fun getTextStyleFromAttribute(
        context: Context,
        @AttrRes styleReferenceAttribute: Int
    ): TextStyle {
        val typedValue = TypedValue()

        // 1. Resolve the attribute to get the resource ID of the referenced style
        if (context.theme.resolveAttribute(styleReferenceAttribute, typedValue, true) &&
            typedValue.resourceId != 0
        ) {
            @StyleRes val referencedStyleResId = typedValue.resourceId

            val attributesToReadFromStyle = intArrayOf(
                android.R.attr.fontFamily,
                android.R.attr.textSize,
                android.R.attr.textColor
            )

            val styleTypedArray: TypedArray =
                context.obtainStyledAttributes(referencedStyleResId, attributesToReadFromStyle)

            var textStyleBuilder = TextStyle.Default
            try {
                var fontFamily: FontFamily? = null

                val androidFontFamilyIndex = attributesToReadFromStyle.indexOf(android.R.attr.fontFamily)
                if (androidFontFamilyIndex != -1 && styleTypedArray.hasValue(androidFontFamilyIndex)) {
                    val androidFontFamilyResId = styleTypedArray.getResourceId(androidFontFamilyIndex, 0)
                    if (androidFontFamilyResId != 0) {
                        try {
                            val typeface = ResourcesCompat.getFont(context, androidFontFamilyResId)
                            fontFamily = typeface?.let { FontFamily(it) }
                        } catch (e: Exception) { /* ... */ }
                    } else {
                        styleTypedArray.getString(androidFontFamilyIndex)?.let {
                            fontFamily = mapFontFamilyString(it)
                        }
                    }
                }
                fontFamily?.let { textStyleBuilder = textStyleBuilder.copy(fontFamily = it) }

            } finally {
                styleTypedArray.recycle()
            }
            return textStyleBuilder
        }

        // Fallback or default if the attribute couldn't be resolved or wasn't a style reference
        return TextStyle.Default
    }

    // (Keep the mapFontFamilyString helper from the previous example)
    private fun mapFontFamilyString(fontFamilyName: String?): FontFamily? {
        return when (fontFamilyName?.lowercase()) {
            "sans-serif" -> FontFamily.SansSerif
            "serif" -> FontFamily.Serif
            "monospace" -> FontFamily.Monospace
            "cursive" -> FontFamily.Cursive
            else -> null
        }
    }
}
