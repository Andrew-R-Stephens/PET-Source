package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

data class EvidencePopupRecord(
    val id: String = "0",
    @IntegerRes val cost: Int,
    @IntegerRes val unlockLevel: IntArray,
    @StringRes val descriptions: IntArray,
    @DrawableRes val animations: IntArray
): PopupRecord() {

    fun getCost(c: Context): String {
        return c.resources.getInteger(cost).toString()
    }

    @DrawableRes
    fun getAnimationAt(i: Int): Int {
        return animations[i]
    }

    fun getDescriptionAt(c: Context, i: Int): String {
        return c.getString(descriptions[i])
    }

    fun getUnlockLevelAt(c: Context, i: Int): String {
        return c.resources.getInteger(unlockLevel[i]).toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EvidencePopupRecord

        if (id != other.id) return false
        if (cost != other.cost) return false
        if (!unlockLevel.contentEquals(other.unlockLevel)) return false
        if (!descriptions.contentEquals(other.descriptions)) return false
        if (!animations.contentEquals(other.animations)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cost
        result = 31 * result + id.hashCode()
        result = 31 * result + unlockLevel.contentHashCode()
        result = 31 * result + descriptions.contentHashCode()
        result = 31 * result + animations.contentHashCode()
        return result
    }

}