package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.tritiumgaming.core.ui.icon.impl.composite.MarkCheckCircleIcon
import com.tritiumgaming.core.ui.icon.impl.composite.MarkPriorityCircleIcon
import com.tritiumgaming.core.ui.icon.impl.composite.MarkXCircleIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toDrawableResource
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling.NEGATIVE
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling.NEUTRAL
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling.POSITIVE

@Composable
fun LazyItemScope.GhostListItem(
    modifier: Modifier = Modifier,
    ruledEvidence: List<RuledEvidence>,
    ghostScore: GhostScore? = null,
    label: String = ghostScore?.let {
        stringResource(ghostScore.ghostEvidence.ghost.name.toStringResource()) } ?: "Test",
    ghostListUiItemActions: GhostListUiItemActions
) {
    if(ghostScore == null) return
    val scoreState = ghostScore.score.collectAsStateWithLifecycle()

    val rejectionState = ghostScore.forcefullyRejected.collectAsStateWithLifecycle()
    val bpmState = ghostScore.bpmState.collectAsStateWithLifecycle()

    val ghostIdStr = ghostScore.ghostEvidence.ghost.id.toStringResource().let {
        stringResource(it)
    }
    val strikethroughIcon = when(ghostIdStr.toFloat().rem(3f)) {
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
                .width(2.dp)
                .fillMaxHeight()
                .background(
                    if (bpmState.value) {
                        LocalPalette.current.errorContainer
                    } else { Color.Transparent }
                )
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            ghostScore.let {
                                ghostListUiItemActions.onToggleNegateGhost(
                                    ghostScore.ghostEvidence.ghost
                                )
                            }
                        }
                    ) { change, dragAmount ->
                        change.consume()
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures {
                        ghostListUiItemActions.onNameClick()
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

            scoreState.value.let {
                when {
                    (rejectionState.value) ->
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
                            ghostListUiItemActions.onGetRuledEvidence(evidence)?.ruling?.ordinal
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
                        }?.let { true } ?: false,
                        ghostScore = scoreState?.value ?: 0
                    )
            }

        }
    }
}

@Composable
private fun RowScope.EvidenceIcon(
    ruledEvidence: List<RuledEvidence>,
    evidence: EvidenceType,
    ghostScore: Int,
    isStrict: Boolean = false
) {

    val evidenceRuling = ruledEvidence.find { it.evidence.id == evidence.id }?.ruling

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
            )
        )

        val strictlyNegative = ghostScore == GhostScore.STRICT_EVIDENCE_NOT_FOUND

        if(isStrict) {
            when (evidenceRuling) {
                NEUTRAL -> {
                    if(strictlyNegative) {
                        MarkPriorityCircleIcon(
                            modifier = Modifier
                                .fillMaxSize(.5f)
                                .widthIn(min = 16.dp)
                                .align(Alignment.TopEnd),
                            onColor = LocalPalette.current.surfaceContainer,
                            color = LocalPalette.current.primary
                        )
                    } else {
                        MarkPriorityCircleIcon(
                            modifier = Modifier
                                .fillMaxSize(.5f)
                                .widthIn(min = 16.dp)
                                .align(Alignment.TopEnd),
                            onColor = LocalPalette.current.surfaceContainer,
                            color = LocalPalette.current.onSurface
                        )
                    }
                }
                NEGATIVE -> {
                    MarkXCircleIcon(
                        modifier = Modifier
                            .fillMaxSize(.5f)
                            .widthIn(min = 16.dp)
                            .align(Alignment.TopEnd),
                        onColor = LocalPalette.current.surfaceContainer,
                        color = LocalPalette.current.primary
                    )
                }
                else -> {
                    MarkCheckCircleIcon(
                        modifier = Modifier
                            .fillMaxSize(.5f)
                            .widthIn(min = 16.dp)
                            .align(Alignment.TopEnd),
                        onColor = LocalPalette.current.surfaceContainer,
                        color = LocalPalette.current.tertiary
                    )
                }
            }

        }
    }
}
