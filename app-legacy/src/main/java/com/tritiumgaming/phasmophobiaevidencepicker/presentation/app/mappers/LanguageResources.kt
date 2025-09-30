package com.tritiumgaming.phasmophobiaevidencepicker.presentation.app.mappers

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.core.domain.language.mappers.LanguageResources.LocalizationCode
import com.tritiumgaming.shared.core.domain.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.shared.core.domain.language.mappers.LanguageResources.NativeTitle

@StringRes fun LocalizedTitle.toStringResource(): Int =
    when(this) {
        LocalizedTitle.ZH_HANS ->com.tritiumgaming.core.resources.R.string.language_name_localized_zh_Hans
        LocalizedTitle.CS ->com.tritiumgaming.core.resources.R.string.language_name_localized_cs
        LocalizedTitle.DE ->com.tritiumgaming.core.resources.R.string.language_name_localized_de
        LocalizedTitle.EN ->com.tritiumgaming.core.resources.R.string.language_name_localized_en
        LocalizedTitle.ES ->com.tritiumgaming.core.resources.R.string.language_name_localized_es
        LocalizedTitle.FR ->com.tritiumgaming.core.resources.R.string.language_name_localized_fr
        LocalizedTitle.IT ->com.tritiumgaming.core.resources.R.string.language_name_localized_it
        LocalizedTitle.JA ->com.tritiumgaming.core.resources.R.string.language_name_localized_ja
        LocalizedTitle.PT ->com.tritiumgaming.core.resources.R.string.language_name_localized_pt
        LocalizedTitle.RU ->com.tritiumgaming.core.resources.R.string.language_name_localized_ru
        LocalizedTitle.UK ->com.tritiumgaming.core.resources.R.string.language_name_localized_uk
    }

@StringRes fun NativeTitle.toStringResource(): Int =
    when(this) {
        NativeTitle.ZH_HANS ->com.tritiumgaming.core.resources.R.string.language_name_native_zh_Hans
        NativeTitle.CS ->com.tritiumgaming.core.resources.R.string.language_name_native_cs
        NativeTitle.DE ->com.tritiumgaming.core.resources.R.string.language_name_native_de
        NativeTitle.EN ->com.tritiumgaming.core.resources.R.string.language_name_native_en
        NativeTitle.ES ->com.tritiumgaming.core.resources.R.string.language_name_native_es
        NativeTitle.FR ->com.tritiumgaming.core.resources.R.string.language_name_native_fr
        NativeTitle.IT ->com.tritiumgaming.core.resources.R.string.language_name_native_it
        NativeTitle.JA ->com.tritiumgaming.core.resources.R.string.language_name_native_ja
        NativeTitle.PT ->com.tritiumgaming.core.resources.R.string.language_name_native_pt
        NativeTitle.RU ->com.tritiumgaming.core.resources.R.string.language_name_native_ru
        NativeTitle.UK ->com.tritiumgaming.core.resources.R.string.language_name_native_uk
    }

@StringRes fun LocalizationCode.toStringResource(): Int =
    when(this) {
        LocalizationCode.ZH_HANS ->com.tritiumgaming.core.resources.R.string.language_code_zh_Hans
        LocalizationCode.CS ->com.tritiumgaming.core.resources.R.string.language_code_cs
        LocalizationCode.DE ->com.tritiumgaming.core.resources.R.string.language_code_de
        LocalizationCode.EN ->com.tritiumgaming.core.resources.R.string.language_code_en
        LocalizationCode.ES ->com.tritiumgaming.core.resources.R.string.language_code_es
        LocalizationCode.FR ->com.tritiumgaming.core.resources.R.string.language_code_fr
        LocalizationCode.IT ->com.tritiumgaming.core.resources.R.string.language_code_it
        LocalizationCode.JA ->com.tritiumgaming.core.resources.R.string.language_code_ja
        LocalizationCode.PT ->com.tritiumgaming.core.resources.R.string.language_code_pt
        LocalizationCode.RU ->com.tritiumgaming.core.resources.R.string.language_code_ru
        LocalizationCode.UK ->com.tritiumgaming.core.resources.R.string.language_code_uk
    }