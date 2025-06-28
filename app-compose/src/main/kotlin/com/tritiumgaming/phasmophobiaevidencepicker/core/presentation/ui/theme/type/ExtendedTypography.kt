package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type

import androidx.compose.runtime.Immutable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.ExtendedTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.title_classic
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.common.CustomFontFamily
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.common.ExtrasFamily

@Immutable
data class ExtendedTypography(
    val extrasFamily: ExtrasFamily = ExtrasFamily(
        title = title_classic
    ),

    val primary: CustomFontFamily = CustomFontFamily(),
    val secondary: CustomFontFamily = CustomFontFamily(),
    val tertiary: CustomFontFamily = CustomFontFamily(),
    val quaternary: CustomFontFamily = CustomFontFamily()
): ExtendedTheme()