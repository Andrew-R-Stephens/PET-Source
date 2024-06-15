package com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigationUtils.codex.itemstore

class ItemStoreListModel {
    val groups: ArrayList<ItemStoreGroupModel> = ArrayList()

    fun addGroup(groupItems: ItemStoreGroupModel) {
        groups.add(groupItems)
    }

    fun getItemAt(groupIndex: Int, itemIndex: Int): ItemStoreItemModel {
        val item: ItemStoreItemModel?

        val group = groups[groupIndex]
        item = group.getItemDataAt(itemIndex)

        return item
    }

    fun getGroupAt(groupIndex: Int): ItemStoreGroupModel {
        return groups[groupIndex]
    }
}