package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.badge_default
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.title_default

@Immutable
data class ExtrasFamily(
    @StringRes val title: Int = title_default,
    @DrawableRes val badge: Int = badge_default,
    val isLightMode: Boolean = false
)