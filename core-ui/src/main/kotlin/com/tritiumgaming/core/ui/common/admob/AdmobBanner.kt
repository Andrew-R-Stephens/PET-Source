package com.tritiumgaming.core.ui.common.admob

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tritiumgaming.core.resources.R


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
    adId: String
) {
    // Ad load does not work in preview mode because it requires a network connection.
    if (LocalInspectionMode.current) {
        Box {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Google Mobile Ads preview banner."
            )
        }
        return
    }

    val adView = AdView(LocalContext.current).apply {
        //setAdSize(AdSize.BANNER)
        adUnitId = adId

        // 2. Calculate Adaptive Size
        val displayMetrics = context.resources.displayMetrics
        val adWidthPixels = displayMetrics.widthPixels.toFloat()
        val density = displayMetrics.density
        val adWidth = (adWidthPixels / density).toInt()

        setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth))
        loadAd(AdRequest.Builder().build())

        /*val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, width)
        setAdSize(adSize)
        adUnitId = adId
        loadAd(AdRequest.Builder().build())*/
    }

    val rememberAdView = remember { adView }
    Log.d("TAG", "BannerAd Loaded: $rememberAdView")

    // Pause and resume the AdView when the lifecycle is paused and resumed.
    LifecycleResumeEffect(rememberAdView) {
        rememberAdView.resume()
        onPauseOrDispose { rememberAdView.pause() }
    }

    AndroidView(
        modifier = modifier
            .wrapContentSize(),
        factory = { rememberAdView },
        onRelease = { it.destroy() }
    )

}
