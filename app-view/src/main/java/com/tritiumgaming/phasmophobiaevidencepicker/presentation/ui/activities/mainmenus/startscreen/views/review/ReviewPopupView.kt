package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.startscreen.views.review

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.tritiumgaming.phasmophobiaevidencepicker.R

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

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setBackgroundColor(resources.getColor(R.color.black_A75))

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

