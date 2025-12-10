package com.tritiumgaming.feature.investigation.ui.popups.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.admob.BannerAd

@Composable
fun InvestigationPopup(
    modifier: Modifier,
    shown: Boolean = false,
    backgroundColor: Color = Color.Transparent,
    content: @Composable (modifier: Modifier) -> Unit
) {
    if(!shown) return

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = backgroundColor.copy(alpha = .75f)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        Modifier.padding(top = 16.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
                    } else {
                        Modifier.padding(top = 16.dp, bottom = 16.dp, start = 0.dp, end = 16.dp)
                    }
                )
        ) {
            content(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            BannerAd(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor),
                adId = stringResource(R.string.ad_banner_1)
            )

        }
    }
}

