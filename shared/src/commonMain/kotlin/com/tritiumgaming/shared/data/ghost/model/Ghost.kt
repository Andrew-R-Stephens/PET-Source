package com.tritiumgaming.shared.data.ghost.model

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostSpeed
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostWeakness

data class Ghost(
    val id: GhostIdentifier,
    val name: GhostTitle,
    val icon: GhostIcon,
    val info: GhostDescription,
    val strengthData: GhostStrength,
    val weaknessData: GhostWeakness,
    val huntData: GhostHuntInfo,
    val normalEvidence: List<EvidenceIdentifier>,
    val strictEvidence: List<EvidenceIdentifier>,
    val speed: GhostSpeed
)