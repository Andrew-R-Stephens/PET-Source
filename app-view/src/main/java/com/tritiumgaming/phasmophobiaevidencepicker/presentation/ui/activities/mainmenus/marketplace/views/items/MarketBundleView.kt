package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.marketplace.views.items

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.dto.theming.bundle.MarketBundleModel

class MarketBundleView : MarketItemView {

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    override val creditCost: Long
        get() = bundle?.discountedBuyCredits ?: 0

    var bundle: MarketBundleModel? = null
        set(value) {
            field = value

            field?.let { bundle ->
                val titleView = findViewById<AppCompatTextView>(R.id.label_bundleTitle)
                titleView?.text = bundle.name

                val costView = findViewById<AppCompatTextView>(R.id.label_credits_cost)
                costView?.text = bundle.discountedBuyCredits.toString()

                buildThemes()

                validate()
            }
        }

    init {
        inflate(context, R.layout.item_marketplace_bundle, this)
        setBuyButtonListener()

    }

    private fun buildThemes() {
        val themesList = findViewById<LinearLayout>(R.id.themesList)

        bundle?.themes?.let { themes ->
            for (theme in themes) {
                val card = MarketBundleImageView(
                    ContextThemeWrapper(context, theme.style), null, theme.style)
                card.setTheme(theme)

                card.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1f)

                themesList.addView(card)
            }
        }
    }

    override fun validate() {
        validateVisibility()
        validateThemesList()
    }

    private fun validateThemesList() {
        val themesList = findViewById<LinearLayout>(R.id.themesList)
        bundle?.let {
            for (i in 0 until themesList.childCount) {
                val child = themesList.getChildAt(i)
                if (child is MarketBundleImageView) { child.validate() }
            }

            setCreditCost()
        }
    }

    private fun validateVisibility(): Boolean {
        val isAvailable = bundle?.let { it.lockedItemCount > 1 } ?: false

        if(!isAvailable) {
            val buyButton = findViewById<View?>(R.id.button_transactItem)
            buyButton?.isEnabled = false
            buyButton?.alpha = .25f

            val creditsIcon = findViewById<AppCompatImageView>(R.id.creditsIcon)
            creditsIcon?.alpha = .25f

            val creditsTextView = findViewById<AppCompatTextView>(R.id.label_credits_cost)
            /*creditsTextView?.text = context.getString(R.string.marketplace_label_purchased)*/
            creditsTextView?.alpha = .25f
        }

        return isAvailable
    }

    private fun setCreditCost() {
        val creditsTextView = findViewById<AppCompatTextView>(R.id.label_credits_cost)

        bundle?.let { bundle -> creditsTextView.text = bundle.discountedBuyCredits.toString() }
    }

}
