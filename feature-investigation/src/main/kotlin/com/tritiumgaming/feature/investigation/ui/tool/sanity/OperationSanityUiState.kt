package com.tritiumgaming.feature.investigation.ui.tool.sanity

import android.util.Log

data class OperationSanityUiState(
    internal val sanityMax: Float = 1f,
    internal val drainModifier: Float = 1f
){
    init{

        Log.d("OperationSanityUiState", "Diff: $drainModifier")

    }
}
