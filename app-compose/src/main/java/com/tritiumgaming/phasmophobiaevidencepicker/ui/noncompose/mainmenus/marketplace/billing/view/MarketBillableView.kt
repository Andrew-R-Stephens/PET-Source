package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus.marketplace.billing.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.card.MaterialCardView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.firestore.billable.MarketMicroTransactionModel
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ColorUtils.interpolate

class MarketBillableView : MaterialCardView {

    private var billableItem: MarketMicroTransactionModel? = null

    constructor(context: Context) : this(context, null) { init(context) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { init(context) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init(context) }

    fun init(context: Context?) {
        inflate(context, R.layout.item_marketplace_micro, this)

        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val strokeColor = getColorFromAttribute(getContext(), R.attr.textColorBodyEmphasis)

        radius = 16f

        setStrokeColor(interpolate(resources.getColor(R.color.white), strokeColor, .25f))
        strokeWidth = 3

        useCompatPadding = true
        clipToPadding = false
    }

    fun setBillableItem(billableItem: MarketMicroTransactionModel?) {
        this.billableItem = billableItem

        update()
    }

    private fun update() {
        setBannerLabel()
        setTitle()
        setDescription()
        setCreditCost()
    }

    private fun setBannerLabel() {
        val textView = findViewById<AppCompatTextView>(R.id.label_mtx_title)
        textView.text = billableItem?.name
    }

    private fun setTitle() {
        val textView = findViewById<AppCompatTextView>(R.id.label_mtx_content)
        textView?.text = billableItem?.name
    }

    private fun setDescription() {
        val textView = findViewById<AppCompatTextView>(R.id.label_mtx_description)
        textView?.text = billableItem?.description
    }

    private fun setCreditCost() {
        val textView = findViewById<AppCompatTextView>(R.id.label_monetary_value)
        textView?.text = billableItem?.purchaseAmount
    }

    fun setBuyButtonListener(buyButtonListener: OnClickListener?) {
        val buyButton = findViewById<View>(R.id.button_transactItem)
        buyButton?.setOnClickListener(buyButtonListener)
    }

    fun getBillableItem(): MarketMicroTransactionModel? {
        return billableItem
    }
}
