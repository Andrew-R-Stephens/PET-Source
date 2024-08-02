package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.demo_datastore

/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.demo_datastore.repository.TasksRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.demo_datastore.repository.UserPreferencesRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.demo_datastore.viewmodel.TasksViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.demo_datastore.viewmodel.TasksViewModelFactory

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

class TasksActivity : AppCompatActivity() {

    private lateinit var viewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            TasksViewModelFactory(
                TasksRepository,
                UserPreferencesRepository(dataStore, this)
            )
        )[TasksViewModel::class.java]

    }

}