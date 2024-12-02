package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.children

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
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.ItemStoreGroupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.equipment.ItemStoreEquipmentGroupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.equipment.ItemStoreEquipmentItemModel

class EquipmentFragment : ItemStoreFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeData = investigationViewModel.equipmentStoreModel
    }

    override fun setPageTitle(titleView: AppCompatTextView) {
        titleView.setText(R.string.store_title_equipment)
    }

    override fun setDataViewLayout(view: View) {
        val dv = view.findViewById<ViewStub>(R.id.item_safehouse_itemstore_itemData)
        dv.layoutResource = R.layout.layout_itemstore_itemdata_equipment
        dataView = dv.inflate()
        dataView?.visibility = View.INVISIBLE
    }

    override fun createGroup(parent: LinearLayoutCompat, group: ItemStoreGroupModel) {
        val itemStoreGroup = ItemStoreGroupListView(requireContext())
        itemStoreGroup.build(R.drawable.equipment_tier_item, group, true)
        itemStoreGroup.visibility = View.INVISIBLE
        itemStoreGroup.alpha = 0f
        parent.addView(itemStoreGroup)
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
        val groupData = storeData.getGroupAt(groupIndex) as ItemStoreEquipmentGroupModel
        val itemData = groupData.getItemDataAt(itemIndex) as ItemStoreEquipmentItemModel

        val itemNameTextView = dataView.findViewById<AppCompatTextView>(R.id.safehouse_shop_tool_label)
        val flavorTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_flavor)
        val infoTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_info)
        val attrTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_attributes)
        val buyCostTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemcost)
        val upgradeCostView = dataView.findViewById<AppCompatTextView>(R.id.textview_upgradecost)
        val upgradeLevelView = dataView.findViewById<AppCompatTextView>(R.id.textview_unlocklevel)
        //ComposeView itemImageView = dataView.findViewById(R.id.itemStoreEquipmentItemData);
        val itemImageView =
            dataView.findViewById<ItemStoreItemView>(R.id.itemStoreEquipmentItemData) //.findViewById(R.id.tier_item);

        val buyCost = StringBuilder("$")
        buyCost.append(resources.getInteger(groupData.buyCostData))

        val upgradeCostTemp = resources.getInteger(itemData.upgradeCostData)
        val upgradeCost = StringBuilder()
        if (upgradeCostTemp > 0) { upgradeCost.append("$").append(upgradeCostTemp) }
        else { upgradeCost.append("-") }

        val upgradeLevelTemp = resources.getInteger(itemData.upgradeLevelData)
        val upgradeLevel = StringBuilder()
        upgradeLevel.append(if ((upgradeLevelTemp > 0)) upgradeLevelTemp else "-")

        itemNameTextView.text = getString(groupData.nameData)
        flavorTextView.text = Html.fromHtml(getString(itemData.flavorData))
        infoTextView.text = Html.fromHtml(getString(itemData.infoData))
        buyCostTextView.text = buyCost
        upgradeCostView.text = upgradeCost
        upgradeLevelView.text = upgradeLevel
        attrTextView.text =
            Html.fromHtml(itemData.getAllAttributesAsFormattedHTML(requireContext()))

        val layerDrawable = itemImageView.drawable as LayerDrawable
        layerDrawable.setDrawableByLayerId(
            R.id.ic_type,
            ResourcesCompat.getDrawable(resources, itemData.imageData, requireContext().theme)
        )
        layerDrawable.setLevel(itemIndex + 1)

        itemImageView.invalidate()
    }

    override fun reset() {
    }

    /*override fun saveStates() {}*/
}
