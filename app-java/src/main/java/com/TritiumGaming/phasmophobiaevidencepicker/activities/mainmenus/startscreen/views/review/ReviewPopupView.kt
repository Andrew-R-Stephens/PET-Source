package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views.review

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R

class ReviewPopupView: ConstraintLayout {

    constructor(context: Context) :
            super(context) { initView() }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { initView() }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { initView() }

    var reviewPopupListener: ReviewPopupWindow.ReviewPopupListener? = null

    private fun initView() {
        inflate(context, R.layout.popup_requestreview, this)

        val acceptButton = findViewById<AppCompatButton>(R.id.label_accept)
        val declineButton = findViewById<AppCompatButton>(R.id.label_decline)

        // LISTENERS
        acceptButton.setOnClickListener {
            reviewPopupListener?.onAccept()
        }

        declineButton.setOnClickListener {
            reviewPopupListener?.onDecline()
        }
    }
}

