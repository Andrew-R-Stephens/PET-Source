package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.account

import android.content.Context
import android.content.res.ColorStateList
import android.text.Html
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.intToHex
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.setColor

class AccountObtainCreditsView : ConstraintLayout {

    constructor(context: Context) :
            super(context) { init(getContext()) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { init(getContext()) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { init(getContext()) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { init(getContext()) }

    fun init(context: Context?) {
        inflate(context, R.layout.layout_account_obtain_credits, this)

        findViewById<AppCompatButton>(R.id.button_ad_watch)?.let { b ->
            buildButtonStateColors(b)
        }
        findViewById<AppCompatButton>(R.id.settings_account_buy_button)?.let { b ->
            buildButtonStateColors(b)
        }
        setWatchAdsLabelDescription()

        var user: FirebaseUser? = null
        try { user = currentFirebaseUser }
        catch (e: Exception) { e.printStackTrace() }

        setContainerVisibility(user)
    }

    private fun setContainerVisibility(user: FirebaseUser?) {
        visibility = if (user == null) { GONE } else { VISIBLE }
    }

    private fun setWatchAdsLabelDescription() {
        setWatchAdsLabelDescription(-1)
    }

    fun setWatchAdsLabelDescription(quantity: Int) {
        val descriptionLabel =
            findViewById<AppCompatTextView>(R.id.label_ads_description) ?: return

        @ColorInt val color =
            getColorFromAttribute(context, R.attr.textColorPrimary)

        val descriptionQuantity = resources.getQuantityString(
            R.plurals.marketplace_description_watch_ad,
            quantity, quantity
        )
        val colorHex = intToHex(color)

        val quantityStrTemp = StringBuilder("<font color=")
            .append(colorHex).append(">")
        //String quantityStr = "";
        if (quantity >= 0) {
            quantityStrTemp.append(quantity)
        }
        quantityStrTemp.append("</font>")

        val descriptionFormat = context.getString(
            R.string.marketplace_description_watch_ad,
            quantityStrTemp.toString(), descriptionQuantity
        )
        descriptionLabel.text = Html.fromHtml(descriptionFormat)
    }

    private fun buildButtonStateColors(button: AppCompatButton) {

        var colorStateList = button.textColors
        @ColorInt val defaultColor = colorStateList.defaultColor
        @ColorInt val disabledColor = setColor(defaultColor, 50, -1, -1, -1)

        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        )
        val colors = intArrayOf(defaultColor, disabledColor)
        colorStateList = ColorStateList(states, colors)

        button.setTextColor(colorStateList)
    }

    fun enableAdWatchButton(enable: Boolean) {
        val button = findViewById<AppCompatButton>(R.id.button_ad_watch) ?: return

        button.isEnabled = enable
    }
}
