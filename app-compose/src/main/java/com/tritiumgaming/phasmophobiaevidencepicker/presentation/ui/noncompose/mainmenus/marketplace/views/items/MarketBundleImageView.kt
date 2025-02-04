package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.mainmenus.marketplace.views.items

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.card.MaterialCardView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.ThemeModel


@Deprecated("Migrate to Jetpack Compose")
class MarketBundleImageView : MaterialCardView {

    private var theme: ThemeModel? = null

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.item_marketplace_bundle_image, this)

        setCardBackgroundColor(resources.getColor(R.color.transparent))
        elevation = 0f

        foregroundGravity = Gravity.CENTER

        clipToPadding = false
        isSelected = true
    }

    fun setTheme(theme: ThemeModel?) {
        this.theme = theme

        setObtainedIconVisibility()
    }

    private fun setObtainedIconVisibility() {
        val lockedState = findViewById<AppCompatImageView>(R.id.image_obtained)

        theme?.let { theme -> lockedState?.visibility = if (theme.isUnlocked) VISIBLE else GONE }
    }

    fun validate() {
        setObtainedIconVisibility()
    }
}