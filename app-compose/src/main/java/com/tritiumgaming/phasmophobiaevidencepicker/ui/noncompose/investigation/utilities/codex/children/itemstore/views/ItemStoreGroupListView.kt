package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.utilities.codex.children.itemstore.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.ItemStoreGroupModel

class ItemStoreGroupListView : LinearLayoutCompat {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private fun initView(@LayoutRes layoutRes: Int) {
        if (layoutRes == UNSPECIFIED_LAYOUT)
            inflate(context, R.layout.item_itemstore_itemgroup, this)
        else { inflate(context, layoutRes, this) }
    }

    @JvmOverloads
    fun build(@DrawableRes containerSrc: Int, group: ItemStoreGroupModel, hasTier: Boolean = false) {
        initView(
            if (group.size > 3) R.layout.item_itemstore_itemgroup_long
            else R.layout.item_itemstore_itemgroup)

        var groupList = findViewById<ViewGroup>(R.id.groupList)

        if (group.size > groupList.childCount) {
            for (i in 0 until group.size - groupList.childCount) {
                groupList.addView(
                    inflate(context, R.layout.item_itemstore_scrollview_image, null)
                )
            }
        }
        groupList = findViewById(R.id.groupList)

        var i = 0
        while ((i < groupList.childCount) && (i < group.size)) {
            val item = groupList.getChildAt(i) as ItemStoreItemView
            item.setImageResource(containerSrc)
            item.isSelected = false
            item.setTier(if (hasTier) i + 1 else 0)
            item.setEquipment(group.itemImages[i])
            i++
        }
        while (i < groupList.childCount) {
            val v = groupList.getChildAt(i)
            v.visibility = GONE
            i++
        }

        val nameTextView = this.findViewById<AppCompatTextView>(R.id.safehouse_shop_tool_label)
        val title = resources.getString(group.nameData)
        nameTextView.text = title
        nameTextView.isSelected = true

        this.visibility = INVISIBLE
        this.alpha = 0f
    }

    val items: Array<ItemStoreItemView?>
        get() {
            val layout = getChildAt(0) as LinearLayoutCompat
            val container = layout.findViewById<ViewGroup>(R.id.groupList)

            val items = arrayOfNulls<ItemStoreItemView>(container.childCount)

            for (i in 0 until container.childCount) {
                items[i] = container.getChildAt(i) as ItemStoreItemView
            }

            return items
        }

    companion object {
        const val UNSPECIFIED_LAYOUT: Int = -1
    }
}
