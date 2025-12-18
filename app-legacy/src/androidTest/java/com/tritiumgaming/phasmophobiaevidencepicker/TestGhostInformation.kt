package com.tritiumgaming.phasmophobiaevidencepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R

@Composable
@Preview(locale = "zh-rCN")
private fun Test() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_info_dayan
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_strengths_dayan
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_weaknesses_dayan
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_huntingdata_dayan
                    )
                ),
                fontSize = 12.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_info_gallu
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_strengths_gallu
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_weaknesses_gallu
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_huntingdata_gallu
                    )
                ),
                fontSize = 12.sp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_info_obambo
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_strengths_obambo
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(
                        R.string.ghost_weaknesses_obambo
                    )
                ),
                fontSize = 12.sp
            )

            Text(
                text = AnnotatedString.fromHtml(
                    stringResource(R.string.ghost_huntingdata_obambo)
                ),
                fontSize = 12.sp
            )

        }
    }
}
