package com.tritiumgaming.core.ui.theme.palette.provider

import androidx.compose.runtime.staticCompositionLocalOf
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

val LocalPalette = staticCompositionLocalOf { ExtendedPalette() }

val LocalPalettesMap = mapOf(
    Pair("CzjtxSbXRwIpX8SYR0ttngAND", ClassicPalette),
    Pair("ey6VbQN5tx0MgR8tw4iJq3J8L", Monochromacy),
    Pair("9Ec5cQIB0Jb73gbQ3a9R5kScX", Deuteranomaly),
    Pair("44uBEX9ID9131TMG3yH7T2P1Q", Protanomaly),
    Pair("28yi3LJWYMbA7v87RMAEznISy", Tritanomaly),
    Pair("9QWud4Mj5C4jYp1hOA91b6G0L", Recruit),
    Pair("1UdIWr97JGtOoRszQHpBYfK5U", Investigator),
    Pair("1oF0ZF78LqA64zRyn8ozgMqLu", PrivateInvestigator),
    Pair("5T2mB6W1370DEc3cnYN2bI0gT", Detective),
    Pair("9t44qIYH7UU3EPwvgXEO6309E", Technician),
    Pair("9Uo9I6AmpW2XbhHDIxCP4ppds", Specialist),
    Pair("E2m4fr5Vp6dv07JnWE5Q3T08D", Analyst),
    Pair("6BL0zB7jGGye94j45hq3wtwVq", Agent),
    Pair("8No78zJ6oUSBLS818jH10Seni", Operator),
    Pair("4p77qstew9oX20l4X5O0J010k", Commissioner),
    Pair("B6xTXIXttQUqW02Skd094BY15", Easter),
    Pair("EOkBD2aJZhOh9V821o20GFLXK", Halloween23),
    Pair("7RF705OjW3uo39zl0aTktINHd", Holiday22),
    Pair("0XrEhnlpA9mD9dNxrzCbNfkRe", Holiday23),
    Pair("Ex00XEBLXbh65Nk9SdYMrMfsP", Discord),
    Pair("2pe8HDVVWRfxhPUw4Xf0rhgwb", ContentCreator),
    Pair("56iV5cnR3lRJ59uZF1mTtEz9X", Developer),
    Pair("e6nDE683SE7Wg9dRPJeN61CD6", Translator),
    Pair("8xkSw89x50Zwgr4tGd6z5X0a7", Winner),
    Pair("88kEPt48wGs0ZxR4n7gtM3OTe", Artist),
    Pair("tempWhiteboard", Whiteboard),
    Pair("tempStratHero", StrategemHero),
)

private val LocalPalettesList = LocalPalettesMap.toList()

val LocalDefaultPalette = SimpleUniquePalette(
    LocalPalettesList[0].first,
    LocalPalettesList[0].second
)

data class SimpleUniquePalette(val uuid: String, val palette: ExtendedPalette)