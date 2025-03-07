package com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import android.widget.ProgressBar
import com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.popups.GhostPopupWindow
import com.tritiumgaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.ghost.GhostView
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups.GhostPopupModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel
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
        globalPreferencesViewModel: GlobalPreferencesViewModel,
        investigationViewModel: InvestigationViewModel,
        popupWindow: PopupWindow?, progressBar: ProgressBar?, adRequest: AdRequest?
    ) {
        super.init(globalPreferencesViewModel, investigationViewModel,
            popupWindow, progressBar, adRequest)
    }

    @SuppressLint("ResourceType")
    fun createPopupWindow(popupWindow: PopupWindow?) {
        super.createPopupWindow(popupWindow, GhostPopupModel(context))
    }

    override fun build() {
        investigationViewModel?.let { investigationViewModel ->
            val newGhostOrder =
                investigationViewModel.investigationModel?.ghostOrderModel?.currOrder

            newGhostOrder?.let { ghostOrder ->
                Log.d("GhostOrder", "Loading New ${newGhostOrder.joinToString()}")
                this.weightSum = ghostOrder.size.toFloat()

                for (index in ghostOrder) {
                    val ghostView = GhostView(context)
                    ghostView.ghostViewListener = object: GhostView.GhostViewListener() {
                        override fun onCreatePopup() {
                            popupWindow?.dismiss()

                            val ghostPopupWindow = GhostPopupWindow(context)
                            ghostPopupWindow.popupWindow = popupWindow
                            investigationViewModel.investigationModel?.let { investigationModel ->
                                ghostPopupWindow.build(
                                    investigationModel, (popupData as GhostPopupModel),
                                    index, adRequest
                                )
                            }
                        }
                    }

                    ghostView.build(investigationViewModel, index)

                    this.addView(ghostView)
                }
            }
        }
    }

    fun attemptInvalidate(canReorder: Boolean) {
        if (investigationViewModel?.investigationModel?.ghostOrderModel?.hasChanges() == true
            && canReorder) { reorder() } else { updateChildren() }
    }

    private fun reorder() {
        investigationViewModel?.let { investigationViewModel ->
            val ghostOrderData = investigationViewModel.investigationModel?.ghostOrderModel

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
