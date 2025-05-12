package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.account.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography

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
fun AccountIcon() {

    val borderColor = LocalPalette.current.primary.color
    val backgroundColorResId = LocalPalette.current.surface.onColor

    val scaleSize = 48
    val size = scaleSize.dp
    val borderWidth = 1f.coerceAtLeast(4f / 200f * scaleSize).dp

    val authUserName = Firebase.auth.currentUser?.displayName
    //val rememberAuthUserName by remember{ mutableStateOf(Firebase.auth.currentUser?.displayName) }

    val name: List<String?> = (/*rememberAuthUserName*/authUserName)?.split(" ") ?: listOf()

    val firstNameInitial: String =
        try { name[0]?.get(0).toString() } catch (e: Exception) { e.printStackTrace(); "" }
    val lastNameInitial: String =
        try { name[1]?.get(0).toString() } catch (e: Exception) { e.printStackTrace(); "" }

    val rememberInitials by remember{ mutableStateOf("$firstNameInitial$lastNameInitial") }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColorResId)
            .border(borderWidth, borderColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val contentScale = ContentScale.Inside

        if (Firebase.auth.currentUser == null) {

            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "",
                contentScale = contentScale
            )

        } else {

            Image(
                painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                contentDescription = "",
                contentScale = contentScale,
                alpha = .5f
            )

            BasicText(
                modifier = Modifier
                    .fillMaxSize(.9f),
                text = rememberInitials,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )

        }
    }
}
