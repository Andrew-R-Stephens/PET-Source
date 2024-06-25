package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.ProgressBar
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups.InvestigationPopupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel
import com.google.android.gms.ads.AdRequest

abstract class InvestigationListView : LinearLayout {

    protected var globalPreferencesViewModel: GlobalPreferencesViewModel? = null
    protected var evidenceViewModel: EvidenceViewModel? = null

    protected var popupData: InvestigationPopupModel? = null

    protected var popupWindow: PopupWindow? = null
    private var progressBar: ProgressBar? = null

    protected var adRequest: AdRequest? = null

    constructor(context: Context?) :
            super(context)

    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1f
        )
        setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
        layoutTransition = LayoutTransition()
        clipToPadding = false
        gravity = Gravity.CENTER
        orientation = VERTICAL
    }

    protected open fun init(
        globalPreferencesViewModel: GlobalPreferencesViewModel?,
        evidenceViewModel: EvidenceViewModel?,
        popupWindow: PopupWindow?, progressBar: ProgressBar?, adRequest: AdRequest?
    ) {
        this.globalPreferencesViewModel = globalPreferencesViewModel
        this.evidenceViewModel = evidenceViewModel
        this.adRequest = adRequest

        init(popupWindow, progressBar)
    }

    fun init(popupWindow: PopupWindow?, progressBar: ProgressBar?) {
        this.popupWindow = popupWindow
        this.progressBar = progressBar
    }

    @SuppressLint("ClickableViewAccessibility")
    fun createViews() {
        (context as Activity).runOnUiThread {
            buildViews()
            post { progressBar?.let { haltProgressAnimation(it) } }
        }
    }

    private fun haltProgressAnimation(progressBar: ProgressBar) {
        progressBar.animate().alpha(0f).setDuration(250).setListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    progressBar.visibility = GONE
                    super.onAnimationEnd(animation)
                }
            }).start()
    }

    protected fun createPopupWindow(
        popupWindow: PopupWindow?,
        popupData: InvestigationPopupModel?
    ) {
        this.popupData = popupData
        this.popupWindow = popupWindow
    }

    protected abstract fun buildViews()
}
