package com.tritiumgaming.phasmophobiaevidencepicker.presentation.app.mappers

import com.tritiumgaming.core.ui.theme.palette.Agent
import com.tritiumgaming.core.ui.theme.palette.Analyst
import com.tritiumgaming.core.ui.theme.palette.Artist
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.Commissioner
import com.tritiumgaming.core.ui.theme.palette.ContentCreator
import com.tritiumgaming.core.ui.theme.palette.Detective
import com.tritiumgaming.core.ui.theme.palette.Deuteranomaly
import com.tritiumgaming.core.ui.theme.palette.Developer
import com.tritiumgaming.core.ui.theme.palette.Discord
import com.tritiumgaming.core.ui.theme.palette.Easter
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.Halloween23
import com.tritiumgaming.core.ui.theme.palette.Holiday22
import com.tritiumgaming.core.ui.theme.palette.Holiday23
import com.tritiumgaming.core.ui.theme.palette.Investigator
import com.tritiumgaming.core.ui.theme.palette.Monochromacy
import com.tritiumgaming.core.ui.theme.palette.Operator
import com.tritiumgaming.core.ui.theme.palette.PrivateInvestigator
import com.tritiumgaming.core.ui.theme.palette.Protanomaly
import com.tritiumgaming.core.ui.theme.palette.Recruit
import com.tritiumgaming.core.ui.theme.palette.Specialist
import com.tritiumgaming.core.ui.theme.palette.StrategemHero
import com.tritiumgaming.core.ui.theme.palette.Technician
import com.tritiumgaming.core.ui.theme.palette.Translator
import com.tritiumgaming.core.ui.theme.palette.Tritanomaly
import com.tritiumgaming.core.ui.theme.palette.Whiteboard
import com.tritiumgaming.core.ui.theme.palette.Winner
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteResources.PaletteType

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
        PaletteType.STRATAGEM_HERO -> StrategemHero
    }