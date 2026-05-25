package com.tritiumgaming.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.tritiumgaming.`data`.customdifficulty.source.local.CustomDifficultyDao
import com.tritiumgaming.`data`.customdifficulty.source.local.CustomDifficultyDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class LocalDatabase_Impl : LocalDatabase() {
  private val _customDifficultyDao: Lazy<CustomDifficultyDao> = lazy {
    CustomDifficultyDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "bb4ee674b589075eddc88cbd5a543229", "303d6e24ae0f7e01351e1beee841fccb") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `CustomDifficulty` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `startingSanity` TEXT NOT NULL, `sanityPillRestoration` TEXT NOT NULL, `sanityDrainSpeed` TEXT NOT NULL, `sprinting` TEXT NOT NULL, `playerSpeed` TEXT NOT NULL, `flashlights` TEXT NOT NULL, `loseItemsAndConsumables` TEXT NOT NULL, `ghostSpeed` TEXT NOT NULL, `roamingFrequency` TEXT NOT NULL, `changingFavouriteRoom` TEXT NOT NULL, `activityLevel` TEXT NOT NULL, `eventFrequency` TEXT NOT NULL, `friendlyGhost` TEXT NOT NULL, `gracePeriod` TEXT NOT NULL, `huntDuration` TEXT NOT NULL, `killsExtendHunts` TEXT NOT NULL, `evidenceGiven` TEXT NOT NULL, `fingerprintChance` TEXT NOT NULL, `fingerprintDuration` TEXT NOT NULL, `setupTime` TEXT NOT NULL, `weather` TEXT NOT NULL, `doorsStartingOpen` TEXT NOT NULL, `numberOfHidingPlaces` TEXT NOT NULL, `sanityMonitor` TEXT NOT NULL, `activityMonitor` TEXT NOT NULL, `fuseBoxAtStartOfContract` TEXT NOT NULL, `fuseBoxVisibleOnMap` TEXT NOT NULL, `cursedPossessionsQuantity` TEXT NOT NULL, `cursedPossessions` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'bb4ee674b589075eddc88cbd5a543229')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `CustomDifficulty`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsCustomDifficulty: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCustomDifficulty.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("name", TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("startingSanity", TableInfo.Column("startingSanity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("sanityPillRestoration", TableInfo.Column("sanityPillRestoration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("sanityDrainSpeed", TableInfo.Column("sanityDrainSpeed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("sprinting", TableInfo.Column("sprinting", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("playerSpeed", TableInfo.Column("playerSpeed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("flashlights", TableInfo.Column("flashlights", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("loseItemsAndConsumables", TableInfo.Column("loseItemsAndConsumables", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("ghostSpeed", TableInfo.Column("ghostSpeed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("roamingFrequency", TableInfo.Column("roamingFrequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("changingFavouriteRoom", TableInfo.Column("changingFavouriteRoom", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("activityLevel", TableInfo.Column("activityLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("eventFrequency", TableInfo.Column("eventFrequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("friendlyGhost", TableInfo.Column("friendlyGhost", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("gracePeriod", TableInfo.Column("gracePeriod", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("huntDuration", TableInfo.Column("huntDuration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("killsExtendHunts", TableInfo.Column("killsExtendHunts", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("evidenceGiven", TableInfo.Column("evidenceGiven", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("fingerprintChance", TableInfo.Column("fingerprintChance", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("fingerprintDuration", TableInfo.Column("fingerprintDuration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("setupTime", TableInfo.Column("setupTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("weather", TableInfo.Column("weather", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("doorsStartingOpen", TableInfo.Column("doorsStartingOpen", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("numberOfHidingPlaces", TableInfo.Column("numberOfHidingPlaces", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("sanityMonitor", TableInfo.Column("sanityMonitor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("activityMonitor", TableInfo.Column("activityMonitor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("fuseBoxAtStartOfContract", TableInfo.Column("fuseBoxAtStartOfContract", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("fuseBoxVisibleOnMap", TableInfo.Column("fuseBoxVisibleOnMap", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("cursedPossessionsQuantity", TableInfo.Column("cursedPossessionsQuantity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulty.put("cursedPossessions", TableInfo.Column("cursedPossessions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCustomDifficulty: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCustomDifficulty: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCustomDifficulty: TableInfo = TableInfo("CustomDifficulty", _columnsCustomDifficulty, _foreignKeysCustomDifficulty, _indicesCustomDifficulty)
        val _existingCustomDifficulty: TableInfo = read(connection, "CustomDifficulty")
        if (!_infoCustomDifficulty.equals(_existingCustomDifficulty)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |CustomDifficulty(com.tritiumgaming.data.customdifficulty.source.local.CustomDifficultyEntity).
              | Expected:
              |""".trimMargin() + _infoCustomDifficulty + """
              |
              | Found:
              |""".trimMargin() + _existingCustomDifficulty)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "CustomDifficulty")
  }

  public override fun clearAllTables() {
    super.performClear(false, "CustomDifficulty")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(CustomDifficultyDao::class, CustomDifficultyDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun customDifficultyDao(): CustomDifficultyDao = _customDifficultyDao.value
}
