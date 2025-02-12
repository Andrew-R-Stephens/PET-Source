package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography

@Composable
@Preview
private fun AccountIconPreview() {

    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountIcon()
    }

}

@Composable
fun AccountIcon(
    authUser: FirebaseUser? = null
) {
    val borderColor = LocalPalette.current.primary.color
    val backgroundColorResId = LocalPalette.current.surface.onColor

    val scaleSize = 48
    val size = scaleSize.dp
    val borderWidth = 1f.coerceAtLeast(4f / 200f * scaleSize).dp

    val rememberAuthUser by remember{ mutableStateOf(authUser) }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColorResId)
            .border(borderWidth, borderColor, CircleShape)
    ) {
        val contentScale = ContentScale.Inside

        if (rememberAuthUser == null) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "",
                    contentScale = contentScale
                )
            }
        } else {
            Image(
                painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                contentDescription = "",
                contentScale = contentScale
            )
        }
    }
}
