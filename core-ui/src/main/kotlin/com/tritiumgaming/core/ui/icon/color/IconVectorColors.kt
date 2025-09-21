package com.tritiumgaming.core.ui.icon.color

import android.os.Build
import androidx.core.graphics.toColorInt
import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color as ComposeColor

data class IconVectorColors(
    var fillColor: ComposeColor,
    var strokeColor: ComposeColor
) {

    constructor(
        fillColor: AndroidColor,
        strokeColor: AndroidColor
    ): this(
        fillColor = convert(fillColor),
        strokeColor = convert(strokeColor)
    )

    companion object {
        fun defaults(
            fillColor: ComposeColor = ComposeColor.Unspecified,
            strokeColor: ComposeColor = ComposeColor.Unspecified
        ): IconVectorColors = IconVectorColors(
            fillColor = fillColor,
            strokeColor = strokeColor
        )
        
        private fun convert(color: AndroidColor): ComposeColor {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ComposeColor(color.toArgb())
            } else {
                val cInt = color.toString().toColorInt()
                ComposeColor(cInt)
            }
        }
    }

}
