package com.tritiumgaming.shared.core.common.network

enum class FirebaseFunctionMessages {
    BUNDLE_UNLOCKED,
    THEME_UNLOCKED,
    TYPOGRAPHY_UNLOCKED,
    CREDITS_EARNED
}

enum class FirebaseFunctionError(val code: Int) {
    UNSUPPORTED_VERSION(1000),
    AUTH_REQUIRED_PREFERENCES(1001),
    INTERNAL_ERROR_MARKETPLACE_STATE(1002),
    AUTH_REQUIRED_PURCHASE(1003),
    INVALID_ITEM_TYPE(1004),
    INTERNAL_ERROR_PURCHASE(1005),
    ITEM_NOT_FOUND(1006),
    INSUFFICIENT_CREDITS(1007),
    BUNDLE_NOT_FOUND(1008),
    BUNDLE_NO_ITEMS(1009),
    BUNDLE_ALREADY_UNLOCKED(1010),
    INTERNAL_ERROR_FETCH_TYPOGRAPHIES(1011),
    INTERNAL_ERROR_FETCH_PALETTES(1012),
    INTERNAL_ERROR_FETCH_BUNDLES(1013),
    AUTH_REQUIRED_ADD_CREDITS(1014),
    INVALID_CREDIT_AMOUNT(1015),
    INTERNAL_ERROR_ADD_CREDITS(1016),
    UNKNOWN(-1);

    companion object {
        fun fromCode(code: Int?): FirebaseFunctionError {
            if (code == null) return UNKNOWN
            return entries.find { it.code == code } ?: UNKNOWN
        }

        fun fromString(error: String?): FirebaseFunctionError {
            if (error.isNullOrBlank()) return UNKNOWN

            val exactCode = error.trim().toIntOrNull()
            if (exactCode != null) {
                return fromCode(exactCode)
            }

            val nameMatch = entries.find { it.name.equals(error.trim(), ignoreCase = true) }
            if (nameMatch != null) {
                return nameMatch
            }

            val extractedCode = Regex("\\d+").find(error)?.value?.toIntOrNull()
            if (extractedCode != null) {
                val match = fromCode(extractedCode)
                if (match != UNKNOWN) return match
            }

            return UNKNOWN
        }
    }
}
