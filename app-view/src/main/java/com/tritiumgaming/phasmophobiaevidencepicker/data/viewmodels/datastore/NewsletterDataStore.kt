package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.datastore.NewsletterDataStore.PreferenceKeys.KEY_INBOX_GENERAL
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.datastore.NewsletterDataStore.PreferenceKeys.KEY_INBOX_PET
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.datastore.NewsletterDataStore.PreferenceKeys.KEY_INBOX_PHASMOPHOBIA

const val FILE_NAME = "Newsletter_Preferences"
private val Context.dataStore by preferencesDataStore(FILE_NAME)

class NewsletterDataStore(context: Context): PETDataStore(context.dataStore) {

    object PreferenceKeys {
        var KEY_INBOX_GENERAL: Preferences.Key<Long>? = null
        var KEY_INBOX_PET: Preferences.Key<Long>? = null
        var KEY_INBOX_PHASMOPHOBIA: Preferences.Key<Long>? = null
    }

    init {
        KEY_INBOX_GENERAL = longPreferencesKey(
            context.resources.getString(R.string.preference_newsletter_lastreaddate_general))
        KEY_INBOX_PET = longPreferencesKey(
            context.resources.getString(R.string.preference_newsletter_lastreaddate_pet))
        KEY_INBOX_PHASMOPHOBIA = longPreferencesKey(
            context.resources.getString(R.string.preference_newsletter_lastreaddate_phas))
    }

    /*
    public readInboxGeneral(): Long {  }
    */
}