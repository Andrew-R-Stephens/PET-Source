package com.tritiumgaming.feature.investigation.ui.journal.ghost.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.FootprintsIcon
import com.tritiumgaming.core.ui.icon.impl.base.GeneticsIcon
import com.tritiumgaming.core.ui.icon.impl.composite.MarkCheckCircleIconComposite
import com.tritiumgaming.core.ui.icon.impl.composite.MarkPriorityCircleIcon
import com.tritiumgaming.core.ui.icon.impl.composite.MarkXCircleIconComposite
import com.tritiumgaming.core.ui.icon.impl.composite.TraitConfirmIcon
import com.tritiumgaming.core.ui.icon.impl.composite.TraitLikelyIcon
import com.tritiumgaming.core.ui.icon.impl.composite.TraitRejectIcon
import com.tritiumgaming.core.ui.icon.impl.composite.TraitUnlikelyIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toDrawableResource
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState.Companion.NORMAL_AFFIRM_MINIMUM_REACHED
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState.Companion.ZERO_EVIDENCE
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.investigation.model.EvidenceState
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType.NEGATIVE
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType.NEUTRAL
import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType.POSITIVE
import kotlin.math.sign

@Composable
fun LazyItemScope.GhostListItem(
    modifier: Modifier = Modifier,
    evidenceState: List<EvidenceState>,
    ghostState: GhostState? = null,
    label: String = ghostState?.let {
        stringResource(ghostState.ghostEvidence.ghost.name.toStringResource()) } ?: "Test",
    ghostListUiItemActions: GhostListUiItemActions
) {
    if (ghostState == null) return

    val scoreState = ghostState.score
    val traitScore = ghostState.traitScore
    val rejectionState = ghostState.manualRejection

    val bpmState = ghostState.bpmIsValid
    val showTraitConfirm = traitScore.confirm > ZERO_EVIDENCE
    val showTraitReject = traitScore.reject > ZERO_EVIDENCE
    val showTraitProbableConfirm = traitScore.probableConfirm > ZERO_EVIDENCE
    val showTraitProbableReject = traitScore.probableReject > ZERO_EVIDENCE

    val emitSpecialBadge = bpmState ||
            showTraitConfirm || showTraitReject ||
            showTraitProbableConfirm || showTraitProbableReject

    val ghostIdStr = ghostState.ghostEvidence.ghost.id.toStringResource().let {
        stringResource(it)
    }
    val strikethroughIcon = when (ghostIdStr.toFloat().rem(3f)) {
        0f -> R.drawable.icon_strikethrough_2
        1f -> R.drawable.icon_strikethrough_3
        else -> R.drawable.icon_strikethrough_1
    }

    @Composable
    fun Strikethrough(
        modifier: Modifier = Modifier,
    ) {
        scoreState.let {
            when {
                (rejectionState) ->
                    Image(
                        modifier = modifier,
                        painter = painterResource(id = R.drawable.ic_ev_omit),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Omit Icon",
                        colorFilter = ColorFilter.tint(LocalPalette.current.primary)
                    )

                it < ZERO_EVIDENCE ->
                    Image(
                        modifier = modifier,
                        painter = painterResource(id = strikethroughIcon),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Strikeout Icon",
                        colorFilter = ColorFilter.tint(LocalPalette.current.primary)
                    )

                it >= NORMAL_AFFIRM_MINIMUM_REACHED ->
                    Image(
                        modifier = modifier,
                        painter = painterResource(id = R.drawable.ic_selector_selected),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Circle Icon",
                        colorFilter = ColorFilter.tint(LocalPalette.current.tertiary)
                    )
            }
        }
    }

    @Composable
    fun Nameplate(
        modifier: Modifier = Modifier,
    ) {
        BasicText(
            modifier = modifier,
            text = label,
            style = LocalTypography.current.primary.regular.copy(
                color = LocalPalette.current.onSurface,
                textAlign = TextAlign.Center
            ),
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(
                minFontSize = 1.sp,
                maxFontSize = 36.sp,
                stepSize = 5.sp
            )
        )
    }

    @Composable
    fun EvidenceIconRow(modifier: Modifier) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
        ) {

            val allEvidence = ghostState.ghostEvidence
            val evidenceList = allEvidence.normalEvidenceList
            val strictEvidenceList = allEvidence.strictEvidenceList

            evidenceList
                .sortedWith(
                    compareBy(
                        { evidence ->
                            ghostListUiItemActions.onGetEvidenceState(evidence)?.state?.ordinal
                        },
                        { evidence ->
                            evidence.id
                        },
                    )
                )
                .forEach { normal ->
                    EvidenceIcon(
                        evidenceState = evidenceState,
                        evidence = normal,
                        isStrict = strictEvidenceList.find { strict ->
                            strict.id == normal.id
                        }?.let { true } ?: false,
                        ghostScore = scoreState
                    )
                }

        }
    }

    Surface(
        modifier = modifier
            .wrapContentWidth(Alignment.CenterHorizontally),
        shape = RoundedCornerShape(8.dp),
        color = LocalPalette.current.surfaceContainerLow,
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(4.dp),
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                color = LocalPalette.current.surfaceContainerLow,
            ) {
                Row(
                    modifier = modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .height(36.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures(
                                    onDragEnd = {
                                        ghostState.let {
                                            ghostListUiItemActions.onToggleNegateGhost(
                                                ghostState.ghostEvidence.ghost
                                            )
                                        }
                                    }
                                ) { change, _ ->
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
                        Nameplate(
                            Modifier.fillMaxWidth()
                                .wrapContentHeight()
                        )

                        Strikethrough(
                            Modifier.fillMaxSize()
                        )
                    }

                    EvidenceIconRow(
                        Modifier
                            .weight(1f, true)
                            .fillMaxHeight()
                    )
                }
            }

            if (emitSpecialBadge) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(onClick = {
                            ghostListUiItemActions.onRequestToolTip()
                        })
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (bpmState) {
                        Surface(
                            modifier = Modifier
                                .wrapContentSize(),
                            shape = RoundedCornerShape(8.dp),
                            color = LocalPalette.current.surfaceContainerHigh,
                        ) {
                            FootprintsIcon(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(16.dp),
                                colors = IconVectorColors.defaults(
                                    fillColor = LocalPalette.current.tertiary,
                                    strokeColor = LocalPalette.current.tertiary
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .wrapContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (showTraitConfirm) {
                            val color = LocalPalette.current.tertiary

                            Surface(
                                modifier = Modifier
                                    .wrapContentSize(),
                                shape = RoundedCornerShape(8.dp),
                                color = LocalPalette.current.surfaceContainerHigh,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        2.dp,
                                        Alignment.Start
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${traitScore.confirm}",
                                        style = LocalTypography.current.quaternary.bold,
                                        color = color,
                                        autoSize = TextAutoSize.StepBased(
                                            1.sp, 18.sp, 1.sp)
                                    )
                                    TraitConfirmIcon(
                                        modifier = Modifier
                                            .size(16.dp),
                                        color = color
                                    )
                                }
                            }
                        }

                        if (showTraitReject) {
                            val color = LocalPalette.current.primary


                            Surface(
                                modifier = Modifier
                                    .wrapContentSize(),
                                shape = RoundedCornerShape(8.dp),
                                color = LocalPalette.current.surfaceContainerHigh,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        2.dp,
                                        Alignment.Start
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${traitScore.reject}",
                                        style = LocalTypography.current.quaternary.bold,
                                        color = color,
                                        autoSize = TextAutoSize.StepBased(
                                            1.sp, 18.sp, 1.sp)
                                    )
                                    TraitRejectIcon(
                                        modifier = Modifier
                                            .size(16.dp),
                                        color = color
                                    )
                                }
                            }
                        }

                        if (showTraitProbableConfirm ||
                            showTraitProbableReject) {

                            Surface(
                                modifier = Modifier
                                    .wrapContentSize(),
                                shape = RoundedCornerShape(8.dp),
                                color = LocalPalette.current.surfaceContainerHigh,
                            ) {
                                val color = LocalPalette.current.onSurface

                                Row(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        2.dp,
                                        Alignment.Start
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if(showTraitProbableConfirm) {
                                        TraitLikelyIcon(
                                            modifier = Modifier
                                                .size(16.dp),
                                            color = color
                                        )
                                        Text(
                                            text = "${traitScore.probableConfirm}",
                                            style = LocalTypography.current.quaternary.bold,
                                            color = color,
                                            autoSize = TextAutoSize.StepBased(
                                                1.sp, 18.sp, 1.sp
                                            )
                                        )
                                    }

                                    if(showTraitProbableReject) {
                                        TraitUnlikelyIcon(
                                            modifier = Modifier
                                                .size(16.dp),
                                            color = color
                                        )
                                        Text(
                                            text = "${traitScore.probableReject}",
                                            style = LocalTypography.current.quaternary.bold,
                                            color = color,
                                            autoSize = TextAutoSize.StepBased(
                                                1.sp, 18.sp, 1.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.EvidenceIcon(
    evidenceState: List<EvidenceState>,
    evidence: EvidenceType,
    ghostScore: Int,
    isStrict: Boolean = false
) {

    val evidenceRuling = evidenceState.find { it.evidence.id == evidence.id }?.state

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

        val strictlyNegative = ghostScore == GhostState.STRICT_EVIDENCE_NOT_FOUND

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
                    MarkXCircleIconComposite(
                        modifier = Modifier
                            .fillMaxSize(.5f)
                            .widthIn(min = 16.dp)
                            .align(Alignment.TopEnd),
                        onColor = LocalPalette.current.surfaceContainer,
                        color = LocalPalette.current.primary
                    )
                }
                else -> {
                    MarkCheckCircleIconComposite(
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
