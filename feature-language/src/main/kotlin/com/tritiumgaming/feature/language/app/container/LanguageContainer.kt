package com.tritiumgaming.feature.language.app.container

import com.tritiumgaming.shared.data.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.data.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.data.language.usecase.SetDefaultLanguageUseCase

class LanguageContainer(
    internal val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    internal val getDefaultLanguageUseCase: GetDefaultLanguageUseCase,
    internal val setDefaultLanguageUseCase: SetDefaultLanguageUseCase,
    //val initLanguageDataStoreUseCase: SetupLanguageUseCase,
    internal val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    internal val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase,
)
