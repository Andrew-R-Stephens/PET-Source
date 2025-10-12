package com.tritiumgaming.feature.operation.ui.investigation.toolbar.subsection.sanitytracker.controller.operationconfig.operation

data class OperationSanityUiState(
    internal val sanityMax: Float = MAX_SANITY,
    internal val drainModifier: Float = 1f,
    internal val averageTeamSanity: Float = 1f
) {
    fun getDrainModifier(
        difficultyModifier: Float,
        mapModifier: Float
    ): Float {

        return difficultyModifier * mapModifier
    }

    companion object {
        const val MAX_SANITY = 100f
    }
}

