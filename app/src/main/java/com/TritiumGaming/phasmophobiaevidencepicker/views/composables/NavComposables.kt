package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.InvestigationViewModel

@Composable
@Preview
fun TabPanel (
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black, RoundedCornerShape(8.dp))
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            for(i in 0..<3) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .size(48.dp)
                ) {
                    ToggleButton(
                        modifier = Modifier
                            .align(Alignment.Center)
                        //.size(48.dp)
                    )
                }
            }
        }
    }

}

@Composable
@Preview
fun ToggleButton(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationViewModel = viewModel<InvestigationViewModel>(),
    state: Boolean = false
) {
    Image(
        painterResource(id = R.drawable.icon_sanityhead_top),
        contentDescription = "",
        modifier = modifier
            .border(2.dp, Color.White, RoundedCornerShape(4.dp))
    )
}