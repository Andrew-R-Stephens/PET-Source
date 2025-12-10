package com.tritiumgaming.feature.investigation.ui.popups.ghost

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R

enum class GhostPage(
    @field:StringRes val label: Int
) {
    DESCRIPTION(R.string.popup_ghost_info),
    STRENGTH(R.string.popup_ghost_strength),
    WEAKNESS(R.string.popup_ghost_weakness),
    HUNT(R.string.popup_ghost_huntdata)
}