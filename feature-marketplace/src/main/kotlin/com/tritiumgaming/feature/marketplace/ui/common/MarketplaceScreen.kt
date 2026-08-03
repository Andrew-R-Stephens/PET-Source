package com.tritiumgaming.feature.marketplace.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.common.components.AccountBannerExpanded

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
    content: @Composable () -> Unit
) {

    val user = if(!LocalInspectionMode.current)
        Firebase.auth.currentUser else null
    val credits by marketplaceViewModel.accountCreditsUiState.collectAsState()

    MarketplaceContent(
        modifier = modifier
            .padding(8.dp),
        userName = user?.displayName ?: "",
        credits = credits.earnedCredits
    ) {
        content()
    }
}

@Composable
fun MarketplaceContent(
    modifier: Modifier,
    userName: String = "",
    credits: Int = 0,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Top)
    ) {
        AccountDetails(
            name = userName,
            credits = credits
        )

        content()
    }
}


@Composable
private fun AccountDetails(
    name: String = "",
    credits: Int = 0
) {

    AccountBannerExpanded(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        credits = credits,
        name = name
    )

}

@DevicePreviews
@Composable
@Preview
private fun Preview() {
    LocalThemeProvider {
        MarketplaceContent(
            modifier = Modifier,
            content = {

            }
        )
    }

}
