package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexTypeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.source.CodexDataSource

class CodexPossessionsRepositoryImpl(
    context: Context,
    val localSource: CodexDataSource
): CodexTypeRepository {

    val items = fetchItems(context)

    override fun fetchItems(context: Context): CodexGroups {
        return localSource.fetchItems(context = context)
    }

}