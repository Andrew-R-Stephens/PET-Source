package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexTypeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.source.CodexDataSource

class CodexAchievementsRepositoryImpl(
    val localSource: CodexDataSource
): CodexTypeRepository {

    override fun fetchItems(): CodexGroups {
        return localSource.fetchItems()
    }

}