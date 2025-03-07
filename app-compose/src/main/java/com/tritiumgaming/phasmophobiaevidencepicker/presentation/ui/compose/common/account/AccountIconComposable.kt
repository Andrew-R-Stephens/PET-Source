package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedStyleType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography

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

    val rememberAuthUserName by remember{ mutableStateOf(Firebase.auth.currentUser?.displayName) }

    val name: List<String?> = (rememberAuthUserName)?.split(" ") ?: listOf()

    val firstNameInitial: String =
        try { name[0]?.get(0).toString() } catch (e: Exception) { e.printStackTrace(); "" }
    val lastNameInitial: String =
        try { name[1]?.get(0).toString() } catch (e: Exception) { e.printStackTrace(); "" }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColorResId)
            .border(borderWidth, borderColor, CircleShape)
    ) {
        val contentScale = ContentScale.Inside

        if (Firebase.auth.currentUser == null) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                    contentDescription = "",
                    contentScale = contentScale
                )

                AutoResizedText(
                    containerModifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    contentModifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    text = "$firstNameInitial$lastNameInitial",
                    textAlign = TextAlign.Center,
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.primary.regular,
                    borderStyle = LocalTypography.current.primary.regular.copy(
                        drawStyle = Stroke(
                            miter = 1f,
                            width = 2f,
                            join = StrokeJoin.Bevel,
                        ),
                        color = LocalPalette.current.surface.color
                    ),
                    autoResizeStyle = AutoResizedStyleType.SQUEEZE
                )
            }
        }
    }
}
