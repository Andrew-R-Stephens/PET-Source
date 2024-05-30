package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities

import java.util.Locale

@JvmInline
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
        val Uppercase = TextCase(1)
        val Lowercase = TextCase(2)
        val CamelCase = TextCase(3)
        val FirstCharacter = TextCase(4)

        fun values(): List<TextCase> = listOf(Uppercase, Lowercase, CamelCase, FirstCharacter, Unspecified)

        val Unspecified = TextCase(Int.MIN_VALUE)

        fun convertCase(text: String, textCase: TextCase): String {
            return when (textCase) {
                Uppercase -> text.uppercase()
                Lowercase -> text.lowercase()
                CamelCase -> {
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

                FirstCharacter -> text.getOrNull(0)?.uppercase() ?: text
                Unspecified -> text
                else -> {
                    text
                }
            }
        }
    }
}


