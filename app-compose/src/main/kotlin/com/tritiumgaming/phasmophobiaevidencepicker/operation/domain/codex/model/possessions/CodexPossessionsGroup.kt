package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CodexPossessionsGroup(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val items: List<CodexPossessionsGroupItem>
) {
    val size: Int = items.size
}
