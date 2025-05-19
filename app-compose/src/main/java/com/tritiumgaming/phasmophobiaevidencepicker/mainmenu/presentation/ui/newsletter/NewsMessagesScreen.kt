package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.newsletter

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.admob.AdmobBanner
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType.NewsletterInboxTypeDTO
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.common.NotificationIndicator
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.NewsletterViewModel


@Composable
@Preview
private fun NewsMessagesScreenPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface.color
        ) {
            NewsMessagesScreen()
        }
    }
}

@Composable
fun NewsMessagesScreen(
    navController: NavController = rememberNavController(),
    inboxID: Int = 0
) {

    MainMenuScreen {

        NewsMessagesContent(
            navController = navController,
            inboxID = inboxID
        )

    }

}


@Composable
private fun NewsMessagesContent(
    navController: NavController = rememberNavController(),
    newsletterViewModel: NewsletterViewModel = viewModel(factory = NewsletterViewModel.Factory),
    inboxID: Int
) {

    val inboxType: NewsletterInboxTypeDTO? = NewsletterInboxType.getInbox(inboxID)
    val rememberInbox by remember{
        mutableStateOf(inboxType?.let { newsletterViewModel.getInbox(it) })
    }
    val rememberMessages = remember { mutableStateOf(
        rememberInbox?.messages?.toList() ?: listOf() ) }

    var lastReadDateState = rememberInbox?.lastReadDate?.collectAsStateWithLifecycle()
    var rememberLastReadDate by remember {
        mutableLongStateOf(lastReadDateState?.value ?: 0L)
    }

    val requiresNotifyState = newsletterViewModel.requiresNotify(inboxType)?.collectAsStateWithLifecycle()
    val rememberRequiresNotify by remember {
        mutableStateOf(requiresNotifyState)
    }

    LaunchedEffect(rememberRequiresNotify) {
        rememberLastReadDate = rememberInbox?.lastReadDate?.value ?: 0L
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
                centerTitleRes = rememberInbox?.inboxType?.title
                    ?: R.string.messagecenter_inboxestitle_label,
                leftOnClick = { navController.popBackStack() }
            )
        )

        HorizontalDivider()

        Box(
            modifier = Modifier
                .padding(PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
                .align(Alignment.End)
        ) {

            Button(
                modifier = Modifier
                    .height(48.dp)
                    .wrapContentWidth()
                    .alpha(if (rememberRequiresNotify?.value == true) 1f else .4f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalPalette.current.buttonColor,
                ),
                contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(size = 8.dp),
                enabled = rememberRequiresNotify?.value == true,
                onClick = {
                    inboxType?.let { inboxType ->
                        rememberMessages.value.firstOrNull()?.second?.date?.let {
                            newsletterViewModel.setLastReadDate(inboxType, it)

                            rememberLastReadDate = it
                        }
                    }
                }
            ) {

                Text(
                    modifier = Modifier.padding(0.dp),
                    text = stringResource(R.string.newsletter_markallread),
                    color = LocalPalette.current.textFamily.body,
                    style = LocalTypography.current.quaternary.bold,
                    maxLines = 1
                )

            }

        }

        val rememberListState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            state = rememberListState
        ) {

            items(items = rememberMessages.value) { message ->

                val rememberMessage by remember {
                    mutableStateOf(message.second)
                }

                MessageCard(
                    message = rememberMessage,
                    isActive = rememberMessage.date - rememberLastReadDate > 0,
                    onClick = {
                        inboxType?.let { inboxType ->
                            newsletterViewModel.setLastReadDate(
                                inboxType, rememberMessage.date)
                        }
                        navController.navigate(
                            route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGE.route}/" +
                                    "${inboxID}/${rememberMessage.id}"
                        )
                    }
                )

            }

        }

        AdmobBanner()

    }
}

@Composable
private fun MessageCard(
    message: NewsletterInbox.NewsletterMessage,
    isActive: Boolean = false,
    onClick: () -> Unit = {}
) {

    val rememberMessage by remember {
        mutableStateOf(message)
    }

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .height(48.dp),
        color = LocalPalette.current.surface.onColor,
        shape = RoundedCornerShape(CornerSize(16.dp)),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if(isActive) {
                NotificationIndicator(
                    modifier = Modifier
                        .fillMaxHeight(),
                    isActive = true,
                    baseDrawableId = null
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                    ),
                text = rememberMessage.title,
                style = LocalTypography.current.quaternary.bold,
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )

        }

    }
}
