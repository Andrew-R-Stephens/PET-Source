package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.investigation.evidence.views.investigation.popups

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout

abstract class InvestigationPopupWindow : ConstraintLayout {

    var popupWindow: PopupWindow? = null
        set(value) {
            field = value
            field?.contentView = this
        }

    constructor(context: Context) :
            super(context)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    abstract fun initView()

    fun initView(layoutID: Int) {
        inflate(context, layoutID, this)

        popupWindow = PopupWindow(
            this,
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
    }

    fun fadeOutIndicatorAnimation(
        bodyCons: ConstraintLayout?,
        container: ConstraintLayout?,
        scroller: ScrollView,
        indicator: View
    ) {
        scroller.post {
            if (!scroller.canScrollVertically(1)) {
                indicator.visibility = INVISIBLE
                indicatorFadeAnimation(indicator, 0)
            } else {
                if (container != null) {
                    if (container.layoutParams is LayoutParams) {
                        val lParams = container.layoutParams as LayoutParams
                        Log.d("Scroller", "Should constrain")
                        lParams.constrainedHeight = true
                        container.layoutParams = lParams
                        container.invalidate()

                        if (!scroller.canScrollVertically(1)) {
                            indicator.visibility = INVISIBLE

                            indicatorFadeAnimation(indicator, 0)
                        } else {
                            indicator.visibility = VISIBLE
                            indicator.alpha = 1f
                        }
                    }
                }
            }
            if (bodyCons != null) {
                //initialize info content scroller
                bodyCons.visibility = VISIBLE
            }
        }

        scroller.setOnScrollChangeListener { _: View?, _: Int, _: Int, _: Int, _: Int ->
            if (!scroller.canScrollVertically(1)) {
                indicatorFadeAnimation(
                    indicator, resources.getInteger(
                        android.R.integer.config_longAnimTime
                    )
                )
            }
        }
    }

    fun indicatorFadeAnimation(indicator: View, time: Int) {
        indicator.animate()
            .alpha(0f)
            .setDuration(time.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    indicator.visibility = INVISIBLE
                }
            })
    }
}
