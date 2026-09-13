package com.tritiumgaming.feature.marketplace.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.Arrow60LeftIcon
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderSideButton
import com.tritiumgaming.feature.marketplace.ui.common.components.AccountBanner
import com.tritiumgaming.feature.marketplace.ui.common.components.AccountBannerSize

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@Composable
fun MarketplaceScreen(
    modifier: Modifier,
    navController: NavHostController,
    earnedCredits: Int = 0,
    showRewardButton: Boolean = false,
    onClickRewardButton: () -> Unit = {},
    accountContent: @Composable (Modifier) -> Unit,
    storeContent: @Composable (Modifier) -> Unit
) {
    val user = if(!LocalInspectionMode.current)
        Firebase.auth.currentUser else null

    MarketplaceContent(
        modifier = modifier,
        authenticated = user != null,
        userName = user?.displayName ?: "",
        rewardCredits = earnedCredits,
        showRewardButton = showRewardButton,
        onNavigateTo = { route ->
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onNavigateBack = {
            navController.popBackStack()
        },
        onEarnCredits = {
            onClickRewardButton()
        },
        accountContent = { modifier ->
            accountContent(modifier)
        },
        storeContent = { modifier ->
            storeContent(modifier)
        }
    )
}

@Composable
fun MarketplaceContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    userName: String = "",
    rewardCredits: Int = 0,
    showRewardButton: Boolean = false,
    onNavigateTo: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onEarnCredits: () -> Unit = {},
    accountContent: @Composable (Modifier) -> Unit,
    storeContent: @Composable (Modifier) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val navigationHeader: @Composable (Modifier) -> Unit = @Composable { modifier ->
        NavigationHeaderComposable(
            modifier = modifier,
            leftContent = { outerModifier ->
                NavigationHeaderSideButton(
                    modifier = outerModifier,
                    iconContent = { iconModifier ->
                        Arrow60LeftIcon(
                            modifier = iconModifier,
                            colors = IconVectorColors.defaults(
                                fillColor = LocalPalette.current.onSurface
                            )
                        )
                    }
                ) { onNavigateBack() }
            },
            rightContent = { outerModifier ->
                NavigationHeaderSideButton(
                    modifier = outerModifier,
                )
            },
            centerContent = { outerModifier ->
                NavigationHeaderCenter(
                    modifier = outerModifier,
                    textContent = { modifier ->
                        BasicText(
                            modifier = modifier,
                            text = stringResource(R.string.marketplace_title),
                            style = LocalTypography.current.primary.regular.copy(
                                color = LocalPalette.current.primary,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 1,
                            autoSize = TextAutoSize.StepBased(
                                minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
                        )
                    }
                )
            }
        )

    }

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
             DeviceConfiguration.TABLET_PORTRAIT -> {
            PortraitContent(
                modifier = modifier,
                authenticated = authenticated,
                userName = userName,
                credits = rewardCredits,
                showButton = showRewardButton,
                navigationHeader = { modifier -> navigationHeader(modifier) },
                onNavigate = onNavigateTo,
                onEarnCredits = onEarnCredits,
                content = { modifier -> storeContent(modifier) }
            )
        }
        else -> {
            LandscapeContent(
                modifier = modifier,
                authenticated = authenticated,
                userName = userName,
                credits = rewardCredits,
                navigationHeader = { modifier -> navigationHeader(modifier) },
                onNavigate = onNavigateTo,
                onEarnCredits = onEarnCredits,
                content = { modifier -> storeContent(modifier) }
            )
        }
    }
}

@Composable
fun PortraitContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    userName: String = "",
    credits: Int = 0,
    showButton: Boolean = false,
    navigationHeader: @Composable (Modifier) -> Unit,
    onNavigate: (String) -> Unit = {},
    onEarnCredits: () -> Unit = {},
    content: @Composable (Modifier) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top)
    ) {

        navigationHeader(
            Modifier
                .fillMaxWidth()
        )

        AccountBanner(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            authenticated = authenticated,
            name = userName,
            credits = credits,
            showButton = showButton,
            variant = AccountBannerSize.EXPANDED,
            onEarnCredits = onEarnCredits,
            onNavigate = onNavigate
        )

        content(Modifier)
    }
}

@Composable
fun LandscapeContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    userName: String = "",
    credits: Int = 0,
    showButton: Boolean = false,
    navigationHeader: @Composable (Modifier) -> Unit,
    onNavigate: (String) -> Unit = {},
    onEarnCredits: () -> Unit = {},
    content: @Composable (Modifier) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top)
    ) {

        navigationHeader(
            Modifier
                .fillMaxWidth()
        )

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.CenterHorizontally)
        ) {
            AccountBanner(
                modifier = Modifier
                    .weight(1f),
                authenticated = authenticated,
                credits = credits,
                showButton = showButton,
                name = userName,
                variant = AccountBannerSize.COMPOSITE,
                onEarnCredits = onEarnCredits,
                onNavigate = onNavigate
            )

            content(
                Modifier
                    .then(
                        if (deviceConfiguration == DeviceConfiguration.MOBILE_LANDSCAPE)
                            Modifier.widthIn(max = 600.dp)
                        else Modifier.fillMaxWidth(0.7f)
                    )
                    .fillMaxHeight()
            )

        }
    }

}

@DevicePreviews
@Composable
@Preview
private fun Preview() {
    LocalThemeProvider {
        MarketplaceContent(
            modifier = Modifier,
            storeContent = { modifier ->
                Box(
                    modifier = modifier
                        .width(IntrinsicSize.Min)
                        .background(Color.Green),
                    contentAlignment = Alignment.Center,
                ) {}
            },
            accountContent = {

            }
        )
    }

}
