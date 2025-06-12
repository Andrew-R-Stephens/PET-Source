package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.tritiumgaming.phasmophobiaevidencepicker.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
@Immutable
data class RuledEvidence(
    val evidence: EvidenceType
) {
    private val _ruling = MutableStateFlow(Ruling.NEUTRAL)
    val ruling = _ruling.asStateFlow()
    fun setRuling(ruling: Ruling) {
        _ruling.update { ruling }
    }

    enum class Ruling(
        @DrawableRes var icon: Int,
    ) {
        NEGATIVE(R.drawable.ic_selector_neg_unsel),
        NEUTRAL(R.drawable.ic_selector_inc_unsel),
        POSITIVE(R.drawable.ic_selector_pos_unsel)
    }

    fun isRuling(other: Ruling?): Boolean {
        return ruling.value == other
    }

    fun isEvidence(other: RuledEvidence): Boolean {
        return isEvidence(other.evidence)
    }

    fun isEvidence(other: EvidenceType): Boolean {
        return this.evidence == other
    }

    override fun toString(): String {
        return ruling.value.name
    }

}