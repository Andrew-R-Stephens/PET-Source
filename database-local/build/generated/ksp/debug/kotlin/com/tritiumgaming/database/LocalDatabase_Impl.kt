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
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2, "909c43df4b053e7c9e041e4efed610c6", "07d7b49d4eece4b625a39fbca8a78fd2") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `custom_difficulties` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `startingSanity` TEXT NOT NULL, `sanityPillRestoration` TEXT NOT NULL, `sanityDrainSpeed` TEXT NOT NULL, `sprinting` TEXT NOT NULL, `playerSpeed` TEXT NOT NULL, `flashlights` TEXT NOT NULL, `loseItemsAndConsumables` TEXT NOT NULL, `ghostSpeed` TEXT NOT NULL, `roamingFrequency` TEXT NOT NULL, `changingFavouriteRoom` TEXT NOT NULL, `activityLevel` TEXT NOT NULL, `eventFrequency` TEXT NOT NULL, `friendlyGhost` TEXT NOT NULL, `gracePeriod` TEXT NOT NULL, `huntDuration` TEXT NOT NULL, `killsExtendHunts` TEXT NOT NULL, `evidenceGiven` TEXT NOT NULL, `fingerprintChance` TEXT NOT NULL, `fingerprintDuration` TEXT NOT NULL, `setupTime` TEXT NOT NULL, `weather` TEXT NOT NULL, `doorsStartingOpen` TEXT NOT NULL, `numberOfHidingPlaces` TEXT NOT NULL, `sanityMonitor` TEXT NOT NULL, `activityMonitor` TEXT NOT NULL, `fuseBoxAtStartOfContract` TEXT NOT NULL, `fuseBoxVisibleOnMap` TEXT NOT NULL, `cursedPossessionsQuantity` TEXT NOT NULL, `cursedPossessions` TEXT NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '909c43df4b053e7c9e041e4efed610c6')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `custom_difficulties`")
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
        val _columnsCustomDifficulties: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCustomDifficulties.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("name", TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("startingSanity", TableInfo.Column("startingSanity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("sanityPillRestoration", TableInfo.Column("sanityPillRestoration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("sanityDrainSpeed", TableInfo.Column("sanityDrainSpeed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("sprinting", TableInfo.Column("sprinting", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("playerSpeed", TableInfo.Column("playerSpeed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("flashlights", TableInfo.Column("flashlights", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("loseItemsAndConsumables", TableInfo.Column("loseItemsAndConsumables", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("ghostSpeed", TableInfo.Column("ghostSpeed", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("roamingFrequency", TableInfo.Column("roamingFrequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("changingFavouriteRoom", TableInfo.Column("changingFavouriteRoom", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("activityLevel", TableInfo.Column("activityLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("eventFrequency", TableInfo.Column("eventFrequency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("friendlyGhost", TableInfo.Column("friendlyGhost", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("gracePeriod", TableInfo.Column("gracePeriod", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("huntDuration", TableInfo.Column("huntDuration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("killsExtendHunts", TableInfo.Column("killsExtendHunts", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("evidenceGiven", TableInfo.Column("evidenceGiven", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("fingerprintChance", TableInfo.Column("fingerprintChance", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("fingerprintDuration", TableInfo.Column("fingerprintDuration", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("setupTime", TableInfo.Column("setupTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("weather", TableInfo.Column("weather", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("doorsStartingOpen", TableInfo.Column("doorsStartingOpen", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("numberOfHidingPlaces", TableInfo.Column("numberOfHidingPlaces", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("sanityMonitor", TableInfo.Column("sanityMonitor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("activityMonitor", TableInfo.Column("activityMonitor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("fuseBoxAtStartOfContract", TableInfo.Column("fuseBoxAtStartOfContract", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("fuseBoxVisibleOnMap", TableInfo.Column("fuseBoxVisibleOnMap", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("cursedPossessionsQuantity", TableInfo.Column("cursedPossessionsQuantity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCustomDifficulties.put("cursedPossessions", TableInfo.Column("cursedPossessions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCustomDifficulties: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCustomDifficulties: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCustomDifficulties: TableInfo = TableInfo("custom_difficulties", _columnsCustomDifficulties, _foreignKeysCustomDifficulties, _indicesCustomDifficulties)
        val _existingCustomDifficulties: TableInfo = read(connection, "custom_difficulties")
        if (!_infoCustomDifficulties.equals(_existingCustomDifficulties)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |custom_difficulties(com.tritiumgaming.data.customdifficulty.source.local.CustomDifficultyEntity).
              | Expected:
              |""".trimMargin() + _infoCustomDifficulties + """
              |
              | Found:
              |""".trimMargin() + _existingCustomDifficulties)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "custom_difficulties")
  }

  public override fun clearAllTables() {
    super.performClear(false, "custom_difficulties")
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
