package com.tritiumgaming.feature.newsletter.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.admob.BannerAd
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderSideButton
import com.tritiumgaming.feature.newsletter.ui.NewsletterViewModel
import com.tritiumgaming.shared.data.newsletter.model.NewsletterChannel
import com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox
import com.tritiumgaming.shared.data.newsletter.model.NewsletterMessage

@Composable
@Preview(name = "Small Phone", device = "id:small_phone")
private fun NewsMessageScreenPreview_SmallPhone_Portrait() {
    NewsMessagePreview()
}

@Composable
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
private fun NewsMessageScreenPreview_SmallPhone_Landscape() {
    NewsMessagePreview()
}

@Composable
@Preview(name = "Medium Phone Portrait",
    device = "spec:width=411dp,height=891dp"
)
private fun NewsMessageScreenPreview_MediumPhone_Portrait() {
    NewsMessagePreview()
}

@Composable
@Preview(name = "Medium Phone Landscape",
    device = "spec:width=411dp,height=891dp,orientation=landscape"
)
private fun NewsMessageScreenPreview_MediumPhone_Landscape() {
    NewsMessagePreview()
}

@Composable
@Preview(name = "Medium Tablet Portrait",
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait"
)
private fun NewsMessageScreenPreview_MediumTablet_Portrait() {
    NewsMessagePreview()
}

@Composable
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
private fun NewsMessageScreenPreview_MediumTablet_Landscape() {
    NewsMessagePreview()
}

@Composable
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private fun NewsMessageScreenPreview_Foldable() {
    NewsMessagePreview()
}

@Composable
private fun NewsMessagePreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface
        ) {
            val inboxID = "inbox1"
            val messageID = "msg1"
            val message = NewsletterMessage(
                id = messageID,
                title = "Phasmophobia Update",
                description = "This is a <b>test</b> description for the newsletter update. Check out <a href='https://google.com'>this link</a>.",
                dateFormatted = "October 31, 2023"
            )
            val inbox = NewsletterInbox(
                id = inboxID,
                channel = NewsletterChannel(
                    language = "en",
                    messages = listOf(message)
                )
            )

            NewsMessageContent(
                newsletterInboxesUiState = NewsletterInboxesUiState(
                    inboxes = listOf(NewsletterInboxUiState(inbox = inbox))
                ),
                inboxID = inboxID,
                messageID = messageID,
                onNavigateBack = {}
            )
        }
    }
}

@Composable
fun NewsMessageScreen(
    navController: NavHostController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel,
    inboxID: String,
    messageID: String
) {

    val newsletterInboxesUiState by newsletterViewModel.inboxesUiState.collectAsStateWithLifecycle()

    NewsMessageContent(
        newsletterInboxesUiState = newsletterInboxesUiState,
        inboxID = inboxID,
        messageID = messageID,
        onNavigateBack = navController::popBackStack
    )
}

@Composable
private fun NewsMessageContent(
    newsletterInboxesUiState: NewsletterInboxesUiState,
    inboxID: String,
    messageID: String,
    onNavigateBack: () -> Unit
) {

    val rememberInboxID by remember { mutableStateOf(inboxID) }
    val rememberMessageID by remember { mutableStateOf(messageID) }

    val inboxes = newsletterInboxesUiState.inboxes

    val channel = inboxes.find { inboxUiState ->
        inboxUiState.inbox.id == rememberInboxID }?.inbox?.channel
    val messages = channel?.messages
    val message = messages?.find { message -> message.id == rememberMessageID }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        NavigationHeader(onLeftClick = onNavigateBack)

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            color = LocalPalette.current.surfaceContainer,
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
                    text = AnnotatedString.fromHtml(
                        message?.title ?: ""
                    ),
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = LocalPalette.current.primary,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                )

                BasicText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = AnnotatedString.fromHtml(message?.dateFormatted ?: ""),
                    style = LocalTypography.current.quaternary.regular.copy(
                        color = LocalPalette.current.onSurface,
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
            text = AnnotatedString.fromHtml(
                message?.description ?: "",
                linkStyles = TextLinkStyles(
                    style = SpanStyle(
                        color = LocalPalette.current.secondary,
                        fontSize = 18.sp
                    ),
                )
            ),
            style = LocalTypography.current.quaternary.regular.copy(
                color = LocalPalette.current.onSurface,
                fontSize = 18.sp
            )
        )


        BannerAd(
            modifier = Modifier
                .fillMaxWidth(),
            adId = stringResource(R.string.ad_banner_1)
        )

        //AdmobBanner()

    }
}

@Composable
private fun NavigationHeader(
    onLeftClick: () -> Unit = {}
) {
    NavigationHeaderComposable(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 64.dp),
        leftContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
                iconContent = { iconModifier ->
                    Image(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.ic_arrow_60_left),
                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                        contentDescription = ""
                    )
                }
            ) { onLeftClick() }
        },
        rightContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
            )
        },
    )
}

