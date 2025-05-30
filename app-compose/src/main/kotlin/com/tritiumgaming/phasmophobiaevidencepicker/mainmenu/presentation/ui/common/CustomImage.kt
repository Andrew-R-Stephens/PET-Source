package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.component.AccountIcon
import org.jetbrains.annotations.TestOnly

@Preview
@Composable
@TestOnly
fun TestAccountIcon() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        AccountIcon()
    }
}

@Preview
@Composable
@TestOnly
fun TestDiscordIcon() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        DiscordIcon()
    }
}

@Composable
fun LanguageIcon(
    onClick: () -> Unit = {}
) {
    val size = 48.dp

    Box(
        modifier = Modifier
            .size(size)
            .clickable(true) { onClick() }
    ) {
        val contentScale = ContentScale.Inside
        val contentDescription = "Outer Box"

        Image(
            painter = painterResource(id = R.drawable.ic_globe),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier
                .fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.ic_translate),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.5f)
                .align(Alignment.BottomEnd)
        )

    }
}

@Composable
fun DiscordIcon(
    modifier: Modifier = Modifier
        .size(48.dp),
    tintColor: Color = LocalPalette.current.textFamily.primary
) {

    Box(
        modifier = modifier
    ) {
        val contentScale = ContentScale.Inside
        val contentDescription = "Outer Box"

        Image(
            painter = painterResource(id = R.drawable.ic_discord),
            colorFilter = ColorFilter.tint(
                tintColor,
                BlendMode.Modulate
            ),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier
                .fillMaxSize()
        )

        Image(
            painter = painterResource(id = R.drawable.ic_open_in_new),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.4f)
                .align(Alignment.BottomEnd)
        )

    }
}
