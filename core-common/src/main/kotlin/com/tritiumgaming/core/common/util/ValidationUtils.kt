package com.tritiumgaming.core.common.util

import java.text.Normalizer

/**
 * ValidationUtils
 *
 * A utility class for validating data.
 */
object ValidationUtils {

    /**
     * Validates a string created by a user, with support for non-US keyboards and complex scripts.
     *
     * This function performs the following steps:
     * 1. Normalizes the string to NFC (Normalization Form C) to ensure consistent character representation.
     * 2. Trims leading and trailing whitespace.
     * 3. Ensures the input is not null or blank.
     * 4. Filters out invisible control characters.
     * 5. Validates the length using Unicode code points (correctly handling surrogate pairs like Emojis).
     *
     * @param input The user-provided string to validate.
     * @param minLength The minimum acceptable length in code points.
     * @param maxLength The maximum acceptable length in code points.
     * @return The validated, normalized, and trimmed string.
     * @throws IllegalArgumentException if the validation fails.
     */
    @Throws(IllegalArgumentException::class)
    fun validateUserString(
        input: String?,
        minLength: Int = 1,
        maxLength: Int = Int.MAX_VALUE
    ): String {
        if (input == null) {
            throw IllegalArgumentException("Input string cannot be null.")
        }

        // Normalize to NFC (Normalization Form C)
        var processed = Normalizer.normalize(input, Normalizer.Form.NFC)

        // Trim whitespace (handles Unicode whitespace via Kotlin's trim)
        processed = processed.trim()

        // Disallow backslashes to prevent potential escape sequence issues or injection
        if (processed.contains('\\')) {
            throw IllegalArgumentException("Input string cannot contain backslashes ('\\').")
        }

        // Reject other special characters (allow only letters, digits, and spaces)
        // This uses Unicode-aware checks to support non-US alphanumeric characters.
        if (processed.any { !it.isLetterOrDigit() && !it.isWhitespace() }) {
            throw IllegalArgumentException(
                "Input string cannot contain special characters. " +
                        "Only letters, numbers, and spaces are allowed."
            )
        }

        if (processed.isEmpty()) {
            throw IllegalArgumentException("Input string cannot be empty or only whitespace.")
        }

        // Filter out control characters (ISO control characters, etc.)
        // This regex removes non-printing characters while keeping common ones like space.
        processed = processed.replace(Regex("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]"), "")

        // Re-check after filtering control characters
        if (processed.isEmpty()) {
            throw IllegalArgumentException("Input string contains only invalid or control characters.")
        }

        // Count Unicode code points instead of Chars for accurate length
        val codePointCount = processed.codePointCount(0, processed.length)

        if (codePointCount < minLength) {
            throw IllegalArgumentException("Input string is too short. (Minimum length: $minLength)")
        }

        if (codePointCount > maxLength) {
            throw IllegalArgumentException("Input string is too long. (Maximum length: $maxLength)")
        }

        return processed
    }

}
