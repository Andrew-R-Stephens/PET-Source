package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups

interface CodexTypeRepository {

    fun fetchItems(context: Context): CodexGroups

}