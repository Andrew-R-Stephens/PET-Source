package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section.lists

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import android.widget.ProgressBar
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.popups.GhostPopupWindow
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section.ghost.GhostView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.core.view.isEmpty
import kotlin.coroutines.cancellation.CancellationException

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
        popupWindow: PopupWindow?,
        progressBar: ProgressBar?,
        adRequest: AdRequest?
    ) {
        super.init(
            globalPreferencesViewModel,
            investigationViewModel,
            popupWindow,
            progressBar,
            adRequest
        )
    }

    override fun build() {

        fun buildGhosts() {
            investigationViewModel?.let { investigationViewModel ->

                investigationViewModel.ghostOrder.let { order ->

                    Log.d("GhostListView", "Building ${order.value.size}")
                    this.weightSum = order.value.size.toFloat()

                    for (index in order.value) {
                        val ghostView = GhostView(context)
                        ghostView.ghostViewListener = object: GhostView.GhostViewListener() {
                            override fun onCreatePopup() {
                                popupWindow?.dismiss()

                                val ghostPopupWindow = GhostPopupWindow(context)
                                ghostPopupWindow.popupWindow = popupWindow
                                ghostPopupWindow.build(
                                    investigationViewModel,
                                    ghostView.ghostModel?.popupModel,
                                    index, adRequest
                                )
                            }
                        }

                        ghostView.build(investigationViewModel, index)

                        this.addView(ghostView)
                    }

                }

            }
        }

        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            investigationViewModel?.ghostOrder?.collectLatest {
                if(isEmpty()) {
                    Log.d("GhostListView", "Attempt Building")
                    buildGhosts()
                } else {
                    Log.d("GhostListView", "Attempt Invalidate")
                    attemptInvalidate()
                }
            }
        }

    }

    fun attemptInvalidate() {
        if(globalPreferencesViewModel?.ghostReorderPreference?.value == true) { reorder() }
        else { updateChildren() }
    }

    private fun reorder() {
        Log.d("GhostListView", "Reordering ${investigationViewModel?.ghostOrder?.value?.size}")

        investigationViewModel?.let { investigationViewModel ->
            val order = investigationViewModel.ghostOrder.value

            for (j in order) {
                Log.d("GhostListView", "Reordering id: $j")
                val childView = this.findViewById<View>(j)
                this.removeView(childView)
                this.addView(childView)
            }
        }
    }

    private fun updateChildren() {
        Log.d("GhostListView", "Updating Children")

        for (i in 0 until this.childCount) {
            val g = getChildAt(i) as GhostView
            g.forceUpdateComponents()
        }
    }

    fun reset() {
        reorder()
    }

}
