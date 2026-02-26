package com.tritiumgaming.feature.language.app.container

import com.tritiumgaming.shared.data.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.data.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SetDefaultLanguageUseCase

class LanguageContainer(
    val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    val getDefaultLanguageUseCase: GetDefaultLanguageUseCase,
    val setDefaultLanguageUseCase: SetDefaultLanguageUseCase,
    //val initLanguageDataStoreUseCase: SetupLanguageUseCase,
    val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase,
)
