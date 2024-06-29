package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.marketplace.views.items

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.theme.theme.MarketSingleThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.interpolate
import com.google.android.material.card.MaterialCardView

class ThemeSingleCardView : MaterialCardView {
    private var theme: MarketSingleThemeModel? = null

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) { initView(context) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView(context) }

    fun initView(context: Context?) {
        inflate(context, R.layout.item_marketplace_theme, this)

        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val strokeColor =
            getColorFromAttribute(getContext(), R.attr.backgroundColorOnBackground)

        radius = 16f

        setStrokeColor(interpolate(resources.getColor(R.color.white), strokeColor, .25f))
        strokeWidth = 3

        useCompatPadding = true
        clipToPadding = false
    }

    private fun setPurchasable() {
        theme?.let { theme ->
            if (theme.isUnlocked) visibility = GONE
            run {}
        }
    }

    fun setCreditCost() {
        val creditsLabel = findViewById<AppCompatTextView>(R.id.label_credits_cost)

        theme?.let { theme -> creditsLabel?.text = theme.buyCredits.toString() }
    }

    val creditCost: Long
        get() = theme?.buyCredits ?: 0

    fun setTheme(theme: MarketSingleThemeModel?) {
        this.theme = theme

        setPurchasable()
        setCreditCost()
    }

    fun validate() { setPurchasable() }

    fun setBuyButtonListener(buyButtonListener: OnClickListener?) {
        val buyButton = findViewById<View>(R.id.button_transactItem)

        buyButton?.setOnClickListener(buyButtonListener)
    }
}
