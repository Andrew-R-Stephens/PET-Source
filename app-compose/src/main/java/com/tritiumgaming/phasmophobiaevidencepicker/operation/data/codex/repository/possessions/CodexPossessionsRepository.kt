package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.possessions

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.CodexGroups.CodexGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.CodexGroups.CodexGroup.CodexGroupItem


class CodexPossessionsRepository(
    context: Context,
    val localSource: CodexPossessionsLocalDataSource
) {

    val possessions = fetchPossessions(context)

    fun fetchPossessions(context: Context): CodexGroups {
        return localSource.fetchPossessions(context = context)
    }

}
