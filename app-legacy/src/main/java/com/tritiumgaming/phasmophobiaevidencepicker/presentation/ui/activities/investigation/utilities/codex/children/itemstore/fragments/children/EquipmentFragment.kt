package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.fragments.children

import android.annotation.SuppressLint
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.view.View
import android.view.ViewStub
import android.widget.GridLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.ItemStoreGroupModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.equipment.ItemStoreEquipmentGroupModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.equipment.ItemStoreEquipmentItemModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView

class EquipmentFragment : ItemStoreFragment() {
    @SuppressLint("ResourceType")
    override fun buildStoreData() {
        val shopListTypedArray = resources.obtainTypedArray(R.array.shop_equipment_array)

        //scrollViewPaginator.setRowCount(typed_shop_list.length());
        for (i in 0 until shopListTypedArray.length()) {
            @StringRes var equipmentName: Int
            @IntegerRes var buyCostData: Int
            @DrawableRes var equipmentIcon: Int

            //@DrawableRes ArrayList<Integer> tierImages = new ArrayList<>();
            val groupData = ItemStoreEquipmentGroupModel()

            val shopTypedArray = requireContext().resources
                .obtainTypedArray(shopListTypedArray.getResourceId(i, 0))

            equipmentName = shopTypedArray.getResourceId(0, 0)
            equipmentIcon = shopTypedArray.getResourceId(1, 0)
            buyCostData = shopTypedArray.getResourceId(6, 0)

            groupData.nameData = equipmentName
            groupData.paginationIcon = equipmentIcon
            groupData.buyCostData = buyCostData

            val iconsTypedArray = requireContext().resources
                    .obtainTypedArray(shopTypedArray.getResourceId(2, 0))
            for (j in 0 until iconsTypedArray.length()) {
                val itemData = ItemStoreEquipmentItemModel()
                groupData.addItem(itemData)
                @DrawableRes val value = iconsTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).imageData = value
            }
            iconsTypedArray.recycle()

            val flavorTextTypedArray = requireContext().resources
                .obtainTypedArray(shopTypedArray.getResourceId(3, 0))
            for (j in 0 until flavorTextTypedArray.length()) {
                @StringRes val value = flavorTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).flavorData = value
            }
            flavorTextTypedArray.recycle()

            val infoTextTypedArray = requireContext().resources
                .obtainTypedArray(shopTypedArray.getResourceId(4, 0))
            for (j in 0 until infoTextTypedArray.length()) {
                @StringRes val value = infoTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).infoData = value
            }
            infoTextTypedArray.recycle()

            val attributesTypedArray = requireContext().resources
                .obtainTypedArray(shopTypedArray.getResourceId(5, 0))
            for (j in 0 until attributesTypedArray.length()) {
                val positiveAttributesTypesArray = requireContext().resources
                    .obtainTypedArray(attributesTypedArray.getResourceId(j, 0))
                val positiveAttributesListTypedArray = requireContext().resources
                    .obtainTypedArray(positiveAttributesTypesArray.getResourceId(0, 0))
                for (l in 0 until positiveAttributesListTypedArray.length()) {
                    @StringRes val value = positiveAttributesListTypedArray.getResourceId(l, 0)
                    (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).addPositiveAttribute(value)
                }
                positiveAttributesListTypedArray.recycle()
                positiveAttributesTypesArray.recycle()

                val negativeAttributesTypedArray = requireContext().resources
                    .obtainTypedArray(attributesTypedArray.getResourceId(j, 0))
                val negativeAttributesListTypedArray = requireContext().resources
                        .obtainTypedArray(negativeAttributesTypedArray.getResourceId(1, 0))
                for (l in 0 until negativeAttributesListTypedArray.length()) {
                    @StringRes val value = negativeAttributesListTypedArray.getResourceId(l, 0)
                    (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).addNegativeAttribute(value)
                }
                negativeAttributesListTypedArray.recycle()
                negativeAttributesTypedArray.recycle()
            }
            attributesTypedArray.recycle()

            val upgradeLevelTypedArray = requireContext().resources
                .obtainTypedArray(shopTypedArray.getResourceId(7, 0))
            for (j in 0 until upgradeLevelTypedArray.length()) {
                @IntegerRes val value = upgradeLevelTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).setUpgradeLevel(value)
            }
            upgradeLevelTypedArray.recycle()

            val upgradeCostTypedArray = requireContext().resources
                .obtainTypedArray(shopTypedArray.getResourceId(8, 0))
            for (j in 0 until upgradeCostTypedArray.length()) {
                @IntegerRes val value = upgradeCostTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStoreEquipmentItemModel).upgradeCostData = value
            }
            upgradeCostTypedArray.recycle()

            shopTypedArray.recycle()

            storeData.addGroup(groupData)
        }
        shopListTypedArray.recycle()
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

    override fun saveStates() {
    }
}
