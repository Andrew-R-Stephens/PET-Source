package com.tritiumgaming.feature.account.ui.component

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.icon.impl.composite.AccountIcon
import com.tritiumgaming.core.ui.icon.impl.composite.AccountIconPrimaryContent
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.shared.core.ui.mappers.IconResources

@Composable
@Preview
private fun AccountBannerExpandedPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountBannerExpanded()
    }
}

@Composable
@Preview
private fun AccountBannerCompositePreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountBannerComposite()
    }
}

@Composable
@Preview
private fun AccountBannerIconPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        AccountBannerIcon(
            modifier = Modifier,
            name = "A S",
            icon = { modifier ->
                Image(
                    modifier = modifier,
                    painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    alpha = .75f
                )
            }
        )
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

        if (!LocalInspectionMode.current) {
            AccountBannerIcon()
        }
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
        if (!LocalInspectionMode.current) {
            val user = Firebase.auth.currentUser
            
            AccountBannerIcon(
                modifier = Modifier,
                name = user?.displayName,
                icon = { modifier ->
                    Image(
                        modifier = modifier,
                        painter = painterResource(id = LocalPalette.current.extrasFamily.badge),
                        contentDescription = "",
                        contentScale = ContentScale.Inside,
                        alpha = .75f
                    )
                }
            )
        }

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
    modifier: Modifier = Modifier,
    name: String? = null,
    icon: @Composable (Modifier) -> Unit = {}
) {
    AccountIcon(
        modifier = modifier
            .size(48.dp),
        borderColor = LocalPalette.current.onSurface,
        backgroundColor = LocalPalette.current.surfaceContainer,
        placeholder = {
            if(name == null) {
                IconResources.IconResource.PERSON.ToComposable(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    colors = IconVectorColors.defaults(
                        fillColor = LocalPalette.current.surface,
                        strokeColor = LocalPalette.current.onSurface
                    )
                )
            }
        },
        content = {
            val names: List<String?> = (name)?.split(" ") ?: emptyList()

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
                icon(Modifier.alpha(.7f))
            }
        }
    )
}