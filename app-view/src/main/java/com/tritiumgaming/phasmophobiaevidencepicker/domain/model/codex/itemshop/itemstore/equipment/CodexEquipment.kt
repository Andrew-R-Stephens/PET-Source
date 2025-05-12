package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.equipment

import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreGroups


class CodexEquipment(context: Context): ItemStoreGroups() {

    init {
        val shopListTypedArray = context.resources.obtainTypedArray(R.array.shop_equipment_array)

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

            val groupData = ItemStoreEquipmentGroupModel()

            val shopTypedArray = context.resources
                .obtainTypedArray(shopListTypedArray.getResourceId(i, 0))

            equipmentName = shopTypedArray.getResourceId(nameKey, 0)
            equipmentIcon = shopTypedArray.getResourceId(iconKey, 0)
            buyCostData = shopTypedArray.getResourceId(buyCostKey, 0)

            groupData.nameData = equipmentName
            groupData.paginationIcon = equipmentIcon
            groupData.buyCostData = buyCostData

            val iconsTypedArray = context.resources
                .obtainTypedArray(shopTypedArray.getResourceId(imagesKey, 0))
            Log.d("ItemStore", "Icons Count: ${iconsTypedArray.length()}")
            for (j in 0 until iconsTypedArray.length()) {
                val itemData = ItemStoreEquipmentItemModel()

                @DrawableRes val iconRes = iconsTypedArray.getResourceId(j, 0)
                itemData.imageData = iconRes

                groupData.addItem(itemData)
            }
            iconsTypedArray.recycle()

            val flavorTextTypedArray = context.resources
                .obtainTypedArray(shopTypedArray.getResourceId(flavorTextKey, 0))
            for (j in 0 until flavorTextTypedArray.length()) {
                @StringRes val value = flavorTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).flavorData = value
            }
            flavorTextTypedArray.recycle()

            val infoTextTypedArray = context.resources
                .obtainTypedArray(shopTypedArray.getResourceId(infoTextKey, 0))
            for (j in 0 until infoTextTypedArray.length()) {
                @StringRes val value = infoTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).infoData = value
            }
            infoTextTypedArray.recycle()

            val attributesTypedArray = context.resources
                .obtainTypedArray(shopTypedArray.getResourceId(attributesTextKey, 0))
            for (j in 0 until attributesTypedArray.length()) {
                val positiveAttributesTypesArray = context.resources
                    .obtainTypedArray(attributesTypedArray.getResourceId(j, 0))
                val positiveAttributesListTypedArray = context.resources
                    .obtainTypedArray(
                        positiveAttributesTypesArray.getResourceId(
                            positiveAttrsKey,
                            0
                        )
                    )
                for (l in 0 until positiveAttributesListTypedArray.length()) {
                    @StringRes val value = positiveAttributesListTypedArray.getResourceId(l, 0)
                    (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).addPositiveAttribute(
                        value
                    )
                }
                positiveAttributesListTypedArray.recycle()
                positiveAttributesTypesArray.recycle()

                val negativeAttributesTypedArray = context.resources
                    .obtainTypedArray(attributesTypedArray.getResourceId(j, 0))
                val negativeAttributesListTypedArray = context.resources
                    .obtainTypedArray(
                        negativeAttributesTypedArray.getResourceId(
                            negativeAttrsKey,
                            0
                        )
                    )
                for (l in 0 until negativeAttributesListTypedArray.length()) {
                    @StringRes val value = negativeAttributesListTypedArray.getResourceId(l, 0)
                    (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).addNegativeAttribute(
                        value
                    )
                }
                negativeAttributesListTypedArray.recycle()
                negativeAttributesTypedArray.recycle()
            }
            attributesTypedArray.recycle()

            val upgradeLevelTypedArray = context.resources
                .obtainTypedArray(shopTypedArray.getResourceId(upgradeLevelKey, 0))
            for (j in 0 until upgradeLevelTypedArray.length()) {
                @IntegerRes val value = upgradeLevelTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).setUpgradeLevel(value)
            }
            upgradeLevelTypedArray.recycle()

            val upgradeCostTypedArray = context.resources
                .obtainTypedArray(shopTypedArray.getResourceId(upgradeCostKey, 0))
            for (j in 0 until upgradeCostTypedArray.length()) {
                @IntegerRes val value = upgradeCostTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).upgradeCostData = value
            }
            upgradeCostTypedArray.recycle()

            shopTypedArray.recycle()

            addGroup(groupData)
        }
        shopListTypedArray.recycle()
    }

}