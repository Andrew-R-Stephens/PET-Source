package com.tritiumgaming.feature.marketplace.ui.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.ShopCostIcon
import com.tritiumgaming.core.ui.icon.impl.composite.AccountIcon
import com.tritiumgaming.core.ui.icon.impl.composite.AccountIconPrimaryContent
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.core.ui.mappers.IconResources

@Composable
fun AccountBanner(
    modifier: Modifier = Modifier,
    authenticated: Boolean = false,
    name: String = "",
    credits: Int = 100,
    onNavigate: (String) -> Unit = {}
) {

    if(authenticated) {
        AccountBannerExpanded(
            modifier = modifier,
            name = name,
            credits = credits,
            onNavigate = onNavigate
        )
    } else {
        AccountBannerLogin(
            modifier = modifier,
            onNavigate = onNavigate
        )
    }

}

@Composable
private fun AccountBannerComposite(
    modifier: Modifier = Modifier,
    name: String = "",
    credits: Int = 100,
    onNavigate: (String) -> Unit = {}
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AccountBannerIcon(
            modifier = Modifier,
            name = name,
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
fun AccountBannerExpanded(
    modifier: Modifier = Modifier,
    name: String = "",
    credits: Int = 100,
    onNavigate: (String) -> Unit = {}
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                AccountCredits(
                    credits = credits
                )
            }

            AccountBannerIcon(
                modifier = Modifier
                    .clickable(onClick = { onNavigate(NavRoute.SCREEN_ACCOUNT_OVERVIEW.route) }),
                name = name,
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
}

@Composable
fun AccountBannerLogin(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = LocalPalette.current.surfaceContainer,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically)
        ) {
            Surface(
                modifier = Modifier,
                color = LocalPalette.current.secondaryContainer,
                shape = CircleShape
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "",
                    tint = LocalPalette.current.secondary
                )
            }

            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                text = stringResource(R.string.marketplace_error_login_required),
                color = LocalPalette.current.primary,
                style = LocalTypography.current.quaternary.bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .wrapContentWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = LocalPalette.current.onPrimaryContainer,
                    containerColor = LocalPalette.current.primaryContainer
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    onNavigate(NavRoute.SCREEN_ACCOUNT_OVERVIEW.route)
                }
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = stringResource(R.string.account_button_login),
                    style = LocalTypography.current.quaternary.bold,
                    fontSize = 18.sp
                )
            }
        }

    }
}

@Composable
fun AccountCredits(
    modifier: Modifier = Modifier,
    containerColor: Color = LocalPalette.current.surfaceContainer,
    iconColor: Color = LocalPalette.current.onSurface,
    textColor: Color = LocalPalette.current.onSurfaceVariant,
    credits: Int = 100
) {

    Card(
        modifier = modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            contentColor = containerColor,
            containerColor = containerColor
        )
    ) {

        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                alignment = Alignment.CenterHorizontally,
                space = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ShopCostIcon(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(48.dp)
                    .padding(4.dp),
                colors = IconVectorColors(
                    strokeColor = iconColor,
                    fillColor = iconColor
                )
            )

            Text(
                text = credits.toString(),
                fontSize = 24.sp,
                color = textColor,
                maxLines = 1,
                modifier = Modifier
                    .wrapContentWidth()
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

@Composable
@Preview
private fun AccountCreditsPreview() {

    LocalThemeProvider {
        AccountCredits()
    }

}

@Composable
@Preview
private fun AccountBannerExpandedPreview() {
    LocalThemeProvider {
        AccountBannerExpanded()
    }
}

@Composable
@Preview
private fun AccountBannerCompositePreview() {
    LocalThemeProvider {
        AccountBannerComposite()
    }
}

@Composable
@Preview
private fun AccountBannerIconPreview() {
    LocalThemeProvider {
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
@Preview
private fun AccountBannerLoginPreview() {
    LocalThemeProvider {
        AccountBannerLogin(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
    }
}
