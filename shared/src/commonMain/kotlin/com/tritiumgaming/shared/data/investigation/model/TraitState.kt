package com.tritiumgaming.shared.data.investigation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait

@Stable
@Immutable
data class TraitState(
    val trait: GhostTrait,
    val state: TraitValidationType = TraitValidationType.NEUTRAL,
    val enabled: Boolean = true
) {

    fun isRuling(other: TraitValidationType?): Boolean {
        return state == other
    }

    fun isEvidence(other: TraitState): Boolean {
        return isTrait(other.trait)
    }

    fun isTrait(other: GhostTrait): Boolean {
        return this.trait == other
    }

    override fun toString(): String {
        return state.name
    }

}