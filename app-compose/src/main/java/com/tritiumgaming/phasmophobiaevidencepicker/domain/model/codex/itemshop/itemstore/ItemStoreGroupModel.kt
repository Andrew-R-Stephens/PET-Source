package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class ItemStoreGroupModel {

    @StringRes
    var nameData: Int = 0
    private val itemData = ArrayList<com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreItemModel>()

    @get:DrawableRes
    @DrawableRes
    var paginationIcon: Int = 0

    fun addItem(item: com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreItemModel) {
        itemData.add(item)
    }

    fun getItemDataAt(itemIndex: Int): com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreItemModel {
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