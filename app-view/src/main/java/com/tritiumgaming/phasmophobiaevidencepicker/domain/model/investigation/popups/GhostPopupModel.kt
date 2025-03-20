package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups

import androidx.annotation.StringRes

class GhostPopupModel(
    @StringRes val info: Int = 0,
    @StringRes val strength: Int = 0,
    @StringRes val weakness: Int = 0,
    @StringRes val huntInfo: Int = 0,
) {

    fun getCycleDetails(): MutableList<Int> {
        val cycleDetails = mutableListOf<Int>()
        cycleDetails.add(info)
        cycleDetails.add(strength)
        cycleDetails.add(weakness)

        return cycleDetails
    }

}
