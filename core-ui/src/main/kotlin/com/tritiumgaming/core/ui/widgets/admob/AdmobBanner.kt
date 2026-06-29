package com.tritiumgaming.core.ui.widgets.admob

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.tritiumgaming.core.resources.R


val LocalAdConsent = staticCompositionLocalOf { AdConsent() }

data class AdConsent(
    val allowPersonalizedAds: Boolean = true,
    val allowAnalytics: Boolean = true
)

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
    val localContext = LocalContext.current
    
    if (LocalInspectionMode.current) {
        // Formula mirrors Google's internal scaling (~14.5% of width, min 50dp, max 120dp)
        val adWidth = LocalConfiguration.current.screenWidthDp
        val simulatedHeight = remember(adWidth) {
            val calculatedHeight = (adWidth / 6.82f).toInt()
            calculatedHeight.coerceIn(50, 120).dp
        }
        // Render a placeholder box that takes up the exact dimension of the actual ad
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(simulatedHeight)
                .background(Color.LightGray), // Visual cue for previewing layout flow
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Google Mobile Ads preview banner (${adWidth}x${simulatedHeight.value.toInt()}dp)")
        }
        return // Short-circuit so we don't accidentally initialize the live AdView in preview
    }

    // 3. Live production flow
    val adConsent = LocalAdConsent.current
    val adView = remember(adConsent) {
        AdView(localContext).apply {
            adUnitId = adId

            val displayMetrics = context.resources.displayMetrics
            val adWidthPixels = displayMetrics.widthPixels.toFloat()
            val density = displayMetrics.density
            val adWidth = (adWidthPixels / density).toInt()

            setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth))
            val adRequest = AdRequest.Builder().apply {
                if (!adConsent.allowPersonalizedAds) {
                    addNetworkExtrasBundle(AdMobAdapter::class.java, Bundle().apply {
                        putString("npa", "1")
                    })
                }
            }.build()
            loadAd(adRequest)
        }
    }

    Log.d("TAG", "BannerAd Loaded: $adView")

    LifecycleResumeEffect(adView) {
        adView.resume()
        onPauseOrDispose { adView.pause() }
    }

    AndroidView(
        modifier = modifier.wrapContentSize(),
        factory = { adView },
        onRelease = { it.destroy() }
    )
}

@Composable
fun BannerAd2(
    modifier: Modifier = Modifier,
    adId: String
) {
    val localContext = LocalContext.current

    // Ad load does not work in preview mode because it requires a network connection.
    if (LocalInspectionMode.current) {
        Box {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Google Mobile Ads preview banner."
            )
        }
    }

    val adView = AdView(localContext).apply {
        //setAdSize(AdSize.BANNER)
        adUnitId = adId

        // 2. Calculate Adaptive Size
        val displayMetrics = context.resources.displayMetrics
        val adWidthPixels = displayMetrics.widthPixels.toFloat()
        val density = displayMetrics.density
        val adWidth = (adWidthPixels / density).toInt()

        //setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth))
        setAdSize(AdSize.getLargeAnchoredAdaptiveBannerAdSize(context, adWidth))
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
