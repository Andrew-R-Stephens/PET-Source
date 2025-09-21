package com.tritiumgaming.core.ui.theme.palette.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.tritiumgaming.core.ui.theme.badge_default
import com.tritiumgaming.core.ui.theme.title_default

@Immutable
data class ExtrasFamily(
    @StringRes val title: Int = title_default,
    @DrawableRes val badge: Int = badge_default,
    val isLightMode: Boolean = false
)