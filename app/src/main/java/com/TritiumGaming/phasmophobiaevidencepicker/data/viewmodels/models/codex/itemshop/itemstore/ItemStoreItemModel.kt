package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.codex.itemshop.itemstore

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class ItemStoreItemModel {

    @StringRes var flavorData: Int = 0

    @StringRes var infoData: Int = 0

    @DrawableRes var imageData: Int = 0

    abstract fun getAllAttributesAsFormattedHTML(c: Context): String
}