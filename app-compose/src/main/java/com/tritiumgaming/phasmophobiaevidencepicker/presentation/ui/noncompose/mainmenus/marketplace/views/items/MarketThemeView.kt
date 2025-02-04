package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.mainmenus.marketplace.views.items

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MarketPaletteEntity

@Deprecated("Migrate to Jetpack Compose")
class MarketThemeView : MarketItemView {

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    var themeModel: MarketPaletteEntity? = null
        set(value) {
            field = value

            setPurchasable()
            setCreditCost()
        }

    override val creditCost: Long
        get() = themeModel?.buyCredits ?: 0

    init {
        inflate(context, R.layout.item_marketplace_theme, this)
        setBuyButtonListener()

        setPurchasable()
    }

    private fun setPurchasable() {
        themeModel?.let { theme ->
            if (theme.isUnlocked) {
                //visibility = GONE
                val buyButton = findViewById<View?>(R.id.button_transactItem)
                buyButton?.isEnabled = false
                buyButton?.alpha = .25f

                val creditsIcon = findViewById<AppCompatImageView>(R.id.creditsIcon)
                creditsIcon?.alpha = .25f

                val creditsTextView = findViewById<AppCompatTextView>(R.id.label_credits_cost)
                //creditsTextView?.text = context.getString(R.string.marketplace_label_purchased)
                creditsTextView?.alpha = .25f
            }
        }
    }

    private fun setCreditCost() {
        val creditsLabel = findViewById<AppCompatTextView>(R.id.label_credits_cost)

        themeModel?.let { theme -> creditsLabel?.text = theme.buyCredits.toString() }
    }

    override fun validate() { setPurchasable() }

}
