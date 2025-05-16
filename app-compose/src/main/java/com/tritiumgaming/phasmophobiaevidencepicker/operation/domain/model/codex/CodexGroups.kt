package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.codex

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.codex.CodexGroups.CodexGroup.CodexGroupItem

open class CodexGroups {

    val groups: ArrayList<CodexGroup> = ArrayList()

    fun addGroup(groupItems: CodexGroup) {
        groups.add(groupItems)
    }

    fun getItemAt(groupIndex: Int, itemIndex: Int): CodexGroupItem {
        val item: CodexGroupItem?

        val group = groups[groupIndex]
        item = group.getItemDataAt(itemIndex)

        return item
    }

    fun getGroupAt(groupIndex: Int): CodexGroup {
        return groups[groupIndex]
    }

    abstract class CodexGroup {

        @StringRes
        var nameData: Int = 0
        private val itemData = ArrayList<CodexGroupItem>()

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
        fun addItem(item: CodexGroupItem) {
            itemData.add(item)
        }

        fun getItemDataAt(itemIndex: Int): CodexGroupItem {
            return itemData[itemIndex]
        }

        abstract class CodexGroupItem {

            @StringRes var flavorData: Int = 0
            @StringRes var infoData: Int = 0
            @DrawableRes var imageData: Int = 0

            abstract fun getAllAttributesAsFormattedHTML(c: Context): String
        }

    }

}
