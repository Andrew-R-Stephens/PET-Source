package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.newsletter

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedStyleType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.NewsAlert
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.NewsletterViewModel

@Composable
@Preview
private fun NewsInboxesScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
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
                navController = navController
            ){ navController.popBackStack() }
        }
    )

}


@Composable
private fun NewsInboxesContent(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    onBack: () -> Unit = {}
) {

    /*val rememberInboxes by remember {
        mutableStateOf(
            newsletterViewModel.inboxes.value
        )
    }*/
    val rememberInboxes by newsletterViewModel.inboxes.collectAsState()

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
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
        ) {

            items(rememberInboxes.keys.toList()) {
                key: NewsletterRepository.NewsletterInboxType.InboxType ->

                val inbox = rememberInboxes[key]

                inbox?.inboxType?.let {

                    InboxCard(
                        title = it.title,
                        icon = it.icon,
                        isActive = /*inbox.messages.compareDates() ?:*/ false,
                        onClick = {
                            navController.navigate(
                                route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGES.route}/${it.id}")
                        }
                    )

                }
            }



        }

        AdmobBanner()
    }

}

@Composable
private fun InboxCard(
    modifier: Modifier = Modifier,
    title: Int = R.string.preference_general_news_link,
    @DrawableRes icon: Int = R.drawable.ic_notify,
    isActive: Boolean = true,
    onClick: () -> Unit = {}
) {

    Surface(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
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
            ) { }

            AutoResizedText(
                containerModifier = Modifier
                    .weight(1f),
                text = stringResource(title),
                style = LocalTypography.current.primary.regular,
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center,
                autoResizeStyle = AutoResizedStyleType.CONSTRAIN
            )

        }

    }
}

@Composable
@Preview
private fun InboxButtonPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InboxCard(
                title = R.string.preference_general_news_link,
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )
            InboxCard(
                title = R.string.preference_phasmophobia_changelog_link,
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )
            InboxCard(
                title = R.string.preference_pet_changelog_link,
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )
        }
    }
}