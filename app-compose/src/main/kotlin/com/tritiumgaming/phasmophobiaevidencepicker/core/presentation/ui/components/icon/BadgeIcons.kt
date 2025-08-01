package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.vector.getMarkCheckVector
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.vector.getMarkPriorityVector
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.vector.getMarkXVector

@Composable
fun MarkCheckCircleIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    foregroundColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center),
            imageVector = getMarkCheckVector(
                listOf(
                    foregroundColor
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun MarkXCircleIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    foregroundColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center),
            imageVector = getMarkXVector(
                listOf(
                    foregroundColor
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun MarkPriorityCircleIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    foregroundColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center),
            imageVector = getMarkPriorityVector(
                listOf(
                    foregroundColor
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MarkCheckCircleIcon(
                modifier = Modifier
                    .size(48.dp),
                backgroundColor = LocalPalette.current.surface.color,
                foregroundColor = LocalPalette.current.textFamily.body
            )
            MarkXCircleIcon(
                modifier = Modifier.size(48.dp),
                backgroundColor = LocalPalette.current.surface.color,
                foregroundColor = LocalPalette.current.textFamily.body
            )
            MarkPriorityCircleIcon(
                modifier = Modifier.size(48.dp),
                backgroundColor = LocalPalette.current.surface.color,
                foregroundColor = LocalPalette.current.textFamily.body
            )
        }
    }
}
