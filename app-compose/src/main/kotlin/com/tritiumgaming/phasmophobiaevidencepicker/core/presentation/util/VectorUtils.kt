package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap

@Deprecated("Unused")
object VectorUtils {

    fun toBitmap(context: Context, @DrawableRes drawableId: Int) : Bitmap? {
        return AppCompatResources.getDrawable(context, drawableId)?.toBitmap()
    }

}