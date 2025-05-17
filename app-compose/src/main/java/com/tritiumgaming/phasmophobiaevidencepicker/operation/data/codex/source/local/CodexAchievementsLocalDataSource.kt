package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexAchievementGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexAchievementItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.source.CodexDataSource

class CodexAchievementsLocalDataSource: CodexDataSource {

    override fun fetchItems(context: Context): CodexGroups {
        val achievements = CodexGroups()

        val shopListTypedArray = context.resources.obtainTypedArray(R.array.shop_achievements_array)

        val nameKey = 0
        val infoKey = 1
        val iconKey = 2

        for (i in 0 until shopListTypedArray.length()) {
            @StringRes var achievementsName: Int
            @StringRes var achievementsInfo: Int
            @DrawableRes var achievementsIcon: Int

            val shopTypedArray =
                context.resources.obtainTypedArray(shopListTypedArray.getResourceId(i, 0))

            achievementsName = shopTypedArray.getResourceId(nameKey, 0)
            achievementsInfo = shopTypedArray.getResourceId(infoKey, 0)
            achievementsIcon = shopTypedArray.getResourceId(iconKey, 0)

            val achievementItemModel = CodexAchievementItem()

            val groupData = CodexAchievementGroup()

            groupData.nameData = achievementsName
            groupData.paginationIcon = achievementsIcon
            achievementItemModel.infoData = achievementsInfo

            groupData.addItem(achievementItemModel)
            groupData.paginationIcon = achievementsIcon

            achievements.addGroup(groupData)

            shopTypedArray.recycle()
        }

        shopListTypedArray.recycle()

        return achievements
    }


}
