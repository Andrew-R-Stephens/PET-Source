package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model

import androidx.annotation.StringRes

data class GhostPopupRecord(
    val id: String = "0",
    @StringRes val infoArray: Int,
    @StringRes val strengthArray: Int,
    @StringRes val weaknessArray: Int,
    @StringRes val huntDataArray: Int
): PopupRecord()
