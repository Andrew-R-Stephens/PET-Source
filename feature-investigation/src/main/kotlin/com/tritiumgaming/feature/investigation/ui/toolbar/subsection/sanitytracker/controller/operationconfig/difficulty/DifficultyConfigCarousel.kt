package com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.difficulty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.OperationConfigCarousel

@Composable
fun DifficultyConfigCarousel(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel,
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    iconColorFilter: ColorFilter = ColorFilter.tint(Color.Unspecified),
) {

    val difficultyUiState = investigationViewModel.difficultyUiState.collectAsStateWithLifecycle()
    val difficultyName = difficultyUiState.value.name.toStringResource()

    OperationConfigCarousel(
        modifier = modifier,
        primaryIcon = R.drawable.ic_puzzle,
        label = stringResource(difficultyName),
        textStyle = textStyle,
        color = color,
        containerColor = containerColor,
        iconColorFilter = iconColorFilter,
        onClickLeft = {
            investigationViewModel.decrementDifficultyIndex()
        },
        onClickRight = {
            investigationViewModel.incrementDifficultyIndex()
        }
    )
}
