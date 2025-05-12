package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.item.GhostListItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.InvestigationViewModel
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

        val arrayList = ArrayList<Int>()
    }
}

@Composable
fun GhostList(
    investigationViewModel: InvestigationViewModel = viewModel(factory = InvestigationViewModel.Factory)
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val listState = rememberLazyListState()

    val ghostsScoreState = investigationViewModel.ghostScores.collectAsStateWithLifecycle()
    val ghostsOrderState = investigationViewModel.ghostOrder.collectAsStateWithLifecycle()

    val rememberOrderState by remember { ghostsOrderState }

    /*LaunchedEffect(rememberOrderState) {

        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                listState.animateScrollToItem(0)
                Toast.makeText(context, "Update Reorder", Toast.LENGTH_SHORT).show()
            }
        }
    }*/


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState
    ) {

        items(
            items = ghostsOrderState.value,
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