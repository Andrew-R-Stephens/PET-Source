package com.tritiumgaming.feature.marketplace.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel

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
fun MarketplaceHomeScreen(
    modifier: Modifier,
    navController: NavHostController,
    marketplaceViewModel: MarketplaceViewModel,
    content: @Composable () -> Unit
) {
    MarketplaceHomeContent(
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun MarketplaceHomeContent(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {


        content()
    }
}

@DevicePreviews
@Composable
@Preview
private fun Preview() {
    MarketplaceHomeContent(
        modifier = Modifier,
        content = {

        }
    )
}
