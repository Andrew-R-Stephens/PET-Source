package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.EvidencePopupRecord

interface EvidencePopupDataSource: PopupDataSource {

    override fun fetchPopups(context: Context): List<EvidencePopupRecord>

}