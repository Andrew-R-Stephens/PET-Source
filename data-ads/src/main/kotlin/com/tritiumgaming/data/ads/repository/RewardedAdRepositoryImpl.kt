package com.tritiumgaming.data.ads.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.tritiumgaming.shared.data.ads.model.RewardedAdReward
import com.tritiumgaming.shared.data.ads.model.RewardedAdState
import com.tritiumgaming.shared.data.ads.model.RewardedAdStatus
import com.tritiumgaming.shared.data.ads.repository.RewardedAdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Android implementation of [RewardedAdRepository] using Google Mobile Ads SDK.
 *
 * Enforces the constraint that only one rewarded ad can be loaded or loading at any time.
 * Automatically reloads an ad when one is completed or interrupted.
 */
class RewardedAdRepositoryImpl(
    private val adUnitId: String
) : RewardedAdRepository {

    private val _adState = MutableStateFlow(RewardedAdState())
    override val adState: StateFlow<RewardedAdState> = _adState.asStateFlow()

    private var loadedAd: RewardedAd? = null
    private var isLoading: Boolean = false

    override fun loadAd(
        context: Any,
        onAdLoaded: (() -> Unit)?,
        onAdFailedToLoad: ((error: Any?) -> Unit)?
    ) {
        val appContext = context as? Context ?: run {
            Log.e("RewardedAdRepository", "Invalid context type provided for loading ad.")
            return
        }

        if (isLoading) {
            Log.d("RewardedAdRepository", "Already loading ad. Ignoring request.")
            return
        }

        if (loadedAd != null) {
            Log.d("RewardedAdRepository", "Ad is already loaded.")
            onAdLoaded?.invoke()
            return
        }

        isLoading = true
        _adState.value = RewardedAdState(status = RewardedAdStatus.LOADING)
        Log.d("RewardedAdRepository", "Loading ad for unit: $adUnitId")

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(appContext, adUnitId, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                Log.d("RewardedAdRepository", "Ad successfully loaded.")
                loadedAd = ad
                isLoading = false
                
                val reward = RewardedAdReward(ad.rewardItem.amount, ad.rewardItem.type)
                _adState.value = RewardedAdState(status = RewardedAdStatus.LOADED, reward = reward)
                
                onAdLoaded?.invoke()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                Log.e("RewardedAdRepository", "Ad failed to load. Error: ${loadAdError.message}")
                clearLoadedAd()
                isLoading = false
                _adState.value = RewardedAdState(status = RewardedAdStatus.IDLE)
                onAdFailedToLoad?.invoke(loadAdError)
            }
        })
    }

    override fun showAd(
        activity: Any,
        onRewardEarned: (amount: Int, type: String) -> Unit,
        onAdClosed: (() -> Unit)?,
        onAdFailedToShow: ((error: Any?) -> Unit)?
    ) {
        val appActivity = activity as? Activity ?: run {
            Log.e("RewardedAdRepository", "Invalid activity type provided for showing ad.")
            return
        }
        
        val appContext = appActivity.applicationContext

        if (loadedAd == null) {
            Log.w("RewardedAdRepository", "Attempted to show ad but it was not loaded.")
            onAdFailedToShow?.invoke("Ad not loaded")
            return
        }

        loadedAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d("RewardedAdRepository", "Ad dismissed.")
                    clearLoadedAd()
                    onAdClosed?.invoke()
                    
                    // Automatically reload after completion
                    loadAd(appContext)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e("RewardedAdRepository", "Ad failed to show. Error: ${adError.message}")
                    clearLoadedAd()
                    onAdFailedToShow?.invoke(adError)
                    
                    // Automatically reload after interruption
                    loadAd(appContext)
                }
            }

            ad.show(appActivity) { rewardItem ->
                Log.d("RewardedAdRepository", "User earned reward. Amount: ${rewardItem.amount}, Type: ${rewardItem.type}")
                onRewardEarned(rewardItem.amount, rewardItem.type)
            }
        }
    }

    private fun clearLoadedAd() {
        loadedAd = null
        _adState.value = RewardedAdState(status = RewardedAdStatus.IDLE)
    }
}
