package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

class EvidencePopup(
    val id: String = "0",
    @IntegerRes val cost: Int = 0,
    @IntegerRes val unlock_level: IntArray = intArrayOf(),
    @StringRes val descriptions: IntArray = intArrayOf(),
    @DrawableRes val animations: IntArray = intArrayOf()
)