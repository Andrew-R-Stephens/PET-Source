package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section.lists

import android.content.Context
import android.util.AttributeSet
import android.widget.PopupWindow
import android.widget.ProgressBar
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.popups.EvidencePopupWindow
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.evidence.common.investigation.section.evidence.EvidenceView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel

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
        popupWindow: PopupWindow?,
        progressBar: ProgressBar?,
        adRequest: AdRequest?,
        ghostList: GhostListView?
    ) {
        super.init(
            globalPreferencesViewModel,
            investigationViewModel,
            popupWindow,
            progressBar,
            adRequest
        )
        this.ghostList = ghostList
    }

    override fun build() {

        investigationViewModel?.let { investigationViewModel ->
            investigationViewModel.evidenceRepository
                .evidenceList.forEachIndexed { index, it ->
                    ghostList?.let { ghostList ->
                        val evidenceView = EvidenceView(context)
                        evidenceView.evidenceViewListener = object: EvidenceView.EvidenceViewListener() {
                            override fun onCreatePopup() {
                                popupWindow?.dismiss()

                                val evidencePopupWindow = EvidencePopupWindow(context)
                                evidencePopupWindow.popupWindow = popupWindow
                                evidencePopupWindow.build(
                                    evidenceView.evidenceModel,
                                    adRequest
                                )
                            }

                            override fun onAttemptInvalidate() {
                                ghostList.attemptInvalidate()
                            }
                        }
                        evidenceView.build(investigationViewModel, index, ghostList)

                        this.addView(evidenceView)
                    }
                }


        }
    }
}
