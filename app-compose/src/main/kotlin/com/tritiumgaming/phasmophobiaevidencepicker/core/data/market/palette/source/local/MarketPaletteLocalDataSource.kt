package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.CLASSIC
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.MONOCHROMACY
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.DEUTERANOMALY
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.PROTANOMALY
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.TRITANOMALY
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.RECRUIT
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.INVESTIGATOR
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.PRIVATE_INVESTIGATOR
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.DETECTIVE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.TECHNICIAN
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.SPECIALIST
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.ANALYST
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.AGENT
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.OPERATOR
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.COMMISSIONER
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.EASTER
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.HALLOWEEN_23
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.HOLIDAY_22
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.HOLIDAY_23
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.DISCORD
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.CONTENT_CREATOR
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.DEVELOPER
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.TRANSLATOR
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.WINNER
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.ARTIST
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.WHITEBOARD
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteResources.PaletteType.STRATAGEM_HERO
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.LocalPaletteDataSource

class MarketPaletteLocalDataSource :
    LocalPaletteDataSource<Map<String, PaletteType>> {

    private val localPaletteResources: Map<String, PaletteType> = mapOf(
        Pair("CzjtxSbXRwIpX8SYR0ttngAND", CLASSIC),
        Pair("ey6VbQN5tx0MgR8tw4iJq3J8L", MONOCHROMACY),
        Pair("9Ec5cQIB0Jb73gbQ3a9R5kScX", DEUTERANOMALY),
        Pair("44uBEX9ID9131TMG3yH7T2P1Q", PROTANOMALY),
        Pair("28yi3LJWYMbA7v87RMAEznISy", TRITANOMALY),
        Pair("9QWud4Mj5C4jYp1hOA91b6G0L", RECRUIT),
        Pair("1UdIWr97JGtOoRszQHpBYfK5U", INVESTIGATOR),
        Pair("1oF0ZF78LqA64zRyn8ozgMqLu", PRIVATE_INVESTIGATOR),
        Pair("5T2mB6W1370DEc3cnYN2bI0gT", DETECTIVE),
        Pair("9t44qIYH7UU3EPwvgXEO6309E", TECHNICIAN),
        Pair("9Uo9I6AmpW2XbhHDIxCP4ppds", SPECIALIST),
        Pair("E2m4fr5Vp6dv07JnWE5Q3T08D", ANALYST),
        Pair("6BL0zB7jGGye94j45hq3wtwVq", AGENT),
        Pair("8No78zJ6oUSBLS818jH10Seni", OPERATOR),
        Pair("4p77qstew9oX20l4X5O0J010k", COMMISSIONER),
        Pair("B6xTXIXttQUqW02Skd094BY15", EASTER),
        Pair("EOkBD2aJZhOh9V821o20GFLXK", HALLOWEEN_23),
        Pair("7RF705OjW3uo39zl0aTktINHd", HOLIDAY_22),
        Pair("0XrEhnlpA9mD9dNxrzCbNfkRe", HOLIDAY_23),
        Pair("Ex00XEBLXbh65Nk9SdYMrMfsP", DISCORD),
        Pair("2pe8HDVVWRfxhPUw4Xf0rhgwb", CONTENT_CREATOR),
        Pair("56iV5cnR3lRJ59uZF1mTtEz9X", DEVELOPER),
        Pair("e6nDE683SE7Wg9dRPJeN61CD6", TRANSLATOR),
        Pair("8xkSw89x50Zwgr4tGd6z5X0a7", WINNER),
        Pair("88kEPt48wGs0ZxR4n7gtM3OTe", ARTIST),
        Pair("tempWhiteboard", WHITEBOARD),
        Pair("tempStratHero", STRATAGEM_HERO),
    )

    override fun getPalettes(): Result<Map<String, PaletteType>> =
        Result.success(localPaletteResources)

}
