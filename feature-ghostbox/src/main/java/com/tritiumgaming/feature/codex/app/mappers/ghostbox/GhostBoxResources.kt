package com.tritiumgaming.feature.codex.app.mappers.ghostbox

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.ghostbox.mapper.GhostBoxResources

@StringRes fun GhostBoxResources.Response.toStringResource(): Int =
    when (this) {
        GhostBoxResources.Response.Q_01 -> R.string.spiritbox_entry_q01
        GhostBoxResources.Response.Q_02 -> R.string.spiritbox_entry_q02
        GhostBoxResources.Response.Q_03 -> R.string.spiritbox_entry_q03
        GhostBoxResources.Response.Q_04 -> R.string.spiritbox_entry_q04
        GhostBoxResources.Response.Q_05 -> R.string.spiritbox_entry_q05
        GhostBoxResources.Response.Q_06 -> R.string.spiritbox_entry_q06
        GhostBoxResources.Response.Q_07 -> R.string.spiritbox_entry_q07
        GhostBoxResources.Response.Q_08 -> R.string.spiritbox_entry_q08
        GhostBoxResources.Response.Q_09 -> R.string.spiritbox_entry_q09
        GhostBoxResources.Response.Q_10 -> R.string.spiritbox_entry_q10
        GhostBoxResources.Response.Q_11 -> R.string.spiritbox_entry_q11
        GhostBoxResources.Response.Q_12 -> R.string.spiritbox_entry_q12
        GhostBoxResources.Response.Q_14 -> R.string.spiritbox_entry_q14
        GhostBoxResources.Response.Q_16 -> R.string.spiritbox_entry_q16
        GhostBoxResources.Response.Q_17 -> R.string.spiritbox_entry_q17
        GhostBoxResources.Response.Q_18 -> R.string.spiritbox_entry_q18
        GhostBoxResources.Response.Q_19 -> R.string.spiritbox_entry_q19
        GhostBoxResources.Response.Q_20 -> R.string.spiritbox_entry_q20
        GhostBoxResources.Response.Q_21 -> R.string.spiritbox_entry_q21
        GhostBoxResources.Response.Q_22 -> R.string.spiritbox_entry_q22
        GhostBoxResources.Response.Q_23 -> R.string.spiritbox_entry_q23
        GhostBoxResources.Response.Q_24 -> R.string.spiritbox_entry_q24
        GhostBoxResources.Response.Q_25 -> R.string.spiritbox_entry_q25
        GhostBoxResources.Response.Q_26 -> R.string.spiritbox_entry_q26
        GhostBoxResources.Response.Q_27 -> R.string.spiritbox_entry_q27
        GhostBoxResources.Response.Q_28 -> R.string.spiritbox_entry_q28
        GhostBoxResources.Response.Q_30 -> R.string.spiritbox_entry_q30
        GhostBoxResources.Response.Q_31 -> R.string.spiritbox_entry_q31
        GhostBoxResources.Response.Q_32 -> R.string.spiritbox_entry_q32
        GhostBoxResources.Response.Q_33 -> R.string.spiritbox_entry_q33
        GhostBoxResources.Response.Q_34 -> R.string.spiritbox_entry_q34
        GhostBoxResources.Response.Q_35 -> R.string.spiritbox_entry_q35
        GhostBoxResources.Response.Q_36 -> R.string.spiritbox_entry_q36
    }