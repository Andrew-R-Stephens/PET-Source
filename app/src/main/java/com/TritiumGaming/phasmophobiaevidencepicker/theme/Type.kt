package com.tritiumgaming.phasmophobiaevidencepicker.theme

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import com.tritiumgaming.phasmophobiaevidencepicker.R

class Type {

    companion object {

        private val Cuyabra = FontFamily(
            Font(R.font.cuyabra_regular, FontWeight.W400),
            Font(R.font.cuyabra_regular, FontWeight.W700)
        )

        private val DigitalDream = FontFamily(
            Font(R.font.digitaldream_regular, FontWeight.W400),
            Font(R.font.digitaldream_narrow, FontWeight.W400, FontStyle.Italic),
            Font(R.font.digitaldream_fat, FontWeight.W700),
            Font(R.font.digitaldream_fat_narrow, FontWeight.W700, FontStyle.Italic),
        )

        private val EastSeaDokdo = FontFamily(
            Font(R.font.eastseadokdo_regular, FontWeight.W400)
        )

        private val Geo = FontFamily(
            Font(R.font.geo_regular, FontWeight.W400),
            Font(R.font.geo_regular, FontWeight.W700)
        )

        private val JetbrainsMono = FontFamily(
            Font(R.font.jetbrainsmono_thin, FontWeight.W100),
            Font(R.font.jetbrainsmono_regular, FontWeight.W400),
            Font(R.font.jetbrainsmono_italic, FontWeight.W400),
            Font(R.font.jetbrainsmono_extrabold, FontWeight.W800)
        )

        private val LazyDog = FontFamily(
            Font(R.font.lazydog_regular, FontWeight.W400),
            Font(R.font.lazydog_regular, FontWeight.W700)
        )

        private val LongCang = FontFamily(
            Font(R.font.longcang_regular, FontWeight.W400),
            Font(R.font.longcang_regular, FontWeight.W700)
        )

        private val Neucha = FontFamily(
            Font(R.font.neucha_regular, FontWeight.W400),
            Font(R.font.neucha_regular, FontWeight.W700)
        )

        private val NewTegomin = FontFamily(
            Font(R.font.new_tegomin_regular, FontWeight.W400),
            Font(R.font.new_tegomin_regular, FontWeight.W700)
        )

        private val Norse = FontFamily(
            Font(R.font.norse_regular, FontWeight.W400),
            Font(R.font.norse_bold, FontWeight.W700)
        )

        private val RobotoMono = FontFamily(
            Font(R.font.roboto_mono, FontWeight.W400),
            Font(R.font.roboto_mono, FontWeight.W500),
            Font(R.font.roboto_mono, FontWeight.W700)
        )

        private val SerifMonospace = FontFamily.Monospace

        private val YggDrasil = FontFamily(
            Font(R.font.yggdrasil_regular, FontWeight.W400)
        )

        val DefaultTextStyle = TextStyle(
            //fontFamily = Norse,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            ),
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.None
            )
        )

        val CuyabraTextStyle = DefaultTextStyle.copy(
            fontFamily = Cuyabra
        )

        val DigitalDreamTextStyle = DefaultTextStyle.copy(
            fontFamily = DigitalDream
        )

        val EastSeaDokdoTextStyle = DefaultTextStyle.copy(
            fontFamily = EastSeaDokdo
        )

        val GeoTextStyle = DefaultTextStyle.copy(
            fontFamily = Geo
        )

        val JetbrainsMonoTextStyle = DefaultTextStyle.copy(
            fontFamily = JetbrainsMono
        )

        val LazyDogTextStyle = DefaultTextStyle.copy(
            fontFamily = LazyDog
        )

        val LongCangTextStyle = DefaultTextStyle.copy(
            fontFamily = LongCang
        )

        val NeuchaTextStyle = DefaultTextStyle.copy(
            fontFamily = Neucha
        )

        val NewTegominTextStyle = DefaultTextStyle.copy(
            fontFamily = NewTegomin
        )

        val NorseTextStyle = DefaultTextStyle.copy(
            fontFamily = Norse
        )

        val RobotoMonoTextStyle = DefaultTextStyle.copy(
            fontFamily = RobotoMono
        )

        val TypewriterTextStyle = DefaultTextStyle.copy(
            fontFamily = SerifMonospace
        )

        val YggDrasilTextStyle = DefaultTextStyle.copy(
            fontFamily = YggDrasil
        )

    }

}