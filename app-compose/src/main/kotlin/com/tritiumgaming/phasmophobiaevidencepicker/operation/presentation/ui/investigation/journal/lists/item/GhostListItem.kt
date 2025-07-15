package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.journal.lists.item

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.InvestigationViewModel

@Composable
fun GhostListItem(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel,
    ghostScore: GhostScore? = null,
    label: String = ghostScore?.let {
        stringResource(ghostScore.ghostEvidence.ghost.name.toStringResource()) } ?: "Test"
) {

    val scoreState = ghostScore?.score?.collectAsStateWithLifecycle()

    val rejectionState = ghostScore?.forcefullyRejected?.collectAsStateWithLifecycle()

    val strikethroughIcon = when(ghostScore?.ghostEvidence?.ghost?.id?.toFloat()?.rem(3f)) {
        0f -> R.drawable.icon_strikethrough_2
        1f -> R.drawable.icon_strikethrough_3
        else ->  R.drawable.icon_strikethrough_1
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            ghostScore?.let {
                                investigationViewModel.toggleGhostNegation(
                                    ghostScore.ghostEvidence.ghost)
                            }
                        }
                    ) { change, dragAmount ->
                        change.consume()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, maxFontSize = 36.sp, stepSize = 5.sp)
            )

            scoreState?.value?.let {
                when {
                    (rejectionState?.value == true) ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.ic_ev_omit),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Omit Icon",
                            colorFilter = ColorFilter.tint(LocalPalette.current.strikethroughColor)
                        )
                    it < 0 ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = strikethroughIcon),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Strikeout Icon",
                            colorFilter = ColorFilter.tint(LocalPalette.current.negativeSelColor)
                        )
                    it >= 3 ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.ic_selector_selected),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Circle Icon",
                            colorFilter = ColorFilter.tint(LocalPalette.current.positiveSelColor)
                        )
                }
            }

        }

        Row(
            modifier = Modifier
                .weight(1f, false)
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {

            val evidenceList = ghostScore?.ghostEvidence?.normalEvidenceList

            evidenceList?.forEach {
                EvidenceIcon(
                    investigationViewModel = investigationViewModel,
                    evidence = it
                )
            }

        }
    }
}

@Composable
private fun RowScope.EvidenceIcon(
    investigationViewModel: InvestigationViewModel,
    evidence: EvidenceType
) {

    val rulingState = investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()
    val evidenceRuling = rulingState.value.find { it.evidence.id == evidence.id }?.ruling

    Log.d("EvidenceIcon", "Evidence: ${evidence.name}, Ruling: ${evidenceRuling?.name}")

    Image(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .weight(.25f, false),
        contentScale = ContentScale.Fit,
        painter = painterResource(
            id = evidence.icon.toDrawableResource()
        ),
        contentDescription = "Evidence Icon",
        colorFilter = ColorFilter.tint(
            when (evidenceRuling) {
                Ruling.NEGATIVE -> LocalPalette.current.negativeSelColor
                Ruling.POSITIVE -> LocalPalette.current.positiveSelColor
                else -> LocalPalette.current.neutralSelColor
            }
        )
    )
}
