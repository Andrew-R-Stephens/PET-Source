package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.Holiday22
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.Journal
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.subsection.sanity.tools.operationconfig.DifficultyConfig
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.subsection.sanity.tools.operationconfig.MapConfig
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.CollapseButton
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.InvestigationToolbar
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.ResetButton
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.ToolBarItemPair

@Composable
@Preview
private fun InvestigationSoloScreenPreview() {
    SelectiveTheme(
        palette = Holiday22,
        typography = ClassicTypography
    ) {

        InvestigationSoloScreen(
            investigationViewModel = viewModel(factory = InvestigationViewModel.Factory))

    }

}

@Composable
fun InvestigationSoloScreen(
    navController: NavHostController = rememberNavController(),
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {

    OperationScreen(
        navController = navController
    ) {
        InvestigationSoloContent(
            investigationViewModel = investigationViewModel
        )
    }

}

@Composable
private fun InvestigationSoloContent(
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {

    val investigationToolbarUiState =
        investigationViewModel.investigationToolbarUiState.collectAsStateWithLifecycle()
    //var rememberCollapsed by remember { mutableStateOf(collapsedState) }
    /*LaunchedEffect(collapsedState) {
        rememberCollapsed = collapsedState
    }*/

    if(LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Investigation(
                investigationViewModel = investigationViewModel,
                collapsedState = investigationToolbarUiState.value.isCollapsed
            )
        }
    } else {
        Row {
            Investigation(
                investigationViewModel = investigationViewModel,
                collapsedState = investigationToolbarUiState.value.isCollapsed
            )
        }
    }


}

@Composable
private fun ColumnScope.Investigation(
    investigationViewModel: InvestigationViewModel,
    collapsedState: Boolean,
) {
    Journal(
        modifier = Modifier
            .weight(1f, false),
        investigationViewModel = investigationViewModel
    )
    Toolbar(
        modifier = Modifier
            .height(48.dp),
        investigationViewModel = investigationViewModel
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if(!collapsedState) Modifier.wrapContentHeight()
                else Modifier.height(0.dp).alpha(0f)
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        MapConfig(
            investigationViewModel = investigationViewModel
        )
        DifficultyConfig(
            investigationViewModel = investigationViewModel
        )
    }
}

@Composable
private fun RowScope.Investigation(
    investigationViewModel: InvestigationViewModel,
    collapsedState: Boolean,
) {

    Column(
        modifier = Modifier
            .then(
                if(collapsedState) Modifier.fillMaxWidth(0f).alpha(0f)
                else Modifier.fillMaxWidth(.25f)
            )
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        MapConfig(
            investigationViewModel = investigationViewModel
        )
        DifficultyConfig(
            investigationViewModel = investigationViewModel
        )
    }
    Toolbar(
        modifier = Modifier
            .width(48.dp),
        investigationViewModel = investigationViewModel
    )
    Journal(
        investigationViewModel = investigationViewModel,
    )
}

@Composable
private fun Toolbar(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel,
) {
    val investigationToolbarUiState =
        investigationViewModel.investigationToolbarUiState.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
            .heightIn(min = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        val toolsList: Array<ToolBarItemPair> = arrayOf(
            ToolBarItemPair(@Composable {
                CollapseButton(
                    isCollapsed = investigationToolbarUiState.value.isCollapsed
                ) {
                    investigationViewModel.toggleInvestigationToolsDrawerState()
                }
            }),
            ToolBarItemPair(@Composable {
                ResetButton(
                    onClick = { investigationViewModel.resetInvestigationJournal() }
                )
            }),
            ToolBarItemPair(View(LocalContext.current))
        )

        InvestigationToolbar(
            toolsList
        )
    }
}