package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items.ThemeBundleCardView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items.ThemeSingleCardView

class MarketplaceListLayout : LinearLayout {
    constructor(context: Context?) : super(context) { initView() }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { initView() }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView() }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { initView() }

    fun initView() {
        inflate(context, R.layout.layout_marketplace_list, this)

        layoutTransition = LayoutTransition()
        orientation = VERTICAL
    }

    fun setLabel(text: String?) {
        val label = findViewById<AppCompatTextView>(R.id.label)
        label?.text = text
    }

    fun showLabel(visibility: Int) {
        val label = findViewById<AppCompatTextView>(R.id.label)
        label?.visibility = visibility
    }

    fun validateChildren() {
        for (i in 0 until childCount) {
            when(val child = getChildAt(i)) {
                is ThemeSingleCardView -> child.validate()
                is ThemeBundleCardView -> child.validate()
            }
        }
    }

    override fun setOrientation(orientation: Int) {
        super.setOrientation(orientation)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

}
