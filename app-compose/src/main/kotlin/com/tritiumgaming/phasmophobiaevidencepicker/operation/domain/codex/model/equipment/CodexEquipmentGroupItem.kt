package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierFlavorText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierInformation
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UnlockLevel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UpgradeCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource

data class CodexEquipmentGroupItem(
    val image: TierImage,
    val flavor: TierFlavorText,
    val info: TierInformation,
    val upgradeCostData: UpgradeCost,
    val upgradeLevelData: UnlockLevel,
    val positiveAttributes: List<EquipmentAttribute>,
    val negativeAttributes: List<EquipmentAttribute>
) {

    fun getAllAttributesAsFormattedHTML(c: Context): String {
        val pos = c.getString(R.string.shop_equipment_attribute_opinion_positive)
        val neg = c.getString(R.string.shop_equipment_attribute_opinion_negative)
        val invsp = "&nbsp;"

        val out = StringBuilder()
        for (v in positiveAttributes) {
            val attr = c.getString(v.toStringResource()).replace(" ", invsp)
            out.append(pos).append(invsp).append(attr).append(invsp).append(" ")
        }
        for (v in negativeAttributes) {
            val attr = c.getString(v.toStringResource()).replace(" ", invsp)
            out.append(neg).append(invsp).append(attr).append(invsp).append(" ")
        }

        return out.toString()
    }
}