package com.tritiumgaming.phasmophobiaevidencepicker.views.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.theme.palettes.LocalPalette

@Composable
fun AccountIconComposable(
    authUser: FirebaseUser? = null
) {
    val borderColor = LocalPalette.current.primary.color
    val backgroundColorResId = LocalPalette.current.surface.onColor

    val size = 48.dp
    val borderWidth = 4.dp / 200.dp * size

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColorResId)
            .border(borderWidth, borderColor, CircleShape)
    ) {
        val contentScale = ContentScale.Inside
        val contentDescription = "Outer Box"

        if (authUser == null) {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier
                    .padding(4.dp)
            )
        } else {
            Image(
                painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }
    }
}
