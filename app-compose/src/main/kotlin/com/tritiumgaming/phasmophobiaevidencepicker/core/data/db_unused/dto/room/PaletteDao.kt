package com.tritiumgaming.phasmophobiaevidencepicker.core.data.db_unused.dto.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PaletteDao {
    @Query("SELECT * FROM palette")
    fun getAll(): List<Palette>

    @Query("SELECT * FROM palette WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Palette>

    @Query("SELECT * FROM palette WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Palette

    @Insert
    fun insertAll(vararg users: Palette)

    @Delete
    fun delete(user: Palette)
}