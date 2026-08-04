package com.tritiumgaming.feature.marketplace.ui.common

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.common.components.AccountBanner

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
    marketplaceViewModel: MarketplaceViewModel,
    content: @Composable (Modifier) -> Unit
) {

    val context = LocalContext.current

    val user = if(!LocalInspectionMode.current)
        Firebase.auth.currentUser else null
    val credits by marketplaceViewModel.accountCreditsUiState.collectAsState()

    MarketplaceContent(
        modifier = modifier
            .padding(8.dp),
        authenticated = user != null,
        userName = user?.displayName ?: "",
        credits = credits.earnedCredits,
        onNavigate = { route ->
            navController.navigate(route)
        },
        onEarnCredits = {
            marketplaceViewModel.addCredits(
                credits = 100,
                onSuccess = {
                    Toast.makeText(context, "Credits Earned",
                        Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(context, "Error! $it",
                        Toast.LENGTH_SHORT).show()
                }
            )
        }
    ) { modifier ->
        content(modifier)
    }
}

@Composable
fun MarketplaceContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    userName: String = "",
    credits: Int = 0,
    onNavigate: (String) -> Unit = {},
    onEarnCredits: () -> Unit = {},
    content: @Composable (Modifier) -> Unit,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
             DeviceConfiguration.TABLET_PORTRAIT -> {
            PortraitContent(
                modifier = modifier,
                authenticated = authenticated,
                userName = userName,
                credits = credits,
                onNavigate = onNavigate,
                onEarnCredits = onEarnCredits,
                content = { modifier -> content(modifier) }
            )
        }
        else -> {
            LandscapeContent(
                modifier = modifier,
                authenticated = authenticated,
                userName = userName,
                credits = credits,
                onNavigate = onNavigate,
                onEarnCredits = onEarnCredits,
                content = { modifier -> content(modifier) }
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
    onNavigate: (String) -> Unit = {},
    onEarnCredits: () -> Unit = {},
    content: @Composable (Modifier) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.Top)
    ) {
        AccountDetails(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            authenticated = authenticated,
            name = userName,
            credits = credits,
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
    onNavigate: (String) -> Unit = {},
    onEarnCredits: () -> Unit = {},
    content: @Composable (Modifier) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.CenterHorizontally)
    ) {

        content(
            Modifier
                .widthIn(max = 450.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        )

        AccountDetails(
            modifier = Modifier
                .weight(1f),
            authenticated = authenticated,
            name = userName,
            credits = credits,
            onEarnCredits = onEarnCredits,
            onNavigate = onNavigate
        )

    }
}


@Composable
private fun AccountDetails(
    modifier: Modifier = Modifier,
    authenticated: Boolean = false,
    name: String = "",
    credits: Int = 0,
    onEarnCredits: () -> Unit = {},
    onNavigate: (String) -> Unit = {}
) {

    AccountBanner(
        modifier = modifier,
        authenticated = authenticated,
        credits = credits,
        name = name,
        onEarnCredits = onEarnCredits,
        onNavigate = onNavigate
    )

}

@DevicePreviews
@Composable
@Preview
private fun Preview() {
    LocalThemeProvider {
        MarketplaceContent(
            modifier = Modifier,
            content = { modifier ->
                Box(
                    modifier = modifier
                        .width(IntrinsicSize.Min),
                    contentAlignment = Alignment.Center,
                ) {

                }
            }
        )
    }

}
