package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.sanity

data class PlayerSanityUiState(
    internal val insanityLevel: Float = 0.0f,
    internal val sanityLevel: Float = 1.0f
) {

    internal val isInsane: Boolean
        get() = sanityLevel < SAFE_MIN_BOUNDS

    companion object {

        const val MIN_SANITY = 0.0f
        const val HALF_SANITY = 0.5f
        const val THREE_FOURTH_SANITY = 0.75f
        const val MAX_SANITY = 1.0f

        const val SAFE_MIN_BOUNDS = 0.7f

    }
}
