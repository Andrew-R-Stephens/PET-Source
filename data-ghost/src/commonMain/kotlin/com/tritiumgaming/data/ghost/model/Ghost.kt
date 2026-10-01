package com.tritiumgaming.data.ghost.model

import com.tritiumgaming.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostSpeed
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.data.ghost.mapper.GhostResources.HuntCooldown
import com.tritiumgaming.data.ghost.mapper.GhostResources.HuntSanityBounds

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
    val speed: GhostSpeed,
    val huntSanityBounds: HuntSanityBounds,
    val huntCooldown: HuntCooldown,
)