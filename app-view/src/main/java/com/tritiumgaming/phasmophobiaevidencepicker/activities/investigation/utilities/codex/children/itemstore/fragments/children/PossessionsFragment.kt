package com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.children

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.LayerDrawable
import android.text.Html
import android.view.View
import android.view.ViewStub
import android.widget.GridLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.fragments.ItemStoreFragment
import com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreGroupListView
import com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.children.itemstore.views.ItemStoreItemView
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.codex.itemshop.itemstore.ItemStoreGroupModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.codex.itemshop.itemstore.possessions.ItemStorePossessionItemModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.codex.itemshop.itemstore.possessions.ItemStorePossnsGroupModel

class PossessionsFragment : ItemStoreFragment() {
    @SuppressLint("ResourceType")
    override fun buildStoreData() {
        val shopListTypedArray = resources.obtainTypedArray(R.array.shop_cursedpossessions_array)

        for (i in 0 until shopListTypedArray.length()) {
            @StringRes var possessionName: Int
            @DrawableRes var possessionIcon: Int

            val groupData = ItemStorePossnsGroupModel()

            val shopTypedArray =
                resources.obtainTypedArray(shopListTypedArray.getResourceId(i, 0))

            possessionName = shopTypedArray.getResourceId(0, 0)
            possessionIcon = shopTypedArray.getResourceId(1, 0)

            groupData.nameData = possessionName
            groupData.paginationIcon = possessionIcon

            val possessionImagesTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(2, 0))
            for (j in 0 until possessionImagesTypedArray.length()) {
                groupData.addItem(ItemStorePossessionItemModel())
                @DrawableRes val value = possessionImagesTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).imageData = value

                //tierImages.add(value);
                groupData.getItemDataAt(j).imageData = value
            }
            possessionImagesTypedArray.recycle()

            val flavorTextTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(3, 0))
            for (j in 0 until flavorTextTypedArray.length()) {
                @StringRes val value = flavorTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).flavorData = value
            }
            flavorTextTypedArray.recycle()

            val infoTextTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(4, 0))
            for (j in 0 until infoTextTypedArray.length()) {
                @StringRes val value = infoTextTypedArray.getResourceId(j, 0)
                groupData.getItemDataAt(j).infoData = value
            }
            infoTextTypedArray.recycle()

            val attributesTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(5, 0))
            for (j in 0 until attributesTypedArray.length()) {
                @StringRes val value = attributesTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStorePossessionItemModel).addAttribute(value)
            }
            attributesTypedArray.recycle()

            val sanityDrainTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(6, 0))
            for (j in 0 until sanityDrainTypedArray.length()) {
                @StringRes val value = sanityDrainTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStorePossessionItemModel).sanityDrainData = value
            }
            sanityDrainTypedArray.recycle()

            val drawChanceTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(7, 0))
            for (j in 0 until drawChanceTypedArray.length()) {
                @StringRes val value = drawChanceTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStorePossessionItemModel).drawChance = value
            }
            drawChanceTypedArray.recycle()

            val altNameTypedArray =
                resources.obtainTypedArray(shopTypedArray.getResourceId(8, 0))
            for (j in 0 until altNameTypedArray.length()) {
                @StringRes val value = altNameTypedArray.getResourceId(j, 0)
                (groupData.getItemDataAt(j) as ItemStorePossessionItemModel).altName = value
            }
            altNameTypedArray.recycle()

            shopTypedArray.recycle()

            storeData.addGroup(groupData)
        }
        shopListTypedArray.recycle()
    }

    override fun setPageTitle(titleView: AppCompatTextView) {
        titleView.setText(R.string.store_title_cursedpossessions)
    }

    override fun setDataViewLayout(view: View) {
        val dv = view.findViewById<ViewStub>(R.id.item_safehouse_itemstore_itemData)
        dv.layoutResource = R.layout.layout_itemstore_itemdata_possessions
        dataView = dv.inflate()
        dataView?.visibility = View.INVISIBLE
    }

    override fun createGroup(parent: LinearLayoutCompat, group: ItemStoreGroupModel) {
        val itemStoreGroupList = ItemStoreGroupListView(requireContext())
        itemStoreGroupList.build(R.drawable.equipment_possession_item, group)
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
        val groupData = storeData.getGroupAt(groupIndex) as ItemStorePossnsGroupModel
        val itemData = groupData.getItemDataAt(itemIndex) as ItemStorePossessionItemModel

        val itemNameView = dataView.findViewById<AppCompatTextView>(R.id.safehouse_shop_tool_label)
        val flavorTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_flavor)
        val infoTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_info)
        val sanityDrainTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_sanitydrain)
        val drawChanceTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_drawchance)
        val altNameTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_altname)
        val attrTextView = dataView.findViewById<AppCompatTextView>(R.id.textview_itemshop_attributes)
        val itemImageView = dataView.findViewById<ItemStoreItemView>(R.id.itemStoreEquipmentItemData)

        itemNameView.text = getString(groupData.nameData)
        flavorTextView.text = Html.fromHtml(getString(itemData.flavorData))
        infoTextView.text = Html.fromHtml(getString(itemData.infoData))

        try { sanityDrainTextView.text = Html.fromHtml(getString(itemData.sanityDrainData)) }
        catch (e: Resources.NotFoundException) { e.printStackTrace() }

        try { drawChanceTextView.text = Html.fromHtml(getString(itemData.drawChance)) }
        catch (e: Resources.NotFoundException) { e.printStackTrace() }

        try {
            altNameTextView.visibility = View.VISIBLE
            altNameTextView.text = Html.fromHtml(getString(itemData.altName))
        } catch (e: Resources.NotFoundException) {
            altNameTextView.visibility = View.GONE
            e.printStackTrace()
        }

        try {
            val attrData = itemData.getAllAttributesAsFormattedHTML(requireContext())
            if (attrData.isNotEmpty()) { attrTextView.visibility = View.GONE }
            else {
                attrTextView.visibility = View.VISIBLE
                attrTextView.text = attrData
            }
        } catch (e: Resources.NotFoundException) {
            attrTextView.visibility = View.GONE
            e.printStackTrace()
        }

        attrTextView.text = Html.fromHtml(itemData.getAllAttributesAsFormattedHTML(requireContext()))

        val layerDrawable = itemImageView.drawable as LayerDrawable
        layerDrawable.setDrawableByLayerId(R.id.ic_type,
            ResourcesCompat.getDrawable(resources, itemData.imageData, requireContext().theme))
        layerDrawable.setLevel(0)
        itemImageView.invalidate()
    }

    override fun reset() {
    }

    override fun saveStates() {
    }
}
