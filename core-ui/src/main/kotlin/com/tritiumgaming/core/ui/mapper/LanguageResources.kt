package com.tritiumgaming.core.ui.mapper

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.LocalizationCode
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.NativeTitle

@StringRes fun LocalizedTitle.toStringResource(): Int =
    when(this) {
        LocalizedTitle.ZH_HANS -> R.string.language_name_localized_zh_Hans
        LocalizedTitle.CS -> R.string.language_name_localized_cs
        LocalizedTitle.DE -> R.string.language_name_localized_de
        LocalizedTitle.EN -> R.string.language_name_localized_en
        LocalizedTitle.ES -> R.string.language_name_localized_es
        LocalizedTitle.FR -> R.string.language_name_localized_fr
        LocalizedTitle.IT -> R.string.language_name_localized_it
        LocalizedTitle.JA -> R.string.language_name_localized_ja
        LocalizedTitle.PT -> R.string.language_name_localized_pt
        LocalizedTitle.RU -> R.string.language_name_localized_ru
        LocalizedTitle.UK -> R.string.language_name_localized_uk
    }

@StringRes fun NativeTitle.toStringResource(): Int =
    when(this) {
        NativeTitle.ZH_HANS -> R.string.language_name_native_zh_Hans
        NativeTitle.CS -> R.string.language_name_native_cs
        NativeTitle.DE -> R.string.language_name_native_de
        NativeTitle.EN -> R.string.language_name_native_en
        NativeTitle.ES -> R.string.language_name_native_es
        NativeTitle.FR -> R.string.language_name_native_fr
        NativeTitle.IT -> R.string.language_name_native_it
        NativeTitle.JA -> R.string.language_name_native_ja
        NativeTitle.PT -> R.string.language_name_native_pt
        NativeTitle.RU -> R.string.language_name_native_ru
        NativeTitle.UK -> R.string.language_name_native_uk
    }

@StringRes fun LocalizationCode.toStringResource(): Int =
    when(this) {
        LocalizationCode.ZH_HANS -> R.string.language_code_zh_Hans
        LocalizationCode.CS -> R.string.language_code_cs
        LocalizationCode.DE -> R.string.language_code_de
        LocalizationCode.EN -> R.string.language_code_en
        LocalizationCode.ES -> R.string.language_code_es
        LocalizationCode.FR -> R.string.language_code_fr
        LocalizationCode.IT -> R.string.language_code_it
        LocalizationCode.JA -> R.string.language_code_ja
        LocalizationCode.PT -> R.string.language_code_pt
        LocalizationCode.RU -> R.string.language_code_ru
        LocalizationCode.UK -> R.string.language_code_uk
    }