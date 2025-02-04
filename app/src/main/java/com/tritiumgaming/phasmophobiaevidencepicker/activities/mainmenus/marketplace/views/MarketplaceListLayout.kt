package com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.marketplace.views

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.marketplace.views.items.MarketBundleView
import com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.marketplace.views.items.MarketThemeView

class MarketplaceListLayout : LinearLayout {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
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
                is MarketThemeView -> child.validate()
                is MarketBundleView -> child.validate()
            }
        }
    }

    override fun setOrientation(orientation: Int) {
        super.setOrientation(orientation)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

}
