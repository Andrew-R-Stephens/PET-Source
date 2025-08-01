package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.sanity

data class SanityUiState(
    internal val sanityMax: Float = MAX_SANITY,
    internal val drainModifier: Float = 1f,
    internal val insanityLevel: Float = 0f,
    internal val sanityLevel: Float = sanityMax - insanityLevel,
    internal val isInsane: Boolean = sanityLevel < SAFE_MIN_BOUNDS
){
    companion object {

        const val MIN_SANITY = 0f
        const val HALF_SANITY = 50f
        const val THREE_FOURTH_SANITY = 75f
        const val MAX_SANITY = 100f

        const val SAFE_MIN_BOUNDS = 70f

    }
}
