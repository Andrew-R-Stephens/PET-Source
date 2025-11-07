package com.tritiumgaming.core.ui.theme.palette.common

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CodexColorFamily(
    val codex1: Color = Color.Unspecified,
    val codex2: Color = codex1,
    val codex3: Color = codex2,
    val codex4: Color = codex3,
    val codex5: Color = codex4,
    val codex6: Color = codex5,
    val codex7: Color = codex2,
)
