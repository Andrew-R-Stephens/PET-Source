package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.PopupWindow
import android.widget.ProgressBar
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.popups.GhostPopupWindow
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.ghost.GhostView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups.GhostPopupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel
import com.google.android.gms.ads.AdRequest

class GhostListView : InvestigationListView {
    constructor(context: Context?) :
            super(context)

    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    public override fun init(
        globalPreferencesViewModel: GlobalPreferencesViewModel?,
        evidenceViewModel: EvidenceViewModel?,
        popupWindow: PopupWindow?,
        progressBar: ProgressBar?, adRequest: AdRequest?
    ) {
        super.init(
            globalPreferencesViewModel, evidenceViewModel,
            popupWindow, progressBar, adRequest
        )
    }

    @SuppressLint("ResourceType")
    fun createPopupWindow(popupWindow: PopupWindow?) {
        super.createPopupWindow(popupWindow, GhostPopupModel(context))
    }


    fun requestInvalidateGhostContainer(canReorder: Boolean) {
        if (evidenceViewModel!!.ghostOrderData!!.hasChanges() && canReorder) {
            reorderGhostViews()
        } else {
            updateGhostViews()
        }
    }

    fun forceResetGhostContainer() {
        reorderGhostViews()
    }

    protected fun reorderGhostViews() {
        val ghostOrderData = evidenceViewModel!!.ghostOrderData
        val currOrder = ghostOrderData!!.currOrder

        for (j in currOrder!!) {
            val childView = this.findViewById<View>(j)

            this.removeView(childView)
            this.addView(childView)
        }
    }

    private fun updateGhostViews() {
        for (i in 0 until this.childCount) {
            val g = getChildAt(i) as GhostView
            g.forceUpdateComponents()
        }
    }

    override fun buildViews() {
        val newGhostOrder = evidenceViewModel!!.ghostOrderData!!.currOrder

        this.weightSum = newGhostOrder!!.size.toFloat()

        for (j in newGhostOrder) {
            val ghostView: GhostView = object : GhostView(
                context
            ) {
                override fun createPopup() {
                    if (popupWindow != null) {
                        popupWindow!!.dismiss()
                    }

                    val ghostPopupWindow = GhostPopupWindow(context)
                    ghostPopupWindow.popupWindow = popupWindow
                    ghostPopupWindow.build(
                        evidenceViewModel!!,
                        (popupData as GhostPopupModel?)!!,
                        j,
                        adRequest
                    )
                }
            }

            ghostView.build(evidenceViewModel!!, j)

            this.addView(ghostView)
        }
    }
}
