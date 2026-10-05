package com.tritiumgaming.feature.investigation.ui.components.tool.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.feature.investigation.ui.components.tool.analysis.sections.DifficultyModifierDetails
import com.tritiumgaming.feature.investigation.ui.components.tool.analysis.sections.MapModifierDetails
import com.tritiumgaming.feature.investigation.ui.components.tool.analysis.sections.PhaseModifierDetails

@Composable
internal fun OperationDetails(
    modifier: Modifier = Modifier,
    operationDetailsUiState: OperationDetailsUiState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PhaseModifierDetails(
            modifier = Modifier,
            state = operationDetailsUiState.phaseDetails
        )
        MapModifierDetails(
            state = operationDetailsUiState.mapDetails
        )
        DifficultyModifierDetails(
            difficultyState = operationDetailsUiState.difficultyDetails,
            mapState = operationDetailsUiState.mapDetails,
            weatherDetails = operationDetailsUiState.weatherDetails
        )
        /*ActiveGhostModifierDetails(
            state = operationDetailsUiState.ghostDetails,
            difficultySettings = operationDetailsUiState.difficultyDetails.settings,
            overrides = operationDetailsUiState.overrides
        )*/
    }
}

@Composable
internal fun CategoryColumn(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    content: @Composable () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        content()
    }
}

@Composable
internal fun TextDataRow(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = LocalPalette.current.onSurface,
    data: String,
    dataColor: Color = LocalPalette.current.onSurfaceVariant
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = titleColor)) {
                append(title)
            }
            append(" ")
            withStyle(style = SpanStyle(color = dataColor)) {
                append(data)
            }
        }
    )
}

@Composable
internal fun TextCategoryTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        color = color
    )
}

@Composable
internal fun TextSubTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        color = color
    )
}
