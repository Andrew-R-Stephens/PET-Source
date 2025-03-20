package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

class EvidencePopupModel(
    @IntegerRes val cost: Int = 0,
    @IntegerRes val unlock_level: IntArray = intArrayOf(),
    @StringRes val descriptions: IntArray = intArrayOf(),
    @DrawableRes val animations: IntArray = intArrayOf()
)