package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus.startscreen.views.review

import android.view.Gravity
import android.view.View
import android.widget.PopupWindow

class ReviewPopupWindow(parentView: View, width: Int, height: Int, popupView: ReviewPopupView =
    ReviewPopupView(parentView.context)) :
    PopupWindow(popupView, width, height) {

    abstract class ReviewPopupListener {
        abstract fun onCreate()
        abstract fun onDestroy()
        abstract fun onAccept()
        abstract fun onDecline()
    }
    var reviewPopupWindowListener: ReviewPopupListener? = null

    init {
        popupView.reviewPopupListener = object: ReviewPopupListener() {
            override fun onCreate() { }
            override fun onDestroy() { }
            override fun onAccept() { reviewPopupWindowListener?.onAccept() }
            override fun onDecline() { reviewPopupWindowListener?.onDecline() }
        }

        isOutsideTouchable = false
        animationStyle = androidx.navigation.ui.R.anim.nav_default_enter_anim
        parentView.post {
            showAtLocation(popupView, Gravity.CENTER_VERTICAL, 0, 0)
            reviewPopupWindowListener?.onCreate() }
    }

}