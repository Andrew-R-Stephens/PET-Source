package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.PopupRecord

interface PopupDataSource {

    fun fetchPopups(context: Context): List<PopupRecord>

}