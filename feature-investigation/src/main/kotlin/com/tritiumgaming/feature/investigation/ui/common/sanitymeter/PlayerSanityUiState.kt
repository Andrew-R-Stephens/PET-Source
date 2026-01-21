package com.tritiumgaming.feature.investigation.ui.common.sanitymeter

import com.tritiumgaming.shared.data.sanity.model.SanityLevel.SAFE_MIN_BOUNDS

data class PlayerSanityUiState(
    internal val insanityLevel: Float = 0.0f,
    internal val sanityLevel: Float = 1.0f
) {

    internal val isInsane: Boolean
        get() = sanityLevel < SAFE_MIN_BOUNDS

}
