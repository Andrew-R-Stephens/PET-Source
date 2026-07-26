package com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.phasmophobiaevidencepicker.wear.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation.viewmodel.WearableViewModel
import com.tritiumgaming.shared.data.codex.mappers.toEquipmentIcon
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.mapper.toEquipmentIdentifier
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.model.WearableEvidenceState
import com.tritiumgaming.shared.data.wearable.model.WearableOperationData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp(viewModel: WearableViewModel = viewModel(factory = WearableViewModel.Factory)) {
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
            val isRound = LocalConfiguration.current.isScreenRound
            val horizontalPadding = 12.dp
            val verticalPadding = if (isRound) 24.dp else 12.dp

            Box(modifier = Modifier.fillMaxSize()) {
                ScalingLazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(
                        start = horizontalPadding,
                        end = horizontalPadding,
                        top = verticalPadding,
                        bottom = verticalPadding
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = "Sanity: ${(uiState.sanityLevel * 100).toInt()}%", 
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            uiState.evidenceStates.forEach {
                                EvidenceItem(it) {
                                    onToggleEvidence(it.type, it.state)
                                }
                            }
                        }
                    }

                    items(uiState.evidenceStates) { evidence ->
                        EvidenceItem(evidence) {
                            onToggleEvidence(evidence.type, evidence.state)
                        }
                    }
                }

                SanityBorder(
                    sanityLevel = uiState.sanityLevel,
                    color = LocalPalette.current.primary
                )
            }
        }
    }
}

@Composable
fun EvidenceItem(
    evidence: WearableEvidenceState,
    onClick: () -> Unit = {}
) {
    Chip(
        modifier = Modifier
            .size(36.dp)
            .padding(4.dp),
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
                modifier = Modifier.size(24.dp),
                painter = painterResource(
                    evidence.type.id.toEquipmentIdentifier().toEquipmentIcon().toDrawableResource()),
                colorFilter = ColorFilter.tint(
                    when(evidence.state) {
                        EvidenceValidationType.POSITIVE -> LocalPalette.current.tertiary
                        EvidenceValidationType.NEGATIVE -> LocalPalette.current.onSurface
                        EvidenceValidationType.NEUTRAL -> LocalPalette.current.primary
                    }
                ),
                contentDescription = "Evidence Icon"
            )
        }
    )
}

@Composable
fun SanityBorder(
    sanityLevel: Float,
    color: Color
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

        if (isRound) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * sanityLevel,
                useCenter = false,
                style = Stroke(width = sw, cap = StrokeCap.Round),
                size = Size(w - totalOffset * 2, h - totalOffset * 2),
                topLeft = Offset(totalOffset, totalOffset)
            )
        } else {
            // Square border logic
            val left = totalOffset
            val right = w - totalOffset
            val top = totalOffset
            val bottom = h - totalOffset
            
            val totalLen = (right - left) * 2 + (bottom - top) * 2
            var currentLen = totalLen * sanityLevel
            
            val topHalf = (right - left) / 2
            val rightSide = (bottom - top)
            val bottomSide = (right - left)
            val leftSide = (bottom - top)
            
            // Segment 1: Top middle to top right
            val s1 = minOf(currentLen, topHalf)
            drawLine(color, Offset(w / 2, top), Offset(w / 2 + s1, top), sw, cap = StrokeCap.Round)
            currentLen -= s1
            
            if (currentLen > 0) {
                // Segment 2: Top right to bottom right
                val s2 = minOf(currentLen, rightSide)
                drawLine(color, Offset(right, top), Offset(right, top + s2), sw, cap = StrokeCap.Round)
                currentLen -= s2
            }
            if (currentLen > 0) {
                // Segment 3: Bottom right to bottom left
                val s3 = minOf(currentLen, bottomSide)
                drawLine(color, Offset(right, bottom), Offset(right - s3, bottom), sw, cap = StrokeCap.Round)
                currentLen -= s3
            }
            if (currentLen > 0) {
                // Segment 4: Bottom left to top left
                val s4 = minOf(currentLen, leftSide)
                drawLine(color, Offset(left, bottom), Offset(left, bottom - s4), sw, cap = StrokeCap.Round)
                currentLen -= s4
            }
            if (currentLen > 0) {
                // Segment 5: Top left to top middle
                val s5 = minOf(currentLen, topHalf)
                drawLine(color, Offset(left, top), Offset(left + s5, top), sw, cap = StrokeCap.Round)
            }
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
        mapName = "Tanglewood Drive",
        difficultyName = "Professional",
        sanityLevel = 0.75f,
        evidenceStates =
            listOf(
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
                EvidenceValidationType.POSITIVE, true),
            WearableEvidenceState(
                EvidenceType(
                    EvidenceResources.EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceResources.EvidenceTitle.ULTRAVIOLET_LIGHT,
                    EvidenceResources.EvidenceIcon.ULTRAVIOLET_LIGHT
                ),
                EvidenceValidationType.NEGATIVE, false),
            WearableEvidenceState(
                EvidenceType(
                    EvidenceResources.EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceResources.EvidenceTitle.ULTRAVIOLET_LIGHT,
                    EvidenceResources.EvidenceIcon.ULTRAVIOLET_LIGHT
                ),
                EvidenceValidationType.NEUTRAL, false),
            WearableEvidenceState(
                EvidenceType(
                    EvidenceResources.EvidenceIdentifier.ULTRAVIOLET_LIGHT,
                    EvidenceResources.EvidenceTitle.ULTRAVIOLET_LIGHT,
                    EvidenceResources.EvidenceIcon.ULTRAVIOLET_LIGHT
                ),
                EvidenceValidationType.POSITIVE, false),
        )
    )
    WearAppContent(
        uiState = sampleData,
        onToggleEvidence = { _, _ -> }
    )
}
