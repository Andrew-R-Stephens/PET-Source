package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.achievevments

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.CodexGroups

class CodexAchievementsRepository(
    val codexAchievementsLocalDataSource: CodexAchievementsLocalDataSource
) : CodexGroups()