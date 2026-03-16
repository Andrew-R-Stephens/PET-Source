package com.tritiumgaming.shared.data.ghost.mapper

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostSpeed
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntCooldown
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntSanityBounds
import com.tritiumgaming.shared.data.ghost.model.SanityBounds

class GhostResources {

    enum class GhostIdentifier {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class GhostTitle {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class GhostIcon {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class GhostDescription {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class GhostStrength {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class GhostWeakness {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class GhostHuntInfo {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class GhostSpeed {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class HuntSanityBounds {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

    enum class HuntCooldown {
        BANSHEE,
        DAYAN,
        DEMON,
        DEOGEN,
        GALLU,
        GORYO,
        HANTU,
        JINN,
        MARE,
        MOROI,
        MYLING,
        OBAKE,
        OBAMBO,
        ONI,
        ONRYO,
        PHANTOM,
        POLTERGEIST,
        RAIJU,
        REVENANT,
        SHADE,
        SPIRIT,
        THAYE,
        THE_TWINS,
        THE_MIMIC,
        WRAITH,
        YOKAI,
        YUREI
    }

}

fun GhostSpeed.toMinimumAsInt(): Int =
    when (this) {
        GhostSpeed.BANSHEE -> 102
        GhostSpeed.DAYAN -> 72
        GhostSpeed.DEMON -> 102
        GhostSpeed.DEOGEN -> 24
        GhostSpeed.GALLU -> 81
        GhostSpeed.GORYO -> 102
        GhostSpeed.HANTU -> 84
        GhostSpeed.JINN -> 102
        GhostSpeed.MARE -> 102
        GhostSpeed.MOROI -> 90
        GhostSpeed.MYLING -> 102
        GhostSpeed.OBAKE -> 102
        GhostSpeed.OBAMBO -> 87
        GhostSpeed.ONI -> 102
        GhostSpeed.ONRYO -> 102
        GhostSpeed.PHANTOM -> 102
        GhostSpeed.POLTERGEIST -> 102
        GhostSpeed.RAIJU -> 102
        GhostSpeed.REVENANT -> 60
        GhostSpeed.SHADE -> 102
        GhostSpeed.SPIRIT -> 102
        GhostSpeed.THAYE -> 60
        GhostSpeed.THE_TWINS -> 102
        GhostSpeed.THE_MIMIC -> 22
        GhostSpeed.WRAITH -> 102
        GhostSpeed.YOKAI -> 102
        GhostSpeed.YUREI -> 102
    }

fun GhostSpeed.toMaximumAsInt(): Int =
    when (this) {
        GhostSpeed.BANSHEE -> -1
        GhostSpeed.DAYAN -> 135
        GhostSpeed.DEMON -> -1
        GhostSpeed.DEOGEN -> 180
        GhostSpeed.GALLU -> 118
        GhostSpeed.GORYO -> -1
        GhostSpeed.HANTU -> 162
        GhostSpeed.JINN -> 150
        GhostSpeed.MARE -> -1
        GhostSpeed.MOROI -> 135
        GhostSpeed.MYLING -> -1
        GhostSpeed.OBAKE -> -1
        GhostSpeed.OBAMBO -> 118
        GhostSpeed.ONI -> -1
        GhostSpeed.ONRYO -> -1
        GhostSpeed.PHANTOM -> -1
        GhostSpeed.POLTERGEIST -> -1
        GhostSpeed.RAIJU -> 150
        GhostSpeed.REVENANT -> 180
        GhostSpeed.SHADE -> -1
        GhostSpeed.SPIRIT -> -1
        GhostSpeed.THAYE -> 165
        GhostSpeed.THE_TWINS -> -1
        GhostSpeed.THE_MIMIC -> 114
        GhostSpeed.WRAITH -> -1
        GhostSpeed.YOKAI -> -1
        GhostSpeed.YUREI -> -1
    }

fun GhostSpeed.toHasLosMultiplierBoolean(): Boolean =
    when (this) {
        GhostSpeed.BANSHEE -> true
        GhostSpeed.DAYAN -> false
        GhostSpeed.DEMON -> false
        GhostSpeed.DEOGEN -> true
        GhostSpeed.GALLU -> true
        GhostSpeed.GORYO -> true
        GhostSpeed.HANTU -> false
        GhostSpeed.JINN -> false
        GhostSpeed.MARE -> true
        GhostSpeed.MOROI -> true
        GhostSpeed.MYLING -> true
        GhostSpeed.OBAKE -> true
        GhostSpeed.OBAMBO -> true
        GhostSpeed.ONI -> true
        GhostSpeed.ONRYO -> true
        GhostSpeed.PHANTOM -> true
        GhostSpeed.POLTERGEIST -> true
        GhostSpeed.RAIJU -> false
        GhostSpeed.REVENANT -> false
        GhostSpeed.SHADE -> true
        GhostSpeed.SPIRIT -> true
        GhostSpeed.THAYE -> false
        GhostSpeed.THE_TWINS -> true
        GhostSpeed.THE_MIMIC -> true
        GhostSpeed.WRAITH -> true
        GhostSpeed.YOKAI -> true
        GhostSpeed.YUREI -> true
    }

fun HuntSanityBounds.toSanityBounds(): SanityBounds =
    when (this) {
        HuntSanityBounds.BANSHEE -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.DAYAN -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = 45, empowered = 65)
        HuntSanityBounds.DEMON -> SanityBounds(
            normal = 70, suppressed = SanityBounds.UNSET, empowered = 100)
        HuntSanityBounds.DEOGEN -> SanityBounds(
            normal = 40, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.GALLU -> SanityBounds(
            normal = 50, suppressed = 40, empowered = 60)
        HuntSanityBounds.GORYO -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.HANTU -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.JINN -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.MARE -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = 40, empowered = 60)
        HuntSanityBounds.MOROI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.MYLING -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.OBAKE -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.OBAMBO -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = 65, empowered = 10)
        HuntSanityBounds.ONI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.ONRYO -> SanityBounds(
            normal = 60, suppressed = 40, empowered = 100)
        HuntSanityBounds.PHANTOM -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.POLTERGEIST -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.RAIJU -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = 65)
        HuntSanityBounds.REVENANT -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.SHADE -> SanityBounds(
            normal = 35, suppressed = 0, empowered = SanityBounds.UNSET)
        HuntSanityBounds.SPIRIT -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.THAYE -> SanityBounds(
            normal = 75, suppressed = 15, empowered = SanityBounds.UNSET)
        HuntSanityBounds.THE_TWINS -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.THE_MIMIC -> SanityBounds(
            normal = 50, suppressed = 0, empowered = 100)
        HuntSanityBounds.WRAITH -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
        HuntSanityBounds.YOKAI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = 80)
        HuntSanityBounds.YUREI -> SanityBounds(
            normal = SanityBounds.STANDARD, suppressed = SanityBounds.UNSET, empowered = SanityBounds.UNSET)
    }

fun HuntCooldown.toLong(): Long =
    when (this) {
        HuntCooldown.BANSHEE -> 25000L
        HuntCooldown.DAYAN -> 25000L
        HuntCooldown.DEMON -> 20000L
        HuntCooldown.DEOGEN -> 25000L
        HuntCooldown.GALLU -> 25000L
        HuntCooldown.GORYO -> 25000L
        HuntCooldown.HANTU -> 25000L
        HuntCooldown.JINN -> 25000L
        HuntCooldown.MARE -> 25000L
        HuntCooldown.MOROI -> 25000L
        HuntCooldown.MYLING -> 25000L
        HuntCooldown.OBAKE -> 25000L
        HuntCooldown.OBAMBO -> 25000L
        HuntCooldown.ONI -> 25000L
        HuntCooldown.ONRYO -> 25000L
        HuntCooldown.PHANTOM -> 25000L
        HuntCooldown.POLTERGEIST -> 25000L
        HuntCooldown.RAIJU -> 25000L
        HuntCooldown.REVENANT -> 25000L
        HuntCooldown.SHADE -> 25000L
        HuntCooldown.SPIRIT -> 25000L
        HuntCooldown.THAYE -> 25000L
        HuntCooldown.THE_TWINS -> 25000L
        HuntCooldown.THE_MIMIC -> 25000L
        HuntCooldown.WRAITH -> 25000L
        HuntCooldown.YOKAI -> 25000L
        HuntCooldown.YUREI -> 25000L
    }
