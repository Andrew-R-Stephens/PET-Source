package com.tritiumgaming.shared.data.ads.repository

import com.tritiumgaming.shared.data.ads.model.RewardedAdState
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository interface for managing Rewarded Ads.
 *
 * This interface is defined in the shared module to support KMP, using [Any] for platform-specific
 * types like Context and Activity.
 */
interface RewardedAdRepository {

    /**
     * A [StateFlow] representing the current state of the rewarded ad.
     */
    val adState: StateFlow<RewardedAdState>

    /**
     * Loads the rewarded ad.
     *
     * @param context Platform-specific context (e.g., android.content.Context).
     * @param onAdLoaded Callback triggered when the ad is successfully loaded.
     * @param onAdFailedToLoad Callback triggered when the ad fails to load.
     */
    fun loadAd(
        context: Any,
        onAdLoaded: (() -> Unit)? = null,
        onAdFailedToLoad: ((error: Any?) -> Unit)? = null
    )

    /**
     * Shows the rewarded ad if it is loaded.
     *
     * @param activity Platform-specific activity (e.g., android.app.Activity).
     * @param onRewardEarned Callback triggered when the user earns a reward.
     * @param onAdClosed Callback triggered when the ad is closed.
     * @param onAdFailedToShow Callback triggered when the ad fails to show.
     */
    fun showAd(
        activity: Any,
        onRewardEarned: (amount: Int, type: String) -> Unit,
        onAdClosed: (() -> Unit)? = null,
        onAdFailedToShow: ((error: Any?) -> Unit)? = null
    )

}
