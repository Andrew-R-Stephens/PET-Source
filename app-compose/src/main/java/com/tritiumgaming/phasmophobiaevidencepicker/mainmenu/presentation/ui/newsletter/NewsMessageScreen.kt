package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.newsletter

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.NewsletterViewModel


@Composable
@Preview
private fun NewsMessageScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface.color
        ) {
            NewsMessageScreen(
                inboxID = NewsletterInboxType.NewsletterInboxTypeDTO.PET.id,
                messageID = 0
            )
        }
    }
}

@Composable
fun NewsMessageScreen(
    navController: NavHostController = rememberNavController(),
    inboxID: Int,
    messageID: Int
) {

    MainMenuScreen {

        NewsMessageContent(
            navController = navController,
            inboxID = inboxID,
            messageID = messageID
        )

    }
}

@Composable
private fun NewsMessageContent(
    navController: NavHostController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    inboxID: Int,
    messageID: Int
) {

    /*val rememberInboxID by remember {
        mutableIntStateOf(inboxID)
    }

    val rememberMessageID by remember {
        mutableIntStateOf(messageID)
    }

    val messages = newsletterViewModel.inboxes.collectAsStateWithLifecycle()
    val i: NewsletterInbox = messages.value.values.toList()[inboxID]
    val m: NewsletterMessage? = i.messages["$messageID"]
    val rememberMessage by remember {
        mutableStateOf(m)
    }

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
                leftOnClick = { navController.popBackStack() }
            )
        )

        HorizontalDivider()

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            color = LocalPalette.current.surface.onColor,
            shape = RoundedCornerShape(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = AnnotatedString.Companion.fromHtml(
                        rememberMessage?.title ?: ""
                    ),
                    style = LocalTypography.current.quaternary.regular,
                    color = LocalPalette.current.textFamily.emphasis,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = AnnotatedString.Companion.fromHtml(
                        rememberMessage?.getDateFormatted() ?: ""
                    ),
                    style = LocalTypography.current.quaternary.regular,
                    color = LocalPalette.current.textFamily.body,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

            }

        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            text = AnnotatedString.Companion.fromHtml(
                rememberMessage?.description ?: ""
            ),
            style = LocalTypography.current.quaternary.bold,
            color = LocalPalette.current.textFamily.body,
            fontSize = 18.sp
        )

        AdmobBanner()

    }*/
}