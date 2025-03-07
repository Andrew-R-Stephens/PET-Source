package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers

import android.util.Log
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewsletterHandler(
    private val repository: NewsletterRepository
) {

    fun initialSetupEvent() {
        liveData {
            emit(repository.fetchInitialPreferences())
        }
    }

    suspend fun initFlow() {

        repository.flow.collect { preferences ->
            _lastReadDateGeneral.update { preferences.lastReadDateGeneral }
            _lastReadDatePET.update { preferences.lastReadDatePET }
            _lastReadDatePhasmophobia.update { preferences.lastReadDatePhasmophobia }

            Log.d("GlobalPreferences", "Collecting from flow: \n" +
                    "\tlastReadDateGeneral -> ${lastReadDateGeneral.value}\n" +
                    "\tlastReadDatePET -> ${lastReadDatePET.value}\n" +
                    "\tlastReadDatePhasmophobia -> ${lastReadDatePhasmophobia.value}\n"
            )
        }

    }

    private var inboxes = repository.inboxes
    fun getInbox(inboxType: NewsletterInboxType.InboxType): NewsletterInbox {
        return inboxes.value[inboxType] ?:
            NewsletterInbox(NewsletterInboxType.NewsletterInboxDefaultType.Default.asInboxType())
    }

    private var _lastReadDateGeneral = MutableStateFlow(0L)
    val lastReadDateGeneral = _lastReadDateGeneral.asStateFlow()
    suspend fun setLastReadDateGeneral(date: Long) {
        _lastReadDateGeneral.update { date }
        repository.setLastReadDateGeneral(date)
    }

    private var _lastReadDatePET = MutableStateFlow(0L)
    val lastReadDatePET = _lastReadDatePET.asStateFlow()
    suspend fun setLastReadDatePET(date: Long) {
        _lastReadDatePET.update { date }
        repository.setLastReadDatePET(date)
    }

    private var _lastReadDatePhasmophobia = MutableStateFlow(0L)
    val lastReadDatePhasmophobia = _lastReadDatePhasmophobia.asStateFlow()
    suspend fun setLastReadDatePhasmophobia(date: Long) {
        _lastReadDatePhasmophobia.update { date }
        repository.setLastReadDatePhasmophobia(date)
    }

}
