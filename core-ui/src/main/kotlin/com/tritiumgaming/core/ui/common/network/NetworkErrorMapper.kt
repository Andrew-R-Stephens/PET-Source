package com.tritiumgaming.core.ui.common.network

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.core.common.network.FirebaseFunctionError

@get:StringRes
val FirebaseFunctionError.toStringResource: Int
    get() = when (this) {
        FirebaseFunctionError.UNSUPPORTED_VERSION -> R.string.error_unsupported_version
        FirebaseFunctionError.AUTH_REQUIRED_PREFERENCES -> R.string.error_auth_required_preferences
        FirebaseFunctionError.INTERNAL_ERROR_MARKETPLACE_STATE -> R.string.error_internal_marketplace_state
        FirebaseFunctionError.AUTH_REQUIRED_PURCHASE -> R.string.error_auth_required_purchase
        FirebaseFunctionError.INVALID_ITEM_TYPE -> R.string.error_invalid_item_type
        FirebaseFunctionError.INTERNAL_ERROR_PURCHASE -> R.string.error_internal_purchase
        FirebaseFunctionError.ITEM_NOT_FOUND -> R.string.error_item_not_found
        FirebaseFunctionError.INSUFFICIENT_CREDITS -> R.string.error_insufficient_credits
        FirebaseFunctionError.BUNDLE_NOT_FOUND -> R.string.error_bundle_not_found
        FirebaseFunctionError.BUNDLE_NO_ITEMS -> R.string.error_bundle_no_items
        FirebaseFunctionError.BUNDLE_ALREADY_UNLOCKED -> R.string.error_bundle_already_unlocked
        FirebaseFunctionError.INTERNAL_ERROR_FETCH_TYPOGRAPHIES -> R.string.error_internal_fetch_typographies
        FirebaseFunctionError.INTERNAL_ERROR_FETCH_PALETTES -> R.string.error_internal_fetch_palettes
        FirebaseFunctionError.INTERNAL_ERROR_FETCH_BUNDLES -> R.string.error_internal_fetch_bundles
        FirebaseFunctionError.AUTH_REQUIRED_ADD_CREDITS -> R.string.error_auth_required_add_credits
        FirebaseFunctionError.INVALID_CREDIT_AMOUNT -> R.string.error_invalid_credit_amount
        FirebaseFunctionError.INTERNAL_ERROR_ADD_CREDITS -> R.string.error_internal_add_credits
        FirebaseFunctionError.UNKNOWN -> R.string.error_unknown
    }
