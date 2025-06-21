package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Monochromacy
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Deuteranomaly
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Protanomaly
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Tritanomaly
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Whiteboard
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Recruit
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Investigator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.PrivateInvestigator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Detective
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Technician
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Specialist
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Analyst
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Agent
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Operator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Commissioner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Easter
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Halloween23
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Holiday22
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Holiday23
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Discord
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ContentCreator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Developer
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Translator
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Winner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.Artist
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.StrategemHero
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

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