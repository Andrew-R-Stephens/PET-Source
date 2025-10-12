package com.tritiumgaming.feature.home.ui.applanguages

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity
import com.tritiumgaming.shared.core.domain.language.source.LanguageDatastore
import com.tritiumgaming.shared.core.domain.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.GetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetupLanguageUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class LanguageScreenViewModel(
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val getDefaultLanguageUseCase: GetDefaultLanguageUseCase,
    private val setDefaultLanguageUseCase: SetDefaultLanguageUseCase,
    private val initLanguageDataStoreUseCase: SetupLanguageUseCase,
    private val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    private val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase,
    private val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase
) : ViewModel() {

    /**
     * Language
     */
    val languageList: List<LanguageEntity>
        get() {
            var languages: List<LanguageEntity> = emptyList()

            try {
                languages = getAvailableLanguagesUseCase().getOrThrow()

                setDefaultLanguageUseCase(
                    localeLanguage = Locale.getDefault().language,
                    languages = languages
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return languages
        }

    private val _currentLanguageCode : StateFlow<LanguageDatastore.LanguagePreferences> =
        initFlowLanguageUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = LanguageDatastore.LanguagePreferences(
                    languageCode = getDefaultLanguageUseCase().getOrNull()?.code
                        ?: Locale.ENGLISH.language
                )
            )
    val currentLanguageCode = _currentLanguageCode
    fun setCurrentLanguageCode(languageCode: String) {
        viewModelScope.launch {
            saveCurrentLanguageUseCase(languageCode)

            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.create(
                    Locale.forLanguageTag(currentLanguageCode.value.languageCode)
                )
            )
        }
    }
    fun loadCurrentLanguageCode() {
        viewModelScope.launch {
            loadCurrentLanguageUseCase()
        }
    }

    private fun initialDataStoreSetupEvent() {
        initLanguageDataStoreUseCase()
    }

    init {
        Log.d("LanguageScreenViewModel", "Initializing...")

        initialDataStoreSetupEvent()

    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                val getLanguagesUseCase: GetAvailableLanguagesUseCase = container.getAvailableLanguagesUseCase
                val getDefaultLanguageUseCase: GetDefaultLanguageUseCase = container.getDefaultLanguageUseCase
                val setDefaultLanguageUseCase: SetDefaultLanguageUseCase = container.setDefaultLanguageUseCase
                val initLanguageDataStoreUseCase: SetupLanguageUseCase = container.initLanguageDataStoreUseCase
                val initFlowLanguageUseCase: InitFlowLanguageUseCase = container.initFlowLanguageUseCase
                val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase = container.saveCurrentLanguageUseCase
                val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase = container.loadCurrentLanguageUseCase

                LanguageScreenViewModel(
                    // Languages
                    getAvailableLanguagesUseCase = getLanguagesUseCase,
                    getDefaultLanguageUseCase = getDefaultLanguageUseCase,
                    setDefaultLanguageUseCase = setDefaultLanguageUseCase,
                    initLanguageDataStoreUseCase = initLanguageDataStoreUseCase,
                    initFlowLanguageUseCase = initFlowLanguageUseCase,
                    saveCurrentLanguageUseCase = saveCurrentLanguageUseCase,
                    loadCurrentLanguageUseCase = loadCurrentLanguageUseCase
                )
            }
        }

    }

}
