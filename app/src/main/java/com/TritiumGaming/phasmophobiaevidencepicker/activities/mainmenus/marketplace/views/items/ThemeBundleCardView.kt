package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.bundle.MarketThemeBundleModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.interpolate
import com.google.android.material.card.MaterialCardView

class ThemeBundleCardView : MaterialCardView {

    var bundle: MarketThemeBundleModel? = null
        set(value) {
            field = value

            field?.let { bundle ->
                val titleView = findViewById<AppCompatTextView>(R.id.label_bundleTitle)
                titleView?.text = bundle.name

                val costView = findViewById<AppCompatTextView>(R.id.label_credits_cost)
                costView?.text = bundle.discountedBuyCredits.toString()

                buildThemes()
            }
        }

    val creditCost: Long
        get() = bundle?.discountedBuyCredits ?: 0


    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.item_marketplace_bundle, this)

        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val strokeColor =
            getColorFromAttribute(context, R.attr.backgroundColorOnBackground)

        radius = 16f

        setStrokeColor(interpolate(resources.getColor(R.color.white), strokeColor, .25f))
        strokeWidth = 3

        useCompatPadding = true
        clipToPadding = false
    }

    private fun buildThemes() {
        val themesList = findViewById<LinearLayout>(R.id.themesList)

        bundle?.themes?.let { themes ->
            for (theme in themes) {
                val card = ThemeBundleImageView(
                    ContextThemeWrapper(context, theme.style), null, theme.style)
                card.setTheme(theme)

                card.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1f)

                themesList.addView(card)
            }
        }
    }

    fun validate() {
        if (validateVisibility()) {
            validateThemesList()
        }
    }

    private fun validateThemesList() {
        val themesList = findViewById<LinearLayout>(R.id.themesList)
        bundle?.let {
            for (i in 0 until themesList.childCount) {
                val child = themesList.getChildAt(i)
                if (child is ThemeBundleImageView) { child.validate() }
            }

            setCreditCost()
        }
    }

    private fun validateVisibility(): Boolean {
        val isAvailable = bundle?.let { it.lockedItemCount > 1 } ?: false

        visibility = if (isAvailable) VISIBLE else GONE

        return isAvailable
    }

    private fun setCreditCost() {
        val creditsTextView = findViewById<AppCompatTextView>(R.id.label_credits_cost)

        bundle?.let { bundle -> creditsTextView.text = bundle.discountedBuyCredits.toString() }
    }

    fun setBuyButtonListener(buyButtonListener: OnClickListener?) {
        val buyButton = findViewById<View>(R.id.button_transactItem)

        buyButton?.setOnClickListener(buyButtonListener)
    }
}
