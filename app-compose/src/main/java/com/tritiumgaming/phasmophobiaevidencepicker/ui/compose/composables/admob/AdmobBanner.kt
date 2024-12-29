package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.admob

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
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
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = adID
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}