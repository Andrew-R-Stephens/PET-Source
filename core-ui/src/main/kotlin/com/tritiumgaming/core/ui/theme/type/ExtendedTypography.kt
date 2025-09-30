package com.tritiumgaming.core.ui.theme.type

import androidx.compose.runtime.Immutable
import com.tritiumgaming.core.ui.theme.ExtendedTheme
import com.tritiumgaming.core.ui.theme.title_classic
import com.tritiumgaming.core.ui.theme.type.common.CustomFontFamily
import com.tritiumgaming.core.ui.theme.type.common.ExtrasFamily

@Immutable
data class ExtendedTypography(
    val extrasFamily: ExtrasFamily = ExtrasFamily(
        title = title_classic
    ),
    val primary: CustomFontFamily = CustomFontFamily(),
    val secondary: CustomFontFamily = CustomFontFamily(),
    val tertiary: CustomFontFamily = CustomFontFamily(),
    val quaternary: CustomFontFamily = CustomFontFamily()
) : ExtendedTheme()