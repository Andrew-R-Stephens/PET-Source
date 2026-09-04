package com.tritiumgaming.shared.data.ads.model

/**
 * Enum representing the status of a Rewarded Ad.
 */
enum class RewardedAdStatus {
    IDLE,
    LOADING,
    LOADED
}

/**
 * Data class representing the state of a Rewarded Ad.
 */
data class RewardedAdState(
    val status: RewardedAdStatus = RewardedAdStatus.IDLE,
    val reward: RewardedAdReward? = null
)
