package com.tritiumgaming.core.ui.mapper

import com.tritiumgaming.core.ui.theme.palette.*
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType

fun PaletteType.toPaletteResource(): ExtendedPalette =
    when (this) {
        PaletteType.CLASSIC -> ClassicPalette
        PaletteType.MONOCHROMACY -> Monochromacy
        PaletteType.DEUTERANOMALY -> Deuteranomaly
        PaletteType.PROTANOMALY -> Protanomaly
        PaletteType.TRITANOMALY -> Tritanomaly
        PaletteType.WHITEBOARD -> Whiteboard
        PaletteType.RECRUIT -> Recruit
        PaletteType.INVESTIGATOR -> Investigator
        PaletteType.PRIVATE_INVESTIGATOR -> PrivateInvestigator
        PaletteType.DETECTIVE -> Detective
        PaletteType.TECHNICIAN -> Technician
        PaletteType.SPECIALIST -> Specialist
        PaletteType.ANALYST -> Analyst
        PaletteType.AGENT -> Agent
        PaletteType.OPERATOR -> Operator
        PaletteType.COMMISSIONER -> Commissioner
        PaletteType.EASTER -> Easter
        PaletteType.HALLOWEEN_23 -> Halloween23
        PaletteType.HOLIDAY_22 -> Holiday22
        PaletteType.HOLIDAY_23 -> Holiday23
        PaletteType.DISCORD -> Discord
        PaletteType.CONTENT_CREATOR -> ContentCreator
        PaletteType.DEVELOPER -> Developer
        PaletteType.TRANSLATOR -> Translator
        PaletteType.WINNER -> Winner
        PaletteType.ARTIST -> Artist
        PaletteType.STRATAGEM_HERO -> StratagemHero
    }
