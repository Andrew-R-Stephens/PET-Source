package com.tritiumgaming.core.ui.widgets.walkthrough

import androidx.compose.runtime.Immutable

/**
 * Represents a single page within a walkthrough step.
 */
@Immutable
data class WalkthroughPage(
    val descriptionRes: Int,
    val subtitleRes: Int? = null,
    val targetIds: List<String> = emptyList()
)

/**
 * Represents a single step (category) in a walkthrough tutorial.
 */
@Immutable
data class WalkthroughStep(
    val id: String,
    val pages: List<WalkthroughPage>,
    val titleRes: Int? = null,
    val isMajor: Boolean = false,
    val parentStepId: String? = null
)
