package com.tritiumgaming.shared.data.popup.model

data class GhostPopupRecord(
    val id: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier,
    val name: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle,
    val icon: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIcon,
    val info: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostDescription,
    val strengthData: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostStrength,
    val weaknessData: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostWeakness,
    val huntData: com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostHuntInfo,
)
