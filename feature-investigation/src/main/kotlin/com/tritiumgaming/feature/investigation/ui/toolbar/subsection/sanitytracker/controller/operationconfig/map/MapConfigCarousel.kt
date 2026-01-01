package com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.OperationConfigCarousel
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

@Composable
fun MapConfigCarousel(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel,
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    iconColorFilter: ColorFilter = ColorFilter.tint(Color.Unspecified),
) {

    val mapUiState = investigationViewModel.mapUiState.collectAsStateWithLifecycle()
    val mapName = mapUiState.value.name.toStringResource(SimpleMapResources.MapTitleLength.ABBREVIATED)

    OperationConfigCarousel(
        modifier = modifier,
        primaryIcon = R.drawable.icon_nav_mapmenu2,
        label = stringResource(mapName),
        textStyle = textStyle,
        color = color,
        containerColor = containerColor,
        iconColorFilter = iconColorFilter,
        onClickLeft = {
            investigationViewModel.decrementMapIndex()
        },
        onClickRight = {
            investigationViewModel.incrementMapIndex()
        }
    )
}