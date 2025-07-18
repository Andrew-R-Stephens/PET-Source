package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.admob

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tritiumgaming.phasmophobiaevidencepicker.R

@Composable
fun AdmobBanner(
    modifier: Modifier = Modifier
) {
    val adID = stringResource(R.string.ad_banner_1)

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adID
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Composable
fun AdmobBanner2(
    modifier: Modifier = Modifier
) {
    val adID = stringResource(R.string.ad_banner_1)

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adID
                loadAd(AdRequest.Builder().build())
            }
        }
    )

    //BannerAd()

}

@Composable
fun BannerAd(
    modifier: Modifier = Modifier,
    adView: AdView
) {
    // Ad load does not work in preview mode because it requires a network connection.
    if (LocalInspectionMode.current) {
        Box {
            Text(
                text = "Google Mobile Ads preview banner.",
                modifier
                    .align(Alignment.Center)
            )
        }
        return
    }

    AndroidView(
        modifier = modifier
            .wrapContentSize(),
        factory = { adView }
    )

    // Pause and resume the AdView when the lifecycle is paused and resumed.
    LifecycleResumeEffect(adView) {
        adView.resume()
        onPauseOrDispose { adView.pause() }
    }
}