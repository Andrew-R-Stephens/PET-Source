package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.possessions

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.codex.CodexGroups


class CodexPossessionsRepository(
    context: Context,
    val localSource: CodexPossessionsLocalDataSource
) {

    val possessions = fetchPossessions(context)

    fun fetchPossessions(context: Context): CodexGroups {
        return localSource.fetchPossessions(context = context)
    }

}
