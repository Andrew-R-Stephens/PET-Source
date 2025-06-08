package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups

interface CodexTypeRepository {

    fun fetchItems(): Result<CodexGroups>

}