package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.achievevments

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.CodexGroups

class CodexAchievementsRepository(
    context: Context,
    val localSource: CodexAchievementsLocalDataSource
) {

    val achievements = fetchAchievements(context)

    fun fetchAchievements(context: Context): CodexGroups {
        return localSource.fetchAchievements(context = context)
    }

}