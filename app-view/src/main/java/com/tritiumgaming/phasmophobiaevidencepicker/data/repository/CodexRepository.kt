package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.achievevments.CodexAchievements
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.equipment.CodexEquipment
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.itemshop.itemstore.possessions.CodexPossessions

class CodexRepository(
    context: Context
) {

    val achievements = CodexAchievements(context)
    val equipment = CodexEquipment(context)
    val possessions = CodexPossessions(context)

}