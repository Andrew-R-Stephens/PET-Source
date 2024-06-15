package com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class ItemStoreItemModel {
    @JvmField
    @get:StringRes
    @StringRes
    var flavorData: Int = 0

    @JvmField
    @get:StringRes
    @StringRes
    var infoData: Int = 0

    @JvmField
    @get:DrawableRes
    @DrawableRes
    var imageData: Int = 0


    abstract fun getAllAttributesAsFormattedHTML(c: Context): String
}