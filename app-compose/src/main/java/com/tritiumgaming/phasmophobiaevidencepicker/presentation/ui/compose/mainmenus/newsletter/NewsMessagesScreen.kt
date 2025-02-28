package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.newsletter

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterMessage
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoute
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

    MainMenuScreen{

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

    val rememberInboxID by remember {
        mutableIntStateOf(inboxID)
    }

    val rememberInbox by remember {
        mutableStateOf(
            newsletterViewModel.getInbox(
                NewsletterRepository.NewsletterInboxType.InboxType.entries[rememberInboxID])
        )
    }

    val rememberMessages by remember {
        mutableStateOf(
            rememberInbox?.messages ?: listOf()
        )
    }

    var rememberLastReadDate by remember {
        mutableLongStateOf(rememberInbox?.lastReadDate ?: 0L)
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
                centerTitleRes = integerArrayResource(R.array.messagecenter_inboxtitles)[inboxID],
                leftOnClick = { navController.popBackStack() }
            )
        )

        HorizontalDivider()

        var rememberUpToDate by remember {
            mutableStateOf(rememberInbox?.compareDates() == true)
        }

        Box(
            modifier = Modifier
                .padding(PaddingValues(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
                .align(Alignment.End)
        ) {

            Button(
                modifier = Modifier
                    .height(48.dp)
                    .wrapContentWidth()
                    .alpha(if (rememberUpToDate) 1f else .4f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalPalette.current.buttonColor,
                ),
                contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(size = 8.dp),
                enabled = rememberUpToDate,
                onClick = {
                    rememberInbox?.let{
                        it.updateLastReadDate()
                        rememberLastReadDate = it.lastReadDate
                        rememberUpToDate = it.compareDates() == true
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

        LazyColumn(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {

            itemsIndexed(rememberMessages) {
                messageIndex: Int, message: NewsletterMessage ->

                val rememberMessage by remember {
                    mutableStateOf(message)
                }

                val rememberMessageID by remember {
                    mutableIntStateOf(messageIndex)
                }

                val rememberDateDifference by remember {
                    mutableStateOf(
                        (rememberInbox?.compareDate(rememberMessages[rememberMessageID].date) ?: 0) > 0
                    )
                }

                Toast.makeText(LocalContext.current, "$rememberDateDifference", Toast.LENGTH_SHORT).show()

                MessageCard(
                    title = rememberMessage.title,
                    isActive = rememberDateDifference,
                    onClick = {
                        navController.navigate(
                            route = "${NavRoute.SCREEN_NEWSLETTER_MESSAGE.route}/${rememberInboxID}/${rememberMessageID}")
                        rememberInbox?.updateLastReadDate(rememberMessage)
                    }
                )

            }

        }

        AdmobBanner()

    }
}

@Composable
private fun MessageCard(
    title: String = "temp",
    isActive: Boolean = false,
    onClick: () -> Unit = {}
) {

    val rememberIsActive by remember {
        mutableStateOf(isActive)
    }

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
                    .size(48.dp),
                isActive = rememberIsActive,
                baseDrawableId = null
            )

            AutoResizedText(
                containerModifier = Modifier
                    .weight(1f),
                text = title,
                style = LocalTypography.current.primary.regular,
                color = LocalPalette.current.textFamily.primary,
                textAlign = TextAlign.Center
            )

        }

    }
}
