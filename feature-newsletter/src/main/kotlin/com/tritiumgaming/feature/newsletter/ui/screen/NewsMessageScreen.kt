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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.admob.BannerAd
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderSideButton
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.newsletter.ui.NewsletterViewModel

@Composable
fun NewsMessageScreen(
    navController: NavHostController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel,
    inboxID: String,
    messageID: String
) {

    NewsMessageContent(
        navController = navController,
        newsletterViewModel = newsletterViewModel,
        inboxID = inboxID,
        messageID = messageID
    )
}

@Composable
private fun NewsMessageContent(
    navController: NavHostController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel,
    inboxID: String,
    messageID: String
) {

    val rememberInboxID by remember { mutableStateOf(inboxID) }
    val rememberMessageID by remember { mutableStateOf(messageID) }

    val inboxesUiState = newsletterViewModel.inboxesUiState.collectAsStateWithLifecycle()
    val inboxes = inboxesUiState.value.inboxes

    val channel = inboxes.find { inboxUiState ->
        inboxUiState.inbox.id == rememberInboxID }?.inbox?.channel
    val messages = channel?.messages
    val message = messages?.find { message -> message.id == rememberMessageID }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        NavigationHeader(
            onLeftClick = { navController.popBackStack() }
        )

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
                    text = AnnotatedString.Companion.fromHtml(
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
                    text = AnnotatedString.Companion.fromHtml(message?.dateFormatted ?: ""),
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
            text = AnnotatedString.Companion.fromHtml(
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
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
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

