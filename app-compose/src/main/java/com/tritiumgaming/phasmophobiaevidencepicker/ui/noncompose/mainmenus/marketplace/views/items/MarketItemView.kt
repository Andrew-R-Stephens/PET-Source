package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus.marketplace.views.items

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.interpolate

abstract class MarketItemView : MaterialCardView {

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    abstract val creditCost: Long

    init {
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val strokeColor =
            getColorFromAttribute(context, R.attr.backgroundColorOnBackground)

        radius = 16f

        super.setStrokeColor(interpolate(resources.getColor(R.color.white), strokeColor, .25f))
        strokeWidth = 3

        useCompatPadding = true
        clipToPadding = false
    }

    protected fun setBuyButtonListener() {
        val buyButton = findViewById<View>(R.id.button_transactItem)
        buyButton?.setOnClickListener {
            onPurchaseListener?.onPurchase()
        }
    }

    abstract fun validate()

    var onPurchaseListener: MarketItemOnPurchaseListener? = null
    abstract class MarketItemOnPurchaseListener {
        abstract fun onPurchase()
    }

}

