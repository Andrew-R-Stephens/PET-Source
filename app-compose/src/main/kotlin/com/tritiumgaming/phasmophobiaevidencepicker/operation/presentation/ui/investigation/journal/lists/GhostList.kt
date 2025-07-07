package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists

import android.util.Log
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.item.GhostListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly


@Composable
@Preview
@TestOnly
private fun GhostListPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        GhostList(investigationViewModel = viewModel(factory = InvestigationViewModel.Factory))

        val arrayList = ArrayList<Int>()
    }
}

@Composable
fun GhostList(
    investigationViewModel: InvestigationViewModel
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val listState = rememberLazyListState()

    val ghostsScoreState = investigationViewModel.ghostScores.collectAsStateWithLifecycle()
    val ghostsOrderState = investigationViewModel.ghostOrder.collectAsStateWithLifecycle()

    Log.d("GhostList", "Ghost Scores: ${ghostsScoreState.value.size}")

    val rememberOrderState by remember { ghostsOrderState }

    LaunchedEffect(rememberOrderState) {

        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                listState.animateScrollToItem(0)
            }
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState
    ) {

        items(
            items = ghostsOrderState.value,
            key = { it }
        ) {

            investigationViewModel.getGhostById(it)?.let { ghostModel ->

                Log.d("GhostList", "Ghost Found: ${ghostModel.id}")

                GhostListItem(
                    modifier = Modifier
                        .animateItem(),
                    investigationViewModel = investigationViewModel,
                    ghostScore = ghostsScoreState.value.find {
                        it.ghostEvidence.ghost.id == ghostModel.id }
                )

            } ?: Log.d("GhostList", "Ghost Found for id: $it")


        }

    }

}