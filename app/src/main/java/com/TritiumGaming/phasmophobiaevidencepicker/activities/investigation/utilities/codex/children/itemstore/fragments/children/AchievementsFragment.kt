package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.children

import android.annotation.SuppressLint
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.view.View
import android.view.ViewStub
import android.widget.GridLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.helper.widget.Layer
import androidx.core.content.res.ResourcesCompat
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.codex.itemshop.itemstore.ItemStoreGroupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.codex.itemshop.itemstore.equipment.ItemStoreAchievementGroupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.codex.itemshop.itemstore.equipment.ItemStoreAchievementItemModel

class AchievementsFragment : ItemStoreFragment() {
    @SuppressLint("ResourceType")
    override fun buildStoreData() {
        val shopListTypedArray = resources.obtainTypedArray(R.array.shop_achievements_array)

        for (i in 0 until shopListTypedArray.length()) {
            @StringRes var achievementsName: Int
            @StringRes var achievementsInfo: Int
            @DrawableRes var achievementsIcon: Int

            val groupData = ItemStoreAchievementGroupModel()

            val shopTypedArray =
                resources.obtainTypedArray(shopListTypedArray.getResourceId(i, 0))


            achievementsName = shopTypedArray.getResourceId(0, 0)
            achievementsInfo = shopTypedArray.getResourceId(1, 0)
            achievementsIcon = shopTypedArray.getResourceId(2, 0)


            val achievementItemModel = ItemStoreAchievementItemModel()

            groupData.nameData = achievementsName
            groupData.paginationIcon = achievementsIcon
            achievementItemModel.infoData = achievementsInfo

            groupData.addItem(achievementItemModel)
            groupData.paginationIcon = achievementsIcon

            storeData.addGroup(groupData)

            shopTypedArray.recycle()
        }
        shopListTypedArray.recycle()
    }

    override fun setPageTitle(titleView: AppCompatTextView) {
        titleView.setText(R.string.store_title_achievements)
    }

    override fun setDataViewLayout(view: View) {
        val dv = view.findViewById<ViewStub>(R.id.item_safehouse_itemstore_itemData)
        dv.layoutResource = R.layout.layout_itemstore_itemdata_achievement
        dataView = dv.inflate()
        dataView?.visibility = View.INVISIBLE
    }

    override fun createGroup(parent: LinearLayoutCompat, group: ItemStoreGroupModel) {
        val itemStoreGroupList = ItemStoreGroupListView(requireContext())
        itemStoreGroupList.build(R.drawable.ic_achievement_banshee, group)
        parent.addView(itemStoreGroupList)
    }

    @SuppressLint("ResourceType")
    override fun buildGroupViews(parent: LinearLayoutCompat, scrollViewPaginator: GridLayout) {
        scrollViewPaginator.rowCount = storeData.groups.size

        for (group in storeData.groups) {
            try {
                requireActivity().runOnUiThread {
                    addPaginatorIcon(scrollViewPaginator, group.paginationIcon)
                    createGroup(parent, group)
                }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun buildDataPopupView(dataView: View, groupIndex: Int, itemIndex: Int) {
        val groupData = storeData.getGroupAt(groupIndex) as ItemStoreAchievementGroupModel
        val itemData = groupData.getItemDataAt(itemIndex) as ItemStoreAchievementItemModel

        val itemNameView = dataView.findViewById<AppCompatTextView>(R.id.safehouse_shop_tool_label)
        val infoTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_info)
        val itemImageView = dataView.findViewById<ItemStoreItemView>(R.id.itemStoreAchievementItemData)

        itemNameView.text = getString(groupData.nameData)
        infoTextView.text = Html.fromHtml(getString(itemData.infoData))

        if(itemData.imageData != 0 && itemImageView.drawable is LayerDrawable) {
            val layerDrawable = itemImageView.drawable as LayerDrawable
            layerDrawable.setDrawableByLayerId(
                R.id.ic_type,
                ResourcesCompat.getDrawable(resources, itemData.imageData, requireContext().theme)
            )
            layerDrawable.setLevel(0)
        }
        itemImageView.invalidate()
    }

    override fun reset() {
    }

    override fun saveStates() {
    }
}
