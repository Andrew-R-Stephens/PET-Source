package com.tritiumgaming.data.customdifficulty.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession

class DifficultyTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromCursedPossessionList(value: List<CursedPossession>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCursedPossessionList(value: String?): List<CursedPossession>? {
        val listType = object : TypeToken<List<CursedPossession>>() {}.type
        return gson.fromJson(value, listType)
    }
}
