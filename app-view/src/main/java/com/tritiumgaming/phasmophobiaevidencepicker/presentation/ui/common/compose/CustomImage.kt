package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.common.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.common.AccountIcon

@Preview
@Composable
fun TestLanguageIcon() {
    AccountIcon()
}

@Composable
@Preview
fun LanguageIcon() {
    val size = 48.dp

    Box(
        modifier = Modifier
            .size(size)
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
@Preview
fun DiscordIcon() {
    val size = 48.dp

    Box(
        modifier = Modifier
            .size(size)
    ) {
        val contentScale = ContentScale.Inside
        val contentDescription = "Outer Box"

        Image(
            painter = painterResource(id = R.drawable.ic_discord),
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
