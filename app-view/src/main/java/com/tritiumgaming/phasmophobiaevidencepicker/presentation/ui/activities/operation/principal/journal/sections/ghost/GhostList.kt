package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.sections.ghost

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import org.jetbrains.annotations.TestOnly


@Composable
@Preview
@TestOnly
private fun GhostListPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        GhostList()
    }
}

@Composable
fun GhostList(
    investigationViewModel: InvestigationViewModel = viewModel(factory = InvestigationViewModel.Factory)
) {

    val listState = rememberLazyListState()

    val ghostsScoreState = investigationViewModel.ghostScores.collectAsStateWithLifecycle()

    val ghostsOrderState = investigationViewModel.ghostOrder.collectAsStateWithLifecycle()
    val rememberOrderState by remember { ghostsOrderState }
    LaunchedEffect(rememberOrderState) { listState.animateScrollToItem(0) }

    Toast.makeText(LocalContext.current, "Update Reorder", Toast.LENGTH_SHORT).show()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState
    ) {

        items(
            items = rememberOrderState,
            key = { it }
        ) {

            investigationViewModel.ghostRepository.getById(it)?.let { ghostModel ->

                GhostListItem(
                    modifier = Modifier
                        .animateItem(),
                    ghostScore = ghostsScoreState.value.find { it.ghostModel.id == ghostModel.id }
                )

            }

        }

    }

}