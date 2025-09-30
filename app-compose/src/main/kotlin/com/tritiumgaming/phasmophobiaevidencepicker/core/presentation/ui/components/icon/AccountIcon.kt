package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.shared.core.domain.icons.IconResources
import org.jetbrains.annotations.TestOnly

@Preview
@Composable
@TestOnly
fun TestAccountIcon() {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        AccountIcon(
            borderColor =  LocalPalette.current.textFamily.body,
            backgroundColor = LocalPalette.current.surface.onColor
        )
    }

}

@Composable
fun AccountIcon(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    borderColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
) {

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(2.dp, borderColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val contentScale = ContentScale.Inside

        if (user == null) {

            IconResources.IconResource.PERSON.ToComposable(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                colors = IconVectorColors.defaults(
                    fillColor = LocalPalette.current.background.color,
                    strokeColor = LocalPalette.current.textFamily.body
                )
            )

        } else {

            val authUserName = user.displayName

            val names: List<String?> = (authUserName)?.split(" ") ?: listOf()

            val firstNameInitial: String =
                try { names[0]?.get(0).toString() } catch (e: Exception) { e.printStackTrace(); "" }
            val lastNameInitial: String =
                try { names[1]?.get(0).toString() } catch (e: Exception) { e.printStackTrace(); "" }

            val rememberInitials by remember{ mutableStateOf("$firstNameInitial$lastNameInitial") }

            Image(
                painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                contentDescription = "",
                contentScale = contentScale,
                alpha = .5f
            )

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(4.dp)
                        .align(Alignment.Center),
                    text = rememberInitials,
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.textFamily.body,
                        textAlign = TextAlign.Center,
                        shadow = Shadow(LocalPalette.current.surface.color, blurRadius = 8f)
                    ),
                    autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
                )
            }


        }
    }
}
