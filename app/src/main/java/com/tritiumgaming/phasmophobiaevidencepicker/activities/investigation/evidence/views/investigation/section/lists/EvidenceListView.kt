package com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.evidence.views.investigation.section.lists

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.PopupWindow
import android.widget.ProgressBar
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.evidence.views.investigation.popups.EvidencePopupWindow
import com.tritiumgaming.phasmophobiaevidencepicker.ui.investigation.evidence.views.investigation.section.evidence.EvidenceView
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidencePopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.InvestigationViewModel

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
        globalPreferencesViewModel: GlobalPreferencesViewModel,
        investigationViewModel: InvestigationViewModel,
        popupWindow: PopupWindow?, progressBar: ProgressBar?, adRequest: AdRequest?,
        ghostList: GhostListView?
    ) {
        super.init(globalPreferencesViewModel, investigationViewModel,
            popupWindow, progressBar, adRequest)
        this.ghostList = ghostList
    }

    @SuppressLint("ResourceType")
    fun createPopupWindow(popupWindow: PopupWindow?) {
        super.createPopupWindow(popupWindow, EvidencePopupRepository(context))
    }

    override fun build() {
        for (i in 0 until (popupData as EvidencePopupRepository).count) {
            val popupRecord =
                (popupData as EvidencePopupRepository).getEvidencePopupRecordAt(i)

            investigationViewModel?.let { investigationViewModel ->
                val evidenceView = EvidenceView(context)
                evidenceView.evidenceViewListener = object: EvidenceView.EvidenceViewListener() {
                    override fun onCreatePopup() {
                        popupWindow?.dismiss()

                        val evidencePopupWindow = EvidencePopupWindow(context)
                        evidencePopupWindow.popupWindow = popupWindow
                        evidencePopupWindow.build(investigationViewModel, popupRecord, adRequest)
                    }

                    override fun onAttemptInvalidate() {
                        ghostList?.attemptInvalidate(
                            globalPreferencesViewModel?.ghostReorderPreference?.value ?: false)
                    }
                }

                ghostList?.let { ghostList ->
                    evidenceView.build(investigationViewModel, i, ghostList)
                }

                this.addView(evidenceView)
            }
        }
    }

    override fun initObservables() {
        // Nothing here
    }
}
