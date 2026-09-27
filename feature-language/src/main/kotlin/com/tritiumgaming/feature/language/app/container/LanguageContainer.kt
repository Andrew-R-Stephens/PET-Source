package com.tritiumgaming.feature.language.app.container

import com.tritiumgaming.data.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.data.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.data.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.data.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.data.language.usecase.SetDefaultLanguageUseCase

class LanguageContainer(
    val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    val getDefaultLanguageUseCase: GetDefaultLanguageUseCase,
    val setDefaultLanguageUseCase: SetDefaultLanguageUseCase,
    //val initLanguageDataStoreUseCase: SetupLanguageUseCase,
    val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase,
)
