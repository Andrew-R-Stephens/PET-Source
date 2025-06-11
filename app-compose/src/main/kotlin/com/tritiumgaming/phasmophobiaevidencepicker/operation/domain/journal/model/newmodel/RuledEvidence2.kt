package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.newmodel

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Stable
@Immutable
data class RuledEvidence2(
    val evidence: EvidenceType
) {
    private val _ruling = MutableStateFlow(Ruling2.NEUTRAL)
    val ruling = _ruling.asStateFlow()
    fun setRuling(ruling: Ruling2) {
        _ruling.update { ruling }
    }

    enum class Ruling2(
        @DrawableRes var icon: Int,
    ) {
        NEGATIVE(R.drawable.ic_selector_neg_unsel),
        NEUTRAL(R.drawable.ic_selector_inc_unsel),
        POSITIVE(R.drawable.ic_selector_pos_unsel)
    }

    fun isRuling(other: Ruling2?): Boolean {
        return ruling.value == other
    }

    fun isEvidence(other: RuledEvidence2): Boolean {
        return isEvidence(other.evidence)
    }

    fun isEvidence(other: EvidenceType): Boolean {
        return this.evidence == other
    }

    override fun toString(): String {
        return ruling.value.name
    }

}