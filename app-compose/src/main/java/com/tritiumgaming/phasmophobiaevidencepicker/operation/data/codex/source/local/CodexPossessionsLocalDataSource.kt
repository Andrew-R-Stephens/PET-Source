package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexPossessionItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexPossessionsGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.source.CodexDataSource

class CodexPossessionsLocalDataSource: CodexDataSource {

    override fun fetchItems(context: Context): CodexGroups {
        val possessions = CodexGroups()

        val shopListTypedArray =
            context.resources.obtainTypedArray(R.array.shop_cursedpossessions_array)

        val nameKey = 0
        val iconKey = 1
        val imagesKey = 2
        val flavorTextKey = 3
        val infoTextKey = 4
        val attributesTextKey = 5
        val sanityDrainKey = 6
        val drawChanceKey = 7
        val altNameKey = 8

        for (i in 0 until shopListTypedArray.length()) {
            @StringRes var possessionName: Int
            @DrawableRes var possessionIcon: Int

            val groupData = CodexPossessionsGroup()

            val shopTypedArray =
                context.resources.obtainTypedArray(shopListTypedArray.getResourceId(i, 0))

            possessionName = shopTypedArray.getResourceId(nameKey, 0)
            possessionIcon = shopTypedArray.getResourceId(iconKey, 0)

            groupData.nameData = possessionName
            groupData.paginationIcon = possessionIcon

            val possessionImagesTypedArray =
                context.resources.obtainTypedArray(shopTypedArray.getResourceId(imagesKey, 0))
            for (j in 0 until possessionImagesTypedArray.length()) {
                groupData.addItem(CodexPossessionItem())
                @DrawableRes val value = possessionImagesTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).imageData = value
            }
            possessionImagesTypedArray.recycle()

            val flavorTextTypedArray =
                context.resources.obtainTypedArray(shopTypedArray.getResourceId(flavorTextKey, 0))
            for (j in 0 until flavorTextTypedArray.length()) {
                @StringRes val value = flavorTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).flavorData = value
            }
            flavorTextTypedArray.recycle()

            val infoTextTypedArray =
                context.resources.obtainTypedArray(shopTypedArray.getResourceId(infoTextKey, 0))
            for (j in 0 until infoTextTypedArray.length()) {
                @StringRes val value = infoTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).infoData = value
            }
            infoTextTypedArray.recycle()

            val attributesTypedArray =
                context.resources.obtainTypedArray(
                    shopTypedArray.getResourceId(
                        attributesTextKey,
                        0
                    )
                )
            for (j in 0 until attributesTypedArray.length()) {
                @StringRes val value = attributesTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as CodexPossessionItem).addAttribute(value)
            }
            attributesTypedArray.recycle()

            val sanityDrainTypedArray =
                context.resources.obtainTypedArray(shopTypedArray.getResourceId(sanityDrainKey, 0))
            for (j in 0 until sanityDrainTypedArray.length()) {
                @StringRes val value = sanityDrainTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as CodexPossessionItem).sanityDrainData = value
            }
            sanityDrainTypedArray.recycle()

            val drawChanceTypedArray =
                context.resources.obtainTypedArray(shopTypedArray.getResourceId(drawChanceKey, 0))
            for (j in 0 until drawChanceTypedArray.length()) {
                @StringRes val value = drawChanceTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as CodexPossessionItem).drawChance = value
            }
            drawChanceTypedArray.recycle()

            val altNameTypedArray =
                context.resources.obtainTypedArray(shopTypedArray.getResourceId(altNameKey, 0))
            for (j in 0 until altNameTypedArray.length()) {
                @StringRes val value = altNameTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as CodexPossessionItem).altName = value
            }
            altNameTypedArray.recycle()

            shopTypedArray.recycle()

            possessions.addGroup(groupData)
        }
        shopListTypedArray.recycle()

        return possessions
    }

}