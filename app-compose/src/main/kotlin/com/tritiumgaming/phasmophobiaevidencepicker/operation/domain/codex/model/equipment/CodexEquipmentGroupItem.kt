package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

data class CodexEquipmentGroupItem(
    @DrawableRes val image: Int,
    @StringRes val flavor: Int,
    @StringRes val info: Int,
    @IntegerRes var upgradeCostData: Int = 0,
    @IntegerRes var upgradeLevelData: Int = 0,
    @StringRes val positiveAttributes: List<Int> = ArrayList(),
    @StringRes val negativeAttributes: List<Int> = ArrayList()
) {

    fun getAllAttributesAsFormattedHTML(c: Context): String {
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