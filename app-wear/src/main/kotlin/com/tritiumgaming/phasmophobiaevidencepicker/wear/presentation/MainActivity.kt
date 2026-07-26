package com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import androidx.wear.tooling.preview.devices.WearDevices
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.wear.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation.viewmodel.WearableViewModel
import com.tritiumgaming.shared.data.codex.mappers.toEquipmentIcon
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.mapper.toEquipmentIdentifier
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.model.WearableEvidenceState
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import kotlin.collections.listOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp(
    viewModel: WearableViewModel = viewModel(factory = WearableViewModel.Factory)) {
    val uiState by viewModel.uiState.collectAsState()
    
    WearAppContent(
        uiState = uiState,
        onToggleEvidence = { id, state ->
            viewModel.toggleEvidence(id, state) }
    )
}

@Composable
fun WearAppContent(
    uiState: WearableOperationData,
    onToggleEvidence: (EvidenceType, EvidenceValidationType) -> Unit
) {
    val listState = rememberScalingLazyListState()

    LocalThemeProvider {
        Scaffold(
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
            positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                ScalingLazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    state = listState,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Chip(
                            modifier = Modifier
                                .width(IntrinsicSize.Max)
                                .wrapContentHeight()
                                .align(Alignment.Center),
                            onClick = {},
                            enabled = true,
                            colors = ChipDefaults.chipColors(
                                backgroundColor = LocalPalette.current.surfaceContainer,
                            ),
                            border = ChipDefaults.chipBorder(),
                            contentPadding = PaddingValues(4.dp),
                            shape = CircleShape,
                            role = Role.Image,
                            content = {
                                Text(
                                    modifier = Modifier
                                        .wrapContentSize(),
                                    text = uiState.sanityLevel.toPercentageString(),
                                    style = LocalTypography.current.tertiary.bold,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }

                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(
                                items = uiState.evidenceStates,
                                key = { it.type.id }
                            ) { evidence ->
                                EvidenceItem(
                                    modifier = Modifier
                                        .size(36.dp),
                                    evidence = evidence
                                ) {
                                    onToggleEvidence(evidence.type, evidence.state)
                                }
                            }
                        }
                    }

                }

                SanityBorder(
                    sanityLevel = uiState.sanityLevel,
                    color = LocalPalette.current.surface,
                    onColor = LocalPalette.current.primary
                )
            }
        }
    }
}

@Composable
fun EvidenceItem(
    modifier: Modifier = Modifier,
    evidence: WearableEvidenceState,
    onClick: () -> Unit = {}
) {
    Chip(
        modifier = modifier,
        onClick = onClick,
        enabled = evidence.enabled,
        colors = ChipDefaults.chipColors(
            backgroundColor = LocalPalette.current.surfaceContainer,
        ),
        border = ChipDefaults.chipBorder(),
        contentPadding = PaddingValues(4.dp),
        shape = CircleShape,
        content = {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .padding(4.dp),
                painter = painterResource(
                    evidence.type.icon.toDrawableResource()),
                colorFilter = ColorFilter.tint(
                    when(evidence.state) {
                        EvidenceValidationType.POSITIVE -> LocalPalette.current.tertiary
                        EvidenceValidationType.NEUTRAL -> LocalPalette.current.onSurface
                        EvidenceValidationType.NEGATIVE -> LocalPalette.current.primary
                    }
                ),
                contentDescription = "Evidence Icon"
            )
        }
    )
}

@Composable
private fun SanityBorder(
    sanityLevel: Float,
    color: Color,
    onColor: Color
) {
    val isRound = LocalConfiguration.current.isScreenRound
    val strokeWidth = 6.dp
    val inset = 6.dp

    Canvas(modifier = Modifier.fillMaxSize()) {
        val sw = strokeWidth.toPx()
        val ins = inset.toPx()
        val totalOffset = ins + sw / 2
        val w = size.width
        val h = size.height

        val bgSw = ins + sw
        val bgOffset = bgSw / 2

        if (isRound) {
            // Background ring extending to edge
            drawArc(
                color = color,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = bgSw),
                size = Size(w - bgSw, h - bgSw),
                topLeft = Offset(bgOffset, bgOffset)
            )

            // Progress arc
            drawArc(
                color = onColor,
                startAngle = -90f,
                sweepAngle = 360f * sanityLevel,
                useCenter = false,
                style = Stroke(width = sw, cap = StrokeCap.Round),
                size = Size(w - totalOffset * 2, h - totalOffset * 2),
                topLeft = Offset(totalOffset, totalOffset)
            )
        } else {
            // Background rect extending to edge
            drawRect(
                color = color,
                topLeft = Offset(bgOffset, bgOffset),
                size = Size(w - bgSw, h - bgSw),
                style = Stroke(width = bgSw)
            )

            // Square border logic
            val left = totalOffset
            val right = w - totalOffset
            val top = totalOffset
            val bottom = h - totalOffset

            val fullPath = Path().apply {
                moveTo(w / 2f, top)
                lineTo(right, top)
                lineTo(right, bottom)
                lineTo(left, bottom)
                lineTo(left, top)
                lineTo(w / 2f, top)
            }

            val pathMeasure = PathMeasure()
            pathMeasure.setPath(fullPath, false)
            val segmentPath = Path()
            pathMeasure.getSegment(
                0f,
                pathMeasure.length * sanityLevel,
                segmentPath,
                true
            )

            drawPath(
                path = segmentPath,
                color = onColor,
                style = Stroke(width = sw, cap = StrokeCap.Round)
            )
        }
    }
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun WearAppPreviewLargeRound() {
    WearAppPreviewContent()
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun WearAppPreviewSmallRound() {
    WearAppPreviewContent()
}

@Preview(device = WearDevices.SQUARE, showSystemUi = true)
@Composable
fun WearAppPreviewSquare() {
    WearAppPreviewContent()
}

@Preview(device = WearDevices.RECT, showSystemUi = true)
@Composable
fun WearAppPreviewRect() {
    WearAppPreviewContent()
}

@Composable
fun WearAppPreviewContent() {
    val sampleData = WearableOperationData(
        mapName = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
        difficultyName = DifficultyResources.DifficultyType.AMATEUR,
        setupTimeRemaining = 0L,
        sanityLevel = 0.75f,
        evidenceStates = listOf(
            WearableEvidenceState(
                EvidenceType(
                    EvidenceResources.EvidenceIdentifier.EMF_5,
                    EvidenceResources.EvidenceTitle.EMF_5,
                    EvidenceResources.EvidenceIcon.EMF_5
                ),
                EvidenceValidationType.NEGATIVE, true),
            WearableEvidenceState(
                EvidenceType(
                    EvidenceResources.EvidenceIdentifier.GHOST_ORBS,
                    EvidenceResources.EvidenceTitle.GHOST_ORBS,
                    EvidenceResources.EvidenceIcon.GHOST_ORBS
                ),
                EvidenceValidationType.NEUTRAL, true),
            WearableEvidenceState(
                EvidenceType(
                    EvidenceResources.EvidenceIdentifier.DOTS,
                    EvidenceResources.EvidenceTitle.DOTS,
                    EvidenceResources.EvidenceIcon.DOTS
                ),
                EvidenceValidationType.POSITIVE, true)
        )
    )
    WearAppContent(
        uiState = sampleData,
        onToggleEvidence = { _, _ -> }
    )
}
