package com.tritiumgaming.feature.investigation.app.mappers.phase

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.phase.model.PhaseResources

fun PhaseResources.PhaseIdentifier.toPhaseTitle() = when(this) {
    PhaseResources.PhaseIdentifier.SETUP -> PhaseResources.PhaseTitle.SETUP
    PhaseResources.PhaseIdentifier.ACTION -> PhaseResources.PhaseTitle.ACTION
    PhaseResources.PhaseIdentifier.HUNT -> PhaseResources.PhaseTitle.HUNT
}

@StringRes
fun PhaseResources.PhaseTitle.toStringResource(): Int {
    return when (this) {
        PhaseResources.PhaseTitle.SETUP -> R.string.investigation_phase_label_setup
        PhaseResources.PhaseTitle.ACTION -> R.string.investigation_phase_label_action
        PhaseResources.PhaseTitle.HUNT -> R.string.investigation_phase_label_hunt
    }
}