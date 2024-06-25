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
        popupWindow: PopupWindow?, progressBar: ProgressBar?, adRequest: AdRequest?
    ) {
        super.init(globalPreferencesViewModel, evidenceViewModel,
            popupWindow, progressBar, adRequest)
    }

    @SuppressLint("ResourceType")
    fun createPopupWindow(popupWindow: PopupWindow?) {
        super.createPopupWindow(popupWindow, GhostPopupModel(context))
    }

    override fun buildViews() {
        val popupRecord = (popupData as GhostPopupModel)

        evidenceViewModel?.let { evidenceViewModel ->
            val newGhostOrder = evidenceViewModel.ghostOrderModel?.currOrder

            newGhostOrder?.let {
                this.weightSum = newGhostOrder.size.toFloat()

                for (index in newGhostOrder) {
                    val ghostView = GhostView(context)
                    ghostView.ghostViewListener = object: GhostView.GhostViewListener() {
                        override fun onCreatePopup() {
                            popupWindow?.dismiss()

                            val ghostPopupWindow = GhostPopupWindow(context)
                            ghostPopupWindow.popupWindow = popupWindow
                            ghostPopupWindow.build(evidenceViewModel, popupRecord, index, adRequest)
                        }
                    }

                    ghostView.build(evidenceViewModel, index)

                    this.addView(ghostView)
                }
            }
        }
    }
    fun attemptInvalidate(canReorder: Boolean) {
        if (evidenceViewModel?.ghostOrderModel?.hasChanges() == true && canReorder) {
            reorder()
        } else { updateChildren() }
    }

    protected fun reorder() {
        evidenceViewModel?.let { evidenceViewModel ->
            val ghostOrderData = evidenceViewModel.ghostOrderModel

            ghostOrderData?.currOrder?.let { currOrder ->
                for (j in currOrder) {
                    val childView = this.findViewById<View>(j)
                    this.removeView(childView)
                    this.addView(childView)
                }
            }
        }
    }

    private fun updateChildren() {
        for (i in 0 until this.childCount) {
            val g = getChildAt(i) as GhostView
            g.forceUpdateComponents()
        }
    }

    fun reset() {
        reorder()
    }

}
