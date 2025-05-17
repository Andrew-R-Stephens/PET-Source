package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source

import android.content.Context
import android.content.res.Resources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.GhostPopupRecord

interface GhostPopupDataSource: PopupDataSource {

    override fun fetchPopups(context: Context): ArrayList<GhostPopupRecord>
    fun readGhost(resources: Resources, index: Int, ghostsArrayID: Int): GhostPopupRecord

}