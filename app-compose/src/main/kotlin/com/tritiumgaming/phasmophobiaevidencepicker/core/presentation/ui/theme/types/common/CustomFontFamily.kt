package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.common

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

@Immutable
data class CustomFontFamily(
    val regular: TextStyle = TextStyle(),
    val bold: TextStyle = TextStyle(),
    val narrow: TextStyle = TextStyle(),
    val boldNarrow: TextStyle = TextStyle()
)
