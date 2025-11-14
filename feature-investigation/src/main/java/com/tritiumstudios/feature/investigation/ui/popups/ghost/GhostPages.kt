package com.tritiumstudios.feature.investigation.ui.popups.ghost

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R

enum class GhostPage(
    val pageIndex: Int,
    @field:StringRes val label: Int
) {
    DESCRIPTION(0, R.string.popup_ghost_info),
    STRENGTH(1, R.string.popup_ghost_strength),
    WEAKNESS(1, R.string.popup_ghost_weakness),
    HUNT(1, R.string.popup_ghost_huntdata)
}