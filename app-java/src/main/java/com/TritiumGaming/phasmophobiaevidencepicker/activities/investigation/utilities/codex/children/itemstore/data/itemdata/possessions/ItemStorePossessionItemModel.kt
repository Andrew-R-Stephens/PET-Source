package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.possessions

import android.content.Context
import androidx.annotation.StringRes
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.data.itemdata.ItemStoreItemModel

class ItemStorePossessionItemModel : ItemStoreItemModel() {
    @StringRes
    private val attributes = ArrayList<Int>()

    @JvmField
    @StringRes
    var altName: Int = 0

    @StringRes
    var sanityDrainData: Int = 0

    @JvmField
    @StringRes
    var drawChance: Int = 0

    fun addAttribute(value: Int) {
        attributes.add(value)
    }

    override fun getAllAttributesAsFormattedHTML(c: Context): String {
        val out = StringBuilder()
        for (v in attributes) {
            out.append(c.getString(v)).append(" ")
        }

        return out.toString()
    }
}