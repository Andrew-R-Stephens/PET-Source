package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model

import androidx.annotation.StringRes

data class GhostPopupRecord(
    val id: String = "0",
    @StringRes val generalInfo: Int,
    @StringRes val strengthData: Int,
    @StringRes val weaknessData: Int,
    @StringRes val huntData: Int
): PopupRecord()
