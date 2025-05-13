package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common.CollapseButton
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common.InvestigationToolbar
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common.ResetButton
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.common.ToolBarItemPair
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.popups.EvidencePopup
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.popups.GhostPopup
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.sections.Journal
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
fun InvestigationPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Surface(
            color = LocalPalette.current.surface.color,
        ) {
            InvestigationContent()
        }

    }
}

@Composable
fun InvestigationContent() {

    Box {

        if(LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Investigation()
            }
        } else {
            Row {
                Investigation()
            }
        }

        EvidencePopup()
        GhostPopup()

    }

}

@Composable
private fun ColumnScope.Investigation() {
    Journal(
        modifier = Modifier
            .weight(1f, false)
    )
    Toolbar(
        modifier = Modifier
            .height(48.dp)
    )
    Toolbox(
        modifier = Modifier
            .fillMaxHeight(.3f)

    )
}

@Composable
private fun RowScope.Investigation() {
    Toolbox(
        modifier = Modifier
            .fillMaxWidth(.3f)
    )
    Toolbar(
        modifier = Modifier
            .width(48.dp)
    )
    Journal()
}

@Composable
private fun Toolbar(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel = viewModel(factory = InvestigationViewModel.Factory)
) {

    Row(
        modifier = modifier
            .heightIn(min = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        val toolsList: Array<ToolBarItemPair> = arrayOf(
            ToolBarItemPair(@Composable {
                CollapseButton()
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

@Composable
private fun ColumnScope.Toolbox(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {
    val collapsedState =
        investigationViewModel.isInvestigationToolsDrawerCollapsed.collectAsStateWithLifecycle()

    val visible by remember {
        mutableStateOf(collapsedState)
    }

    AnimatedVisibility(!visible.value) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

        }
    }
}

@Composable
private fun RowScope.Toolbox(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel =
        viewModel(factory = InvestigationViewModel.Factory)
) {
    val collapsedState =
        investigationViewModel.isInvestigationToolsDrawerCollapsed.collectAsStateWithLifecycle()

    val visible by remember {
        mutableStateOf(collapsedState)
    }

    AnimatedVisibility(!visible.value) {
        Column(
            modifier = modifier
                .wrapContentWidth(Alignment.Start),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {

        }
    }
}