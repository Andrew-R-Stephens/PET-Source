package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.newsletter

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoutes
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.NewsAlert
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.mainmenus.MainMenuScreen

@Composable
@Preview
private fun NewsInboxesScreenPreview() {
    SelectiveTheme(
        theme = Non_Colorblind,
        typography = Classic
    ) {
        Surface(
            color = LocalPalette.current.surface.color
        ) {
            NewsInboxesScreen()
        }
    }
}

@Composable
fun NewsInboxesScreen(
    //content: @Composable () -> Unit
    navController: NavController = rememberNavController()
) {

    MainMenuScreen (
        content = {
            NewsInboxesContent(
                navController = navController,
                onBack = { navController.popBackStack() }
            )
        }
    )

}


@Composable
private fun NewsInboxesContent(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    onBack: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationHeaderComposable(
            NavHeaderComposableParams(
                leftType = PETImageButtonType.BACK,
                rightType = PETImageButtonType.NONE,
                centerTitleRes = R.string.messagecenter_inboxestitle_label,
                leftOnClick = onBack
            )
        )

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {

            val inboxTypes = NewsletterViewModel.InboxType.entries.toTypedArray()

            items(inboxTypes) { inboxType ->

                InboxCard(
                    inboxType.getName(LocalContext.current),
                    inboxType.getIcon(LocalContext.current),
                    isActive = newsletterViewModel.inboxMessageList[inboxType.id]?.compareDates() ?: false,
                    onClick = {
                        navController.navigate(
                            route = "${NavRoutes.SCREEN_NEWSLETTER_MESSAGES.route}/${inboxType.id}") {
                        }
                    }
                )

            }

        }
    }

    //AdmobBanner()
}

@Composable
private fun InboxCard(
    title: String = "temp",
    @DrawableRes icon: Int = R.drawable.ic_notify,
    isActive: Boolean = true,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = Modifier
            .padding(8.dp),
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(CornerSize(16.dp)),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            NewsAlert(
                modifier = Modifier
                    .size(98.dp),
                isActive = isActive,
                icon
            )

            AutoResizedText(
                modifier = Modifier
                    .weight(1f),
                text = title,
                style = LocalTypography.current.primary.regular,
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center
            )

        }

    }
}

@Composable
@Preview
private fun InboxButtonPreview() {
    SelectiveTheme(
        theme = Non_Colorblind,
        typography = Classic
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InboxCard("test")
            InboxCard("test")
            InboxCard("test")
        }
    }
}