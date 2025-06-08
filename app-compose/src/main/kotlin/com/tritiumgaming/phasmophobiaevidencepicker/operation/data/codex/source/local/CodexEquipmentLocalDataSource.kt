package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexEquipmentGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexEquipmentItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.source.CodexDataSource

class CodexEquipmentLocalDataSource(
    override val applicationContext: Context
): CodexDataSource {

    override fun fetchItems(): Result<CodexGroups> {

        val resources = applicationContext.resources

        val equipment = CodexGroups()

        val shopListTypedArray = resources.obtainTypedArray(R.array.shop_equipment_array)

        val nameKey = 0
        val iconKey = 1
        val imagesKey = 2
        val flavorTextKey = 3
        val infoTextKey = 4
        val attributesTextKey = 5
        val buyCostKey = 6
        val upgradeLevelKey = 7
        val upgradeCostKey = 8

        val positiveAttrsKey = 0
        val negativeAttrsKey = 1

        for (i in 0 until shopListTypedArray.length()) {
            @StringRes var equipmentName: Int
            @IntegerRes var buyCostData: Int
            @DrawableRes var equipmentIcon: Int

            val groupData = CodexEquipmentGroup()

            val shopTypedArray =
                resources.obtainTypedArray(shopListTypedArray.getResourceId(i, 0))

            equipmentName = shopTypedArray.getResourceId(nameKey, 0)
            equipmentIcon = shopTypedArray.getResourceId(iconKey, 0)
            buyCostData = shopTypedArray.getResourceId(buyCostKey, 0)

            groupData.nameData = equipmentName
            groupData.paginationIcon = equipmentIcon
            groupData.buyCostData = buyCostData

            val iconsTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(imagesKey, 0))
            for (j in 0 until iconsTypedArray.length()) {
                val itemData = CodexEquipmentItem()
                groupData.addItem(itemData)
                @DrawableRes val value = iconsTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).imageData = value
            }
            iconsTypedArray.recycle()

            val flavorTextTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(flavorTextKey, 0))
            for (j in 0 until flavorTextTypedArray.length()) {
                @StringRes val value = flavorTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).flavorData = value
            }
            flavorTextTypedArray.recycle()

            val infoTextTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(infoTextKey, 0))
            for (j in 0 until infoTextTypedArray.length()) {
                @StringRes val value = infoTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).infoData = value
            }
            infoTextTypedArray.recycle()

            val attributesTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(attributesTextKey, 0))
            for (j in 0 until attributesTypedArray.length()) {
                val positiveAttributesTypesArray =
                    resources.obtainTypedArray(attributesTypedArray.getResourceId(j, 0))
                val positiveAttributesListTypedArray =
                    resources.obtainTypedArray(
                        positiveAttributesTypesArray.getResourceId(
                            positiveAttrsKey,
                            0
                        )
                    )
                for (l in 0 until positiveAttributesListTypedArray.length()) {
                    @StringRes val value = positiveAttributesListTypedArray.getResourceId(l, 0)
                    (groupData.getItemDataAt(j) as CodexEquipmentItem).addPositiveAttribute(
                        value
                    )
                }
                positiveAttributesListTypedArray.recycle()
                positiveAttributesTypesArray.recycle()

                val negativeAttributesTypedArray =
                    resources.obtainTypedArray(attributesTypedArray.getResourceId(j, 0))
                val negativeAttributesListTypedArray =
                    resources.obtainTypedArray(
                        negativeAttributesTypedArray.getResourceId(
                            negativeAttrsKey,
                            0
                        )
                    )
                for (l in 0 until negativeAttributesListTypedArray.length()) {
                    @StringRes val value = negativeAttributesListTypedArray.getResourceId(l, 0)
                    (groupData.getItemDataAt(j) as CodexEquipmentItem).addNegativeAttribute(
                        value
                    )
                }
                negativeAttributesListTypedArray.recycle()
                negativeAttributesTypedArray.recycle()
            }
            attributesTypedArray.recycle()

            val upgradeLevelTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(upgradeLevelKey, 0))
            for (j in 0 until upgradeLevelTypedArray.length()) {
                @IntegerRes val value = upgradeLevelTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as CodexEquipmentItem).setUpgradeLevel(value)
            }
            upgradeLevelTypedArray.recycle()

            val upgradeCostTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(upgradeCostKey, 0))
            for (j in 0 until upgradeCostTypedArray.length()) {
                @IntegerRes val value = upgradeCostTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as CodexEquipmentItem).upgradeCostData = value
            }
            upgradeCostTypedArray.recycle()

            shopTypedArray.recycle()

            equipment.addGroup(groupData)
        }
        shopListTypedArray.recycle()

        return Result.success(equipment)
    }

}



