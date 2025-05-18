package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.source

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups

interface CodexDataSource {

    val applicationContext: Context

    fun fetchItems(): CodexGroups

}