package com.TritiumGaming.phasmophobiaevidencepicker.data.repository


import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.achievevments.AchievementsStoreModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.equipment.EquipmentStoreModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.codex.itemshop.itemstore.possessions.PossessionsStoreModel

class CodexRepository(
    context: Context
) {

    val achievementsStore = AchievementsStoreModel()
    val equipmentStore = EquipmentStoreModel()
    val possessionsStore = PossessionsStoreModel()

    init {
        achievementsStore.init(context)
        equipmentStore.init(context)
        possessionsStore.init(context)
    }
}