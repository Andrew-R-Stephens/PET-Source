package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model

import android.content.Context
import androidx.annotation.StringRes

class CodexPossessionItem : CodexGroups.CodexGroup.CodexGroupItem() {
    @StringRes private val attributes = ArrayList<Int>()
    @StringRes var altName: Int = 0
    @StringRes var sanityDrainData: Int = 0
    @StringRes var drawChance: Int = 0

    fun addAttribute(value: Int) {
        attributes.add(value)
    }

    override fun getAllAttributesAsFormattedHTML(c: Context): String {
        val out = StringBuilder()
        for (v in attributes) {
            out.append(c.getString(v)).append(" ")
        }

        return out.toString()
    }
}