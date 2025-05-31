package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.formatFromEpoch
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
                inboxID = "0",
                messageID = "0"
            )
        }
    }
}

@Composable
fun NewsMessageScreen(
    navController: NavHostController = rememberNavController(),
    inboxID: String,
    messageID: String
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
    inboxID: String,
    messageID: String
) {

    val rememberInboxID by remember { mutableStateOf(inboxID) }
    val rememberMessageID by remember { mutableStateOf(messageID) }

    val inbox = newsletterViewModel.inboxes.collectAsStateWithLifecycle()
    val channel = inbox.value.find { inbox -> inbox.id == rememberInboxID }?.channel
    val messages = channel?.messages?.collectAsStateWithLifecycle()
    val message = messages?.value?.find { message -> message.id == rememberMessageID }

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

                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = AnnotatedString.Companion.fromHtml(
                        message?.title ?: ""
                    ),
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = LocalPalette.current.textFamily.emphasis,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )

                )

                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = AnnotatedString.Companion.fromHtml(
                        formatFromEpoch(message?.date) ?: ""
                    ),
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = LocalPalette.current.textFamily.body,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                )

            }

        }

        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            text = AnnotatedString.Companion.fromHtml(
                message?.description ?: ""
            ),
            style = LocalTypography.current.quaternary.bold.copy(
                color = LocalPalette.current.textFamily.body,
                fontSize = 18.sp
            )
        )

        AdmobBanner()

    }
}

/*
@Composable
private fun NewsMessageContent(
    navController: NavHostController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    inboxID: Int,
    messageID: Int
) {

    val rememberInboxID by remember {
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

    }
}*/
