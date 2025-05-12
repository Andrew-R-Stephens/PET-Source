package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.sections.lists

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.ProgressBar
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel

@Deprecated("Removed in favor of GhostListColumn() and EvidenceListColumn() in Journal",
    ReplaceWith("Journal"), DeprecationLevel.WARNING)
abstract class InvestigationListView : LinearLayout {

    protected var globalPreferencesViewModel: GlobalPreferencesViewModel? = null
    protected var investigationViewModel: InvestigationViewModel? = null

    protected var popupWindow: PopupWindow? = null
    private var progressBar: ProgressBar? = null

    protected var adRequest: AdRequest? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f)

        this.setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
        layoutTransition = LayoutTransition()
        clipToPadding = false
        gravity = Gravity.CENTER
        orientation = VERTICAL
    }

    protected open fun init(
        globalPreferencesViewModel: GlobalPreferencesViewModel,
        investigationViewModel: InvestigationViewModel,
        popupWindow: PopupWindow?,
        progressBar: ProgressBar?,
        adRequest: AdRequest?
    ) {
        this.globalPreferencesViewModel = globalPreferencesViewModel
        this.investigationViewModel = investigationViewModel
        this.adRequest = adRequest

        this.popupWindow = popupWindow
        this.progressBar = progressBar
    }

    fun createViews() {
        (context as Activity).runOnUiThread {
            build()
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

    fun createPopupWindow(popupWindow: PopupWindow?) {
        this.popupWindow = popupWindow
    }

    protected abstract fun build()
}
