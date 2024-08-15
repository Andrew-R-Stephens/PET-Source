package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.datastore.globalPreferences.repository

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
import com.TritiumGaming.phasmophobiaevidencepicker.R

class LanguageRepository(context: Context) {

    data class LanguageObject(val name: String, val abbreviation: String)

    val languageList: ArrayList<LanguageObject> = ArrayList()

    init {
        val languageNames = ArrayList(
            listOf(*context.resources.getStringArray(R.array.language_names)))
        val languageCodes = ArrayList(
            listOf(*context.resources.getStringArray(R.array.language_codes)))

        if(languageNames.size == languageCodes.size) {
            languageNames.forEachIndexed { index: Int, name: String ->
                languageList.add(LanguageObject(name, languageCodes[index]))
            }
        }
    }

}