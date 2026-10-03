package com.tritiumgaming.feature.start.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.navigation.NavRoute
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.ButtonScratchedIcon
import com.tritiumgaming.core.ui.icon.impl.composite.LanguageIcon
import com.tritiumgaming.core.ui.mapper.ToComposable
import com.tritiumgaming.core.ui.mappers.IconResources.IconResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.admob.BannerAd

@Composable
internal fun StartContent(
    inboxNotificationState: Boolean,
    canRequestReview: Boolean,
    currentLanguage: String,
    currentUser: FirebaseUser?,
    onNavigate: (route: String) -> Unit,
    onOpenPatreon: () -> Unit,
    onOpenDiscord: () -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            HeaderNavBar(
                inboxNotificationState = inboxNotificationState,
                canRequestReview = canRequestReview,
                currentUser = currentUser,
                onNavigate = onNavigate,
                onOpenPatreon = onOpenPatreon,
                onOpenDiscord = onOpenDiscord
            )
        }

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                StartContentPortrait(
                    modifier = Modifier.weight(1f, true),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                StartContentLandscape(
                    modifier = Modifier.weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.TABLET_PORTRAIT -> {
                StartContentPortrait(
                    modifier = Modifier.weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.TABLET_LANDSCAPE -> {
                StartContentLandscape(
                    modifier = Modifier.weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
            DeviceConfiguration.DESKTOP -> {
                StartContentLandscape(
                    modifier = Modifier.weight(1f),
                    currentLanguage = currentLanguage,
                    onNavigate = onNavigate
                )
            }
        }

        BannerAd(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .heightIn(min = 50.dp),
            adId = stringResource(R.string.ad_banner_1)
        )
    }
}

@Composable
private fun StartContentPortrait(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    onNavigate: (route: String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LogoSection(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .weight(1f, false)
        )

        Spacer(modifier = Modifier.height(32.dp))

        StartSection(
            modifier = Modifier
                .weight(1f, false),
            currentLanguage = currentLanguage,
            onNavigate = onNavigate
        )
    }
}

@Composable
internal fun StartContentLandscape(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    onNavigate: (route: String) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LogoSection(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .weight(1f, false)
        )

        StartSection(
            modifier = Modifier.weight(1f),
            currentLanguage = currentLanguage,
            onNavigate = onNavigate
        )
    }
}

@Composable
private fun LogoSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        IconResource.ICON_LOGO_APP.ToComposable(
            modifier = Modifier
                .widthIn(max = 256.dp)
                .weight(1f, false)
                .aspectRatio(1f),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        )

        BasicText(
            modifier = Modifier
                .widthIn(max = 256.dp)
                .fillMaxWidth()
                .height(36.dp),
            text = stringResource(R.string.titlescreen_description).uppercase(),
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.primary,
                textAlign = TextAlign.Center
            ),
            maxLines = 2,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 12.sp, maxFontSize = 36.sp, stepSize = 2.sp
            )
        )
    }
}

@Composable
private fun StartSection(
    modifier: Modifier = Modifier,
    currentLanguage: String,
    onNavigate: (route: String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StartButton {
            onNavigate(NavRoute.SCREEN_INVESTIGATION.route)
        }

        LanguageButton(
            currentLanguage = currentLanguage
        ) {
            onNavigate(NavRoute.SCREEN_LANGUAGE.route)
        }
    }
}

@Composable
private fun LanguageButton(
    currentLanguage: String,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LanguageIcon(
            modifier = Modifier.size(48.dp),
            colors = IconVectorColors.defaults(
                fillColor = LocalPalette.current.surfaceContainerLow,
                strokeColor = LocalPalette.current.onSurface
            )
        ) {
            onClick()
        }

        BasicText(
            modifier = Modifier.wrapContentSize(),
            text = currentLanguage,
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.onSurface,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            ),
            maxLines = 1
        )
    }
}

@Composable
private fun StartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .width(IntrinsicSize.Max),
        onClick = { onClick() },
        enabled = true,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            contentAlignment = Alignment.Center,
        ) {
            ButtonScratchedIcon(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(IntrinsicSize.Max),
                colors = IconVectorColors.defaults(
                    fillColor = LocalPalette.current.surfaceContainerLow,
                    strokeColor = LocalPalette.current.onSurface
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .fillMaxHeight(.65f),
                contentAlignment = Alignment.Center,
                propagateMinConstraints = true
            ) {
                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = stringResource(R.string.titlescreen_button).uppercase(),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.onSurface,
                        textAlign = TextAlign.Center,
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 5.sp
                    )
                )
            }
        }
    }
}
