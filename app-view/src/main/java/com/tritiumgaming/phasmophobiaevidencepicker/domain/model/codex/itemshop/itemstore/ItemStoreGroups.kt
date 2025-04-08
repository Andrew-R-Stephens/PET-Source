package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore

open class ItemStoreGroups {

    val groups: ArrayList<ItemStoreGroup> = ArrayList()

    fun addGroup(groupItems: ItemStoreGroup) {
        groups.add(groupItems)
    }

    fun getItemAt(groupIndex: Int, itemIndex: Int): ItemStoreItem {
        val item: ItemStoreItem?

        val group = groups[groupIndex]
        item = group.getItemDataAt(itemIndex)

        return item
    }

    fun getGroupAt(groupIndex: Int): ItemStoreGroup {
        return groups[groupIndex]
    }

}