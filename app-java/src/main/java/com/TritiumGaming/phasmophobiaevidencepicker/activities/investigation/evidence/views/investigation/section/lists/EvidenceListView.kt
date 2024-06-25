package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.lists

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.PopupWindow
import android.widget.ProgressBar
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.popups.EvidencePopupWindow
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views.investigation.section.evidence.EvidenceView
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups.EvidencePopupModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel
import com.google.android.gms.ads.AdRequest

class EvidenceListView : InvestigationListView {

    private var ghostList: GhostListView? = null

    constructor(context: Context?) :
            super(context)

    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    fun init(
        globalPreferencesViewModel: GlobalPreferencesViewModel?,
        evidenceViewModel: EvidenceViewModel?, popupWindow: PopupWindow?,
        progressBar: ProgressBar?, adRequest: AdRequest?, ghostList: GhostListView?
    ) {
        super.init(
            globalPreferencesViewModel, evidenceViewModel, popupWindow, progressBar, adRequest)
        this.ghostList = ghostList
    }

    @SuppressLint("ResourceType")
    fun createPopupWindow(popupWindow: PopupWindow?) {
        super.createPopupWindow(popupWindow, EvidencePopupModel(context))
    }

    override fun buildViews() {
        for (i in 0 until (popupData as EvidencePopupModel).count) {
            val popupRecord =
                (popupData as EvidencePopupModel).getEvidencePopupRecordAt(i)

            evidenceViewModel?.let { evidenceViewModel ->
                val evidenceView = EvidenceView(context)
                evidenceView.evidenceViewListener = object: EvidenceView.EvidenceViewListener() {
                    override fun onCreatePopup() {
                        popupWindow?.dismiss()

                        val evidencePopupWindow = EvidencePopupWindow(context)
                        evidencePopupWindow.popupWindow = popupWindow
                        evidencePopupWindow.build(evidenceViewModel, popupRecord, adRequest)
                    }

                    override fun onAttemptInvalidate() {
                        ghostList?.attemptInvalidate(
                            globalPreferencesViewModel?.reorderGhostViews ?: false)
                    }
                }

                ghostList?.let { ghostList ->
                    evidenceView.build(evidenceViewModel, i, ghostList)
                }

                this.addView(evidenceView)
            }
        }
    }
}
