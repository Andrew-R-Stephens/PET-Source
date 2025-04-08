package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class ItemStoreGroup {

    @StringRes
    var nameData: Int = 0
    private val itemData = ArrayList<ItemStoreItem>()

    @DrawableRes
    var paginationIcon: Int = 0

    val size: Int = itemData.size

    val itemImages: ArrayList<Int>
        get() {
            @DrawableRes val images = ArrayList<Int>()
            for (data in itemData) {
                images.add(data.imageData)
            }
            return images
        }
    fun addItem(item: ItemStoreItem) {
        itemData.add(item)
    }

    fun getItemDataAt(itemIndex: Int): ItemStoreItem {
        return itemData[itemIndex]
    }

}