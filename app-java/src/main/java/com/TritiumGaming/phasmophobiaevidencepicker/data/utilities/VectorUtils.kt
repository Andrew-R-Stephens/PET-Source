package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toBitmap
import org.jetbrains.annotations.NotNull

object VectorUtils {

    @JvmStatic
    fun toBitmap(@NotNull context: Context, @DrawableRes drawableId: Int) : Bitmap? {
        return AppCompatResources.getDrawable(context, drawableId)?.toBitmap()
    }

}