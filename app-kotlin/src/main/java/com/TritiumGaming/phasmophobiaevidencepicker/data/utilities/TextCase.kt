package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities

import java.util.Locale

@kotlin.jvm.JvmInline
value class TextCase internal constructor(internal val value: Int) {

    override fun toString(): String {
        return when (this) {
            Uppercase -> "Uppercase"
            Lowercase -> "Lowercase"
            CamelCase -> "Camel Case"
            FirstCharacter -> "First Character"
            Unspecified -> "Unspecified"
            else -> "Unspecified"
        }
    }

    companion object {
        /** Align the text on the left edge of the container. */
        val Uppercase = TextCase(1)

        /** Align the text on the right edge of the container. */
        val Lowercase = TextCase(2)

        /** Align the text in the center of the container. */
        val CamelCase = TextCase(3)

        /** Align the text in the center of the container. */
        val FirstCharacter = TextCase(4)
        /**
         * Return a list containing all possible values of TextAlign.
         */
        fun values(): List<TextCase> = listOf(Uppercase, Lowercase, CamelCase, FirstCharacter, Unspecified)

        /**
         * This represents an unset value, a usual replacement for "null" when a primitive value
         * is desired.
         */
        val Unspecified = TextCase(Int.MIN_VALUE)

        fun convertCase(text: String, textCase: TextCase): String {
            return when (textCase) {
                TextCase.Uppercase -> text.uppercase()
                TextCase.Lowercase -> text.lowercase()
                TextCase.CamelCase -> {
                    val words = text.split(" ")
                    val builder = StringBuilder()
                    for (i in words.indices) {
                        var word = words[i]
                        word = if (i == 0) {
                            if (word.isEmpty()) word else word.lowercase(Locale.getDefault())
                        } else {
                            if (word.isEmpty()) word else word[0].uppercaseChar()
                                .toString() + word.substring(1).lowercase(
                                Locale.getDefault()
                            )
                        }
                        builder.append(word)
                    }
                    return builder.toString()
                }

                TextCase.FirstCharacter -> text.getOrNull(0)?.uppercase() ?: text
                TextCase.Unspecified -> text
                else -> {
                    text
                }
            }
        }
    }
}


