package com.tritiumgaming.feature.investigation.ui.journal

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumgaming.feature.investigation.ui.journal.lists.EvidenceList
import com.tritiumgaming.feature.investigation.ui.journal.lists.GhostList
import org.jetbrains.annotations.TestOnly

@Composable
@Preview
@TestOnly
private fun JournalListsPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Journal(investigationViewModel = viewModel(factory = InvestigationScreenViewModel.Factory))
    }
}

@Composable
fun Journal(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {

        if(investigationViewModel.rTLPreference) {
            EvidenceListColumn(investigationScreenViewModel = investigationViewModel)
            GhostListColumn(investigationScreenViewModel = investigationViewModel)
        } else {
            GhostListColumn(investigationScreenViewModel = investigationViewModel)
            EvidenceListColumn(investigationScreenViewModel = investigationViewModel)
        }

    }

}

@Composable
private fun RowScope.GhostListColumn(
    investigationScreenViewModel: InvestigationScreenViewModel
) {
    Column(
        modifier = Modifier
            .weight(1f, false)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = stringResource(R.string.investigation_section_title_ghosts),
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.primary,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )
        }

        GhostList(
            investigationViewModel = investigationScreenViewModel
        ) {
            Log.d("GhostList", "Setting popup to ${it.name}")
            investigationScreenViewModel.setPopup(it)
        }
    }
}

@Composable
private fun RowScope.EvidenceListColumn(
    investigationScreenViewModel: InvestigationScreenViewModel
) {

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth()
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicText(
                    text = stringResource(R.string.investigation_section_title_evidence),
                    style = LocalTypography.current.primary.regular.copy(
                        color = LocalPalette.current.primary,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
                )
            }

            EvidenceList(
                investigationViewModel = investigationScreenViewModel
            ) {
                Log.d("EvidenceList", "Setting popup to ${it.name}")
                investigationScreenViewModel.setPopup(it)
            }

        }
    }
}