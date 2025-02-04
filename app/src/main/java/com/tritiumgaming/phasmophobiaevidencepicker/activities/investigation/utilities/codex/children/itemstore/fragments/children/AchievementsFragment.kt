package com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.utilities.codex.children.itemstore.fragments.children

import android.annotation.SuppressLint
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.ViewStub
import android.widget.GridLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.ItemStoreGroupModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.model.codex.itemshop.itemstore.equipment.ItemStoreAchievementGroupModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.model.codex.itemshop.itemstore.equipment.ItemStoreAchievementItemModel

class AchievementsFragment : ItemStoreFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeData = investigationViewModel.achievementsStoreModel
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

    /*override fun saveStates() {}*/
}
