package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model

import android.content.Context
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

class CodexEquipmentItem : CodexGroups.CodexGroup.CodexGroupItem() {

    @IntegerRes var upgradeCostData: Int = 0
    @IntegerRes var upgradeLevelData: Int = 0
        private set
    @StringRes val positiveAttributes: ArrayList<Int> = ArrayList()
    @StringRes val negativeAttributes: ArrayList<Int> = ArrayList()

    fun setUpgradeLevel(@IntegerRes levelData: Int) {
        this.upgradeLevelData = levelData
    }

    fun addPositiveAttribute(value: Int) {
        positiveAttributes.add(value)
    }

    fun addNegativeAttribute(value: Int) {
        negativeAttributes.add(value)
    }

    override fun getAllAttributesAsFormattedHTML(c: Context): String {
        val pos = c.getString(R.string.shop_equipment_attribute_opinion_positive)
        val neg = c.getString(R.string.shop_equipment_attribute_opinion_negative)
        val invsp = "&nbsp;"

        val out = StringBuilder()
        for (v in positiveAttributes) {
            val attr = c.getString(v).replace(" ", invsp)
            out.append(pos).append(invsp).append(attr).append(invsp).append(" ")
        }
        for (v in negativeAttributes) {
            val attr = c.getString(v).replace(" ", invsp)
            out.append(neg).append(invsp).append(attr).append(invsp).append(" ")
        }

        return out.toString()
    }

}