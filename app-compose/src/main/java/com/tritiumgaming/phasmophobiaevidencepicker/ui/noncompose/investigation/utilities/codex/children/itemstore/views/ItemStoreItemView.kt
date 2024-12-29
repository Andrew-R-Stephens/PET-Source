package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.utilities.codex.children.itemstore.views

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.tritiumgaming.phasmophobiaevidencepicker.R

class ItemStoreItemView : AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private var isSelected = false

    fun setTier(tier: Int) {
        setImageLevel(tier)
    }

    fun setEquipment(@DrawableRes res: Int) {
        if(drawable is LayerDrawable) {
            val layerDrawable = drawable as LayerDrawable
            layerDrawable.setDrawableByLayerId(
                R.id.ic_type, ResourcesCompat.getDrawable(
                    resources, res, context.theme
                )
            )
        }
    }

    override fun setSelected(isSelected: Boolean) {
        this.isSelected = isSelected

        setImageState(
            intArrayOf(if (isSelected) R.attr.state_selected else -R.attr.state_selected),
            true
        )
    }

    override fun isSelected(): Boolean {
        return isSelected
    }
}