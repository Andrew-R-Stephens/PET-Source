package com.tritiumgaming.feature.operation.ui.missions.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.ui.investigation.InvestigationScreenViewModel
import com.tritiumgaming.feature.operation.ui.missions.ObjectivesViewModel
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources

@Composable
fun GhostResponseContent(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel,
    investigationViewModel: InvestigationScreenViewModel
) {
    val ghostDetailsUiState = objectivesViewModel.ghostDetailsUiState.collectAsStateWithLifecycle()
    val responseState = ghostDetailsUiState.value.responseState

    val difficultyUiState = investigationViewModel.difficultyUiState.collectAsStateWithLifecycle()
    val isResponseKnown = difficultyUiState.value.responseType ==
            DifficultyResources.DifficultyResponseType.KNOWN

    Column(
        modifier = modifier
            .wrapContentHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            text = stringResource(R.string.objectives_title_response),
            style = LocalTypography.current.quaternary.regular.copy(
                textAlign = TextAlign.Center
            ),
            color = LocalPalette.current.textFamily.body,
            fontSize = 18.sp
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                ResponseItem(
                    modifier = Modifier
                        .weight(1f, false),
                    title = R.string.objectives_title_response_alone,
                    icon = R.drawable.ic_response_alone,
                    state = responseState == ObjectivesViewModel.ALONE,
                    enabled = isResponseKnown
                ) {
                    objectivesViewModel.setGhostResponse(ObjectivesViewModel.ALONE)
                }

                ResponseItem(
                    modifier = Modifier
                        .weight(1f, false),
                    title = R.string.objectives_title_response_everyone,
                    icon = R.drawable.ic_response_group,
                    state = responseState == ObjectivesViewModel.GROUP,
                    enabled = isResponseKnown
                ) {
                    objectivesViewModel.setGhostResponse(ObjectivesViewModel.GROUP)
                }

            }

            if(!isResponseKnown) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.icon_strikethrough_2),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(
                        LocalPalette.current.negativeSelColor
                    ),
                )
            }
        }

        if (!isResponseKnown) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp),
                text = "Note: Difficulties higher than intermediate do not disclose response type",
                style = LocalTypography.current.quaternary.regular.copy(
                    textAlign = TextAlign.Center
                ),
                maxLines = 2,
                color = LocalPalette.current.textFamily.emphasis,
                fontSize = 14.sp
            )
        }

    }


}

@Composable
fun ResponseItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    state: Boolean,
    enabled: Boolean,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .wrapContentHeight()
            .clickable(
                enabled = enabled,
                onClick = { onClick() }
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .sizeIn(
                    minWidth = 48.dp, minHeight = 48.dp,
                    maxHeight = 72.dp, maxWidth = 72.dp
                )
                .padding(4.dp),
            painter = painterResource(icon),
            contentDescription = "",
            colorFilter = ColorFilter.tint(
                if (state && enabled)
                    LocalPalette.current.selectedColor
                else
                    LocalPalette.current.unselectedColor.copy(alpha = 0.75f)
            )
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(title),
            style = LocalTypography.current.quaternary.regular.copy(
                textAlign = TextAlign.Center
            ),
            color = if (state && enabled)
                    LocalPalette.current.textFamily.body
                else
                    LocalPalette.current.unselectedColor.copy(alpha = .75f),
            fontSize = 18.sp
        )

    }

}