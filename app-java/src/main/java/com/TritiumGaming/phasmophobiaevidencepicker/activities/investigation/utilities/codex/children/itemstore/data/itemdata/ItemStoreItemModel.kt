package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata

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