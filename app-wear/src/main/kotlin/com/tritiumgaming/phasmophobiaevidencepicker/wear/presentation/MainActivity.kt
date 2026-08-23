package com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.InlineSlider
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.tooling.preview.devices.WearDevices
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.wear.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation.viewmodel.WearableViewModel
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.model.WearableEvidenceState
import com.tritiumgaming.shared.data.wearable.model.WearableInvestigationData
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
fun WearApp(
    viewModel: WearableViewModel = viewModel(factory = WearableViewModel.Factory)) {
    val uiState by viewModel.uiState.collectAsState()
    
    WearAppContent(
        uiState = uiState,
        onToggleEvidence = { id, state ->
            viewModel.toggleEvidence(id, state) },
        onUpdateSanity = { level ->
            viewModel.updateSanity(level) }
    )
}

enum class WearScreen {
    Main, SanitySlider
}

@Composable
fun WearAppContent(
    uiState: WearableOperationData,
    onToggleEvidence: (EvidenceType, EvidenceValidationType) -> Unit,
    onUpdateSanity: (Float) -> Unit
) {
    var currentScreen by remember { mutableStateOf(WearScreen.Main) }

    LocalThemeProvider(
        palette = uiState.palette,
        typography = uiState.typography
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
                .background(LocalPalette.current.surface),
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                when (currentScreen) {
                    WearScreen.Main -> MainScreen(
                        uiState = uiState,
                        onToggleEvidence = onToggleEvidence,
                        onLongPressSanity = { currentScreen = WearScreen.SanitySlider }
                    )
                    WearScreen.SanitySlider -> SanitySliderScreen(
                        sanityLevel = uiState.investigationData.sanityLevel,
                        onUpdateSanity = onUpdateSanity,
                        onBack = { currentScreen = WearScreen.Main }
                    )
                }

                SanityBorder(
                    sanityLevel = uiState.investigationData.sanityLevel,
                    color = LocalPalette.current.surface,
                    onColor = LocalPalette.current.primary
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    uiState: WearableOperationData,
    onToggleEvidence: (EvidenceType, EvidenceValidationType) -> Unit,
    onLongPressSanity: () -> Unit
) {
    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 20.dp),
        autoCentering = null,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SanityBubble(
                sanityLevel = uiState.investigationData.sanityLevel,
                onLongPress = onLongPressSanity
            )
        }

        item {
            EvidenceGrid(
                evidenceStates = uiState.investigationData.evidenceStates,
                onToggleEvidence = onToggleEvidence
            )
        }
    }
}

@Composable
fun SanityBubble(
    sanityLevel: Float,
    onLongPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(LocalPalette.current.surfaceContainer)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongPress() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sanityLevel.toPercentageString(),
            style = LocalTypography.current.tertiary.bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = LocalPalette.current.onSurface
        )
    }
}

@Composable
fun EvidenceGrid(
    evidenceStates: List<WearableEvidenceState>,
    onToggleEvidence: (EvidenceType, EvidenceValidationType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Simple manual grid for better control on Wear OS
        val row1 = evidenceStates.take(4)
        val row2 = evidenceStates.drop(4)

        EvidenceRow(row1, onToggleEvidence)
        EvidenceRow(row2, onToggleEvidence)
    }
}

@Composable
fun EvidenceRow(
    items: List<WearableEvidenceState>,
    onToggleEvidence: (EvidenceType, EvidenceValidationType) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
    ) {
        items.forEach { evidence ->
            EvidenceItem(
                modifier = Modifier.size(36.dp),
                evidence = evidence,
                onClick = { onToggleEvidence(evidence.type, evidence.state) }
            )
        }
    }
}

@Composable
fun SanitySliderScreen(
    sanityLevel: Float,
    onUpdateSanity: (Float) -> Unit,
    onBack: () -> Unit
) {
    var sliderValue by remember { mutableStateOf(sanityLevel) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Sanity: ${(sliderValue * 100).toInt()}%",
            style = LocalTypography.current.tertiary.bold,
            fontSize = 14.sp
        )
        InlineSlider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                onUpdateSanity(it)
            },
            steps = 19, // 20 intervals = 19 steps between 0 and 1
            decreaseIcon = {
                Text(
                    "-",
                    style = LocalTypography.current.primary.bold,
                    fontSize = 24.sp,
                    color = LocalPalette.current.primary
                ) },
            increaseIcon = {
                Text(
                    "+",
                    style = LocalTypography.current.primary.bold,
                    fontSize = 24.sp,
                    color = LocalPalette.current.primary
                ) },
            modifier = Modifier.fillMaxWidth(),
            valueRange = 0f..1f
        )
        Chip(
            onClick = onBack,
            label = { Text("Done", style = LocalTypography.current.primary.regular) },
            colors = ChipDefaults.secondaryChipColors(),
            modifier = Modifier.height(32.dp)
        )
    }
}

@Composable
fun EvidenceItem(
    modifier: Modifier = Modifier,
    evidence: WearableEvidenceState,
    onClick: () -> Unit = {}
) {
    val backgroundColor = when (evidence.state) {
        EvidenceValidationType.POSITIVE -> LocalPalette.current.tertiary
        EvidenceValidationType.NEUTRAL -> Color.Transparent
        EvidenceValidationType.NEGATIVE -> LocalPalette.current.primary
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(enabled = evidence.enabled, onClick = onClick)
            .then(
                if (evidence.enabled)
                    Modifier.border(
                        2.dp,
                        LocalPalette.current.onSurface,
                        CircleShape
                    )
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            painter = painterResource(
                evidence.type.icon.toDrawableResource()),
            colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            contentDescription = "Evidence Icon"
        )
    }
}

@Composable
private fun SanityBorder(
    sanityLevel: Float,
    color: Color,
    onColor: Color
) {
    val isRound = LocalConfiguration.current.isScreenRound
    val strokeWidth = 4.dp
    val inset = 5.dp

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
        investigationData = WearableInvestigationData(
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
                    EvidenceValidationType.NEGATIVE, true
                ),
                WearableEvidenceState(
                    EvidenceType(
                        EvidenceResources.EvidenceIdentifier.GHOST_ORBS,
                        EvidenceResources.EvidenceTitle.GHOST_ORBS,
                        EvidenceResources.EvidenceIcon.GHOST_ORBS
                    ),
                    EvidenceValidationType.NEUTRAL, true
                ),
                WearableEvidenceState(
                    EvidenceType(
                        EvidenceResources.EvidenceIdentifier.DOTS,
                        EvidenceResources.EvidenceTitle.DOTS,
                        EvidenceResources.EvidenceIcon.DOTS
                    ),
                    EvidenceValidationType.POSITIVE, true
                )
            )
        ),
        palette = PaletteType.CLASSIC,
        typography = TypographyType.CLASSIC
    )
    WearAppContent(
        uiState = sampleData,
        onToggleEvidence = { _, _ -> },
        onUpdateSanity = {}
    )
}
