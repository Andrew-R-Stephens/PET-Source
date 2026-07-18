package com.tritiumgaming.core.ui.widgets.walkthrough

import androidx.compose.runtime.Immutable

/**
 * Represents a single step in a walkthrough tutorial.
 */
@Immutable
data class WalkthroughStep(
    val id: String,
    val targetIds: List<String>,
    val titleRes: Int? = null,
    val descriptionPagesRes: List<Int> = emptyList(),
    val isMajor: Boolean = false,
    val parentStepId: String? = null
)
