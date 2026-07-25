package com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import com.tritiumgaming.phasmophobiaevidencepicker.wear.presentation.viewmodel.WearableViewModel
import com.tritiumgaming.shared.data.wearable.model.WearableEvidenceState

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
    val listState = rememberScalingLazyListState()

    MaterialTheme {
        Scaffold(
            timeText = { TimeText() },
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
            positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
        ) {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Phasmophobia Companion",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
                
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = uiState.mapName, fontSize = 16.sp)
                        Text(text = uiState.difficultyName, fontSize = 14.sp, color = Color.Gray)
                    }
                }
                
                item {
                    Text(text = "Sanity: ${(uiState.sanityLevel * 100).toInt()}%", fontSize = 14.sp)
                }

                item {
                    Text(text = "Evidence", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp))
                }

                items(uiState.evidenceStates) { evidence ->
                    EvidenceItem(evidence) {
                        viewModel.toggleEvidence(evidence.id, evidence.state)
                    }
                }
            }
        }
    }
}

@Composable
fun EvidenceItem(evidence: WearableEvidenceState, onClick: () -> Unit) {
    Chip(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 2.dp),
        onClick = onClick,
        label = { Text(evidence.id.replace("_", " ")) },
        secondaryLabel = { 
            Text(
                text = evidence.state,
                color = when(evidence.state) {
                    "POSITIVE" -> Color.Green
                    "NEGATIVE" -> Color.Red
                    else -> Color.Gray
                }
            )
        },
        enabled = evidence.enabled,
        colors = ChipDefaults.primaryChipColors()
    )
}
