package com.tritiumgaming.feature.investigation.ui.journal.lists.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyItemScope
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
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.prefabicon.MarkCheckCircleIcon
import com.tritiumgaming.core.ui.common.prefabicon.MarkPriorityCircleIcon
import com.tritiumgaming.core.ui.common.prefabicon.MarkXCircleIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling.NEGATIVE
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling.NEUTRAL
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling.POSITIVE
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toDrawableResource
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.ghost.model.GhostType

@Composable
fun LazyItemScope.GhostListItem(
    modifier: Modifier = Modifier,
    ruledEvidence: List<RuledEvidence>,
    ghostScore: GhostScore? = null,
    label: String = ghostScore?.let {
        stringResource(ghostScore.ghostEvidence.ghost.name.toStringResource()) } ?: "Test",
    onToggleNegateGhost: (GhostType) -> Unit = {},
    onGetRuledEvidence: (EvidenceType) -> RuledEvidence?,
    onNameClick: () -> Unit = {},
) {

    val scoreState = ghostScore?.score?.collectAsStateWithLifecycle()

    val rejectionState = ghostScore?.forcefullyRejected?.collectAsStateWithLifecycle()

    val ghostIdStr = ghostScore?.ghostEvidence?.ghost?.id?.toStringResource()?.let {
        stringResource(it)
    }
    val strikethroughIcon = when(ghostIdStr?.toFloat()?.rem(3f)) {
        0f -> R.drawable.icon_strikethrough_2
        1f -> R.drawable.icon_strikethrough_3
        else ->  R.drawable.icon_strikethrough_1
    }

    Row(
        modifier = modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .height(36.dp)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
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
                                onToggleNegateGhost(ghostScore.ghostEvidence.ghost)
                            }
                        }
                    ) { change, dragAmount ->
                        change.consume()
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        onNameClick()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = label,
                style = LocalTypography.current.primary.regular.copy(
                    color = LocalPalette.current.onSurface,
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
                            colorFilter = ColorFilter.tint(LocalPalette.current.primary)
                        )
                    it < 0 ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = strikethroughIcon),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Strikeout Icon",
                            colorFilter = ColorFilter.tint(LocalPalette.current.primary)
                        )
                    it >= 3 ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.ic_selector_selected),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = "Circle Icon",
                            colorFilter = ColorFilter.tint(LocalPalette.current.tertiary)
                        )
                }
            }

        }

        Row(
            modifier = Modifier
                .weight(1f, true)
                .fillMaxHeight()
                /*.wrapContentWidth()*/
                /*.width(IntrinsicSize.Max)*/,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
        ) {

            val allEvidence = ghostScore?.ghostEvidence
            val evidenceList = allEvidence?.normalEvidenceList
            val strictEvidenceList = allEvidence?.strictEvidenceList

            evidenceList
                ?.sortedWith (
                    compareBy(
                        { evidence ->
                            onGetRuledEvidence(evidence)?.ruling?.ordinal
                        },
                        { evidence ->
                            evidence.id
                        },
                    )
                )
                ?.forEach { normal ->
                    EvidenceIcon(
                        ruledEvidence = ruledEvidence,
                        evidence = normal,
                        isStrict = strictEvidenceList?.find { strict ->
                            strict.id == normal.id
                        }?.let { true } ?: false
                    )
            }

        }
    }
}

@Composable
private fun RowScope.EvidenceIcon(
    ruledEvidence: List<RuledEvidence>,
    evidence: EvidenceType,
    isStrict: Boolean = false
) {

    val evidenceRuling = ruledEvidence.find { it.evidence.id == evidence.id }?.ruling

    //Log.d("EvidenceIcon", "Evidence: ${evidence.name}, Ruling: ${evidenceRuling?.name}")

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .weight(.25f, false)
    ) {
        Image(
            contentScale = ContentScale.Fit,
            painter = painterResource(
                id = evidence.icon.toDrawableResource()
            ),
            contentDescription = "Evidence Icon",
            colorFilter = ColorFilter.tint(
                when (evidenceRuling) {
                    NEGATIVE -> LocalPalette.current.primary
                    POSITIVE -> LocalPalette.current.tertiary
                    else -> LocalPalette.current.secondary
                }
                /*when (evidenceRuling) {
                    NEGATIVE -> LocalPalette.current.negativeSelColor
                    POSITIVE -> LocalPalette.current.positiveSelColor
                    else -> LocalPalette.current.neutralSelColor
                }*/
            )
        )

        if(isStrict) {
            when (evidenceRuling) {
                NEUTRAL -> {
                    MarkPriorityCircleIcon(
                        modifier = Modifier
                            .fillMaxSize(.5f)
                            .widthIn(min = 16.dp)
                            .align(Alignment.TopEnd),
                        foregroundColor = LocalPalette.current.tertiary,
                        backgroundColor = LocalPalette.current.surfaceContainer,
                        //foregroundColor = LocalPalette.current.negativeSelColor
                    )
                }
                NEGATIVE -> {
                    MarkXCircleIcon(
                        modifier = Modifier
                            .fillMaxSize(.5f)
                            .widthIn(min = 16.dp)
                            .align(Alignment.TopEnd),
                        foregroundColor = LocalPalette.current.surfaceContainer,
                        backgroundColor = LocalPalette.current.secondary
                        //backgroundColor = LocalPalette.current.negativeSelColor,
                    )
                }
                else -> {
                    MarkCheckCircleIcon(
                        modifier = Modifier
                            .fillMaxSize(.5f)
                            .widthIn(min = 16.dp)
                            .align(Alignment.TopEnd),
                        foregroundColor = LocalPalette.current.surfaceContainer,
                        backgroundColor = LocalPalette.current.primary
                        //backgroundColor = LocalPalette.current.positiveSelColor
                    )
                }
            }

        }
    }
}
