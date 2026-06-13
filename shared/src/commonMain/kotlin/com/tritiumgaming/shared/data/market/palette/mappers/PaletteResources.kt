package com.tritiumgaming.shared.data.market.palette.mappers

import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType

class PaletteResources {

    enum class PaletteType {
        CLASSIC,
        MONOCHROMACY,
        DEUTERANOMALY,
        PROTANOMALY,
        TRITANOMALY,
        WHITEBOARD,
        RECRUIT,
        INVESTIGATOR,
        PRIVATE_INVESTIGATOR,
        DETECTIVE,
        TECHNICIAN,
        SPECIALIST,
        ANALYST,
        AGENT,
        OPERATOR,
        COMMISSIONER,
        EASTER,
        HALLOWEEN_23,
        HOLIDAY_22,
        HOLIDAY_23,
        DISCORD,
        CONTENT_CREATOR,
        DEVELOPER,
        TRANSLATOR,
        WINNER,
        ARTIST,
        STRATAGEM_HERO
    }

}

fun PaletteType.asUuid(): String =
    when (this) {
        PaletteType.CLASSIC -> "CzjtxSbXRwIpX8SYR0ttngAND"
        PaletteType.MONOCHROMACY -> "ey6VbQN5tx0MgR8tw4iJq3J8L"
        PaletteType.DEUTERANOMALY -> "9Ec5cQIB0Jb73gbQ3a9R5kScX"
        PaletteType.PROTANOMALY -> "44uBEX9ID9131TMG3yH7T2P1Q"
        PaletteType.TRITANOMALY -> "28yi3LJWYMbA7v87RMAEznISy"
        PaletteType.WHITEBOARD -> "tempWhiteboard"
        PaletteType.RECRUIT -> "9QWud4Mj5C4jYp1hOA91b6G0L"
        PaletteType.INVESTIGATOR -> "1UdIWr97JGtOoRszQHpBYfK5U"
        PaletteType.PRIVATE_INVESTIGATOR -> "1oF0ZF78LqA64zRyn8ozgMqLu"
        PaletteType.DETECTIVE -> "5T2mB6W1370DEc3cnYN2bI0gT"
        PaletteType.TECHNICIAN -> "9t44qIYH7UU3EPwvgXEO6309E"
        PaletteType.SPECIALIST -> "9Uo9I6AmpW2XbhHDIxCP4ppds"
        PaletteType.ANALYST -> "E2m4fr5Vp6dv07JnWE5Q3T08D"
        PaletteType.AGENT -> "6BL0zB7jGGye94j45hq3wtwVq"
        PaletteType.OPERATOR -> "8No78zJ6oUSBLS818jH10Seni"
        PaletteType.COMMISSIONER -> "4p77qstew9oX20l4X5O0J010k"
        PaletteType.EASTER -> "B6xTXIXttQUqW02Skd094BY15"
        PaletteType.HALLOWEEN_23 -> "EOkBD2aJZhOh9V821o20GFLXK"
        PaletteType.HOLIDAY_22 -> "7RF705OjW3uo39zl0aTktINHd"
        PaletteType.HOLIDAY_23 -> "0XrEhnlpA9mD9dNxrzCbNfkRe"
        PaletteType.DISCORD -> "Ex00XEBLXbh65Nk9SdYMrMfsP"
        PaletteType.CONTENT_CREATOR -> "2pe8HDVVWRfxhPUw4Xf0rhgwb"
        PaletteType.DEVELOPER -> "56iV5cnR3lRJ59uZF1mTtEz9X"
        PaletteType.TRANSLATOR -> "e6nDE683SE7Wg9dRPJeN61CD6"
        PaletteType.WINNER -> "8xkSw89x50Zwgr4tGd6z5X0a7"
        PaletteType.ARTIST -> "88kEPt48wGs0ZxR4n7gtM3OTe"
        PaletteType.STRATAGEM_HERO -> "tempStratHero"
    }

val LocalDefaultPalette = PaletteType.CLASSIC