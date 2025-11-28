package com.tritiumgaming.feature.home.ui.account.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.common.prefabicon.AccountIcon
import com.tritiumgaming.core.ui.common.prefabicon.AccountIconPrimaryContent
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.mappers.ToComposable
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.core.ui.mappers.IconResources

@Composable
@Preview
private fun AccountDetailsPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountBannerExpanded()
    }
}

@Composable
fun AccountBannerExpanded(
    modifier: Modifier = Modifier,
    credits: Int = 100
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            AccountCredits(
                credits = credits
            )
        }

        AccountBannerIcon()
    }

}

@Composable
fun AccountBannerComposite(
    modifier: Modifier = Modifier,
    credits: Int = 100
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountBannerIcon()

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            AccountCredits(
                credits = credits
            )
        }

    }

}

@Composable
private fun AccountBannerIcon(
    modifier: Modifier = Modifier
) {
    AccountIcon(
        modifier = modifier
            .size(48.dp),
        borderColor = LocalPalette.current.onSurface,
        backgroundColor = LocalPalette.current.surfaceContainer,
        placeholder = {
            IconResources.IconResource.PERSON.ToComposable(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                colors = IconVectorColors.defaults(
                    fillColor = LocalPalette.current.surface,
                    strokeColor = LocalPalette.current.onSurface
                )
            )
        },
        content = {
            val authUser = Firebase.auth.currentUser
            val authUserName = authUser?.displayName ?: ""

            val names: List<String?> = (authUserName).split(" ")

            AccountIconPrimaryContent(
                firstName = names.getOrNull(0) ?: "",
                lastName = names.getOrNull(1) ?: "",
                textStyle = LocalTypography.current.primary.bold.copy(
                    color = LocalPalette.current.onSurface,
                    textAlign = TextAlign.Center,
                    shadow = Shadow(
                        color = LocalPalette.current.shadow,
                        blurRadius = 8f
                    ),
                )
            ) {
                Image(
                    painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    alpha = .75f
                )
            }
        }
    )
}