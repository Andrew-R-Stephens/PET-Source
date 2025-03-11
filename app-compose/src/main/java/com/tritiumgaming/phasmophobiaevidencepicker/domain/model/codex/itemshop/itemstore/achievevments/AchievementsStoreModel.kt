package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.achievevments

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreListModel

class AchievementsStoreModel : ItemStoreListModel() {

    fun init(context: Context) {
        val shopListTypedArray = context.resources.obtainTypedArray(R.array.shop_achievements_array)

        val nameKey = 0
        val infoKey = 1
        val iconKey = 2

        for (i in 0 until shopListTypedArray.length()) {
            @StringRes var achievementsName: Int
            @StringRes var achievementsInfo: Int
            @DrawableRes var achievementsIcon: Int

            val groupData = ItemStoreAchievementGroupModel()

            val shopTypedArray =
                context.resources.obtainTypedArray(shopListTypedArray.getResourceId(i, 0))


            achievementsName = shopTypedArray.getResourceId(nameKey, 0)
            achievementsInfo = shopTypedArray.getResourceId(infoKey, 0)
            achievementsIcon = shopTypedArray.getResourceId(iconKey, 0)

            val achievementItemModel = ItemStoreAchievementItemModel()

            groupData.nameData = achievementsName
            groupData.paginationIcon = achievementsIcon
            achievementItemModel.infoData = achievementsInfo

            groupData.addItem(achievementItemModel)
            groupData.paginationIcon = achievementsIcon

            addGroup(groupData)

            shopTypedArray.recycle()
        }
        shopListTypedArray.recycle()
    }

}
