package com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class ItemStoreGroupModel {
    @JvmField
    @get:StringRes
    @StringRes
    var nameData: Int = 0
    private val itemData = ArrayList<ItemStoreItemModel>()

    @get:DrawableRes
    @DrawableRes
    var paginationIcon: Int = 0

    fun addItem(item: ItemStoreItemModel) {
        itemData.add(item)
    }

    fun getItemDataAt(itemIndex: Int): ItemStoreItemModel {
        return itemData[itemIndex]
    }

    val size: Int
        get() = itemData.size

    val itemImages: ArrayList<Int>
        get() {
            @DrawableRes val images = ArrayList<Int>()
            for (data in itemData) {
                images.add(data.imageData)
            }
            return images
        }
}