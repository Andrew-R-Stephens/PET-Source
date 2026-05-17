package com.tritiumgaming.`data`.customdifficulty.source.local

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performInTransactionSuspending
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.tritiumgaming.shared.`data`.difficultysetting.mapper.DifficultySettingResources
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class CustomDifficultyDao_Impl(
  __db: RoomDatabase,
) : CustomDifficultyDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfCustomDifficultyEntity: EntityInsertAdapter<CustomDifficultyEntity>

  private val __difficultyTypeConverters: DifficultyTypeConverters = DifficultyTypeConverters()

  private val __deleteAdapterOfCustomDifficultyEntity:
      EntityDeleteOrUpdateAdapter<CustomDifficultyEntity>

  private val __updateAdapterOfCustomDifficultyEntity:
      EntityDeleteOrUpdateAdapter<CustomDifficultyEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfCustomDifficultyEntity = object : EntityInsertAdapter<CustomDifficultyEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `custom_difficulties` (`id`,`name`,`startingSanity`,`sanityPillRestoration`,`sanityDrainSpeed`,`sprinting`,`playerSpeed`,`flashlights`,`loseItemsAndConsumables`,`ghostSpeed`,`roamingFrequency`,`changingFavouriteRoom`,`activityLevel`,`eventFrequency`,`friendlyGhost`,`gracePeriod`,`huntDuration`,`killsExtendHunts`,`evidenceGiven`,`fingerprintChance`,`fingerprintDuration`,`setupTime`,`weather`,`doorsStartingOpen`,`numberOfHidingPlaces`,`sanityMonitor`,`activityMonitor`,`fuseBoxAtStartOfContract`,`fuseBoxVisibleOnMap`,`cursedPossessionsQuantity`,`cursedPossessions`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CustomDifficultyEntity) {
        statement.bindLong(1, entity.id.toLong())
        val _tmpName: String? = entity.name
        if (_tmpName == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpName)
        }
        statement.bindText(3, __StartingSanity_enumToString(entity.startingSanity))
        statement.bindText(4, __SanityPillRestoration_enumToString(entity.sanityPillRestoration))
        statement.bindText(5, __SanityDrainSpeed_enumToString(entity.sanityDrainSpeed))
        statement.bindText(6, __Sprinting_enumToString(entity.sprinting))
        statement.bindText(7, __PlayerSpeed_enumToString(entity.playerSpeed))
        statement.bindText(8, __Flashlights_enumToString(entity.flashlights))
        statement.bindText(9, __LoseItemsAndConsumables_enumToString(entity.loseItemsAndConsumables))
        statement.bindText(10, __GhostSpeed_enumToString(entity.ghostSpeed))
        statement.bindText(11, __RoamingFrequency_enumToString(entity.roamingFrequency))
        statement.bindText(12, __ChangingFavoriteRoom_enumToString(entity.changingFavouriteRoom))
        statement.bindText(13, __ActivityLevel_enumToString(entity.activityLevel))
        statement.bindText(14, __EventFrequency_enumToString(entity.eventFrequency))
        statement.bindText(15, __FriendlyGhost_enumToString(entity.friendlyGhost))
        statement.bindText(16, __GracePeriod_enumToString(entity.gracePeriod))
        statement.bindText(17, __HuntDuration_enumToString(entity.huntDuration))
        statement.bindText(18, __KillsExtendHunts_enumToString(entity.killsExtendHunts))
        statement.bindText(19, __EvidenceGiven_enumToString(entity.evidenceGiven))
        statement.bindText(20, __FingerprintChance_enumToString(entity.fingerprintChance))
        statement.bindText(21, __FingerprintDuration_enumToString(entity.fingerprintDuration))
        statement.bindText(22, __SetupTime_enumToString(entity.setupTime))
        statement.bindText(23, __Weather_enumToString(entity.weather))
        statement.bindText(24, __DoorsStartingOpen_enumToString(entity.doorsStartingOpen))
        statement.bindText(25, __NumberOfHidingPlaces_enumToString(entity.numberOfHidingPlaces))
        statement.bindText(26, __SanityMonitor_enumToString(entity.sanityMonitor))
        statement.bindText(27, __ActivityMonitor_enumToString(entity.activityMonitor))
        statement.bindText(28, __FuseBoxAtStartOfContract_enumToString(entity.fuseBoxAtStartOfContract))
        statement.bindText(29, __FuseBoxVisibleOnMap_enumToString(entity.fuseBoxVisibleOnMap))
        statement.bindText(30, __CursedPossessionsQuantity_enumToString(entity.cursedPossessionsQuantity))
        val _tmp: String? = __difficultyTypeConverters.fromCursedPossessionList(entity.cursedPossessions)
        if (_tmp == null) {
          statement.bindNull(31)
        } else {
          statement.bindText(31, _tmp)
        }
      }
    }
    this.__deleteAdapterOfCustomDifficultyEntity = object : EntityDeleteOrUpdateAdapter<CustomDifficultyEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `custom_difficulties` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CustomDifficultyEntity) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
    this.__updateAdapterOfCustomDifficultyEntity = object : EntityDeleteOrUpdateAdapter<CustomDifficultyEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `custom_difficulties` SET `id` = ?,`name` = ?,`startingSanity` = ?,`sanityPillRestoration` = ?,`sanityDrainSpeed` = ?,`sprinting` = ?,`playerSpeed` = ?,`flashlights` = ?,`loseItemsAndConsumables` = ?,`ghostSpeed` = ?,`roamingFrequency` = ?,`changingFavouriteRoom` = ?,`activityLevel` = ?,`eventFrequency` = ?,`friendlyGhost` = ?,`gracePeriod` = ?,`huntDuration` = ?,`killsExtendHunts` = ?,`evidenceGiven` = ?,`fingerprintChance` = ?,`fingerprintDuration` = ?,`setupTime` = ?,`weather` = ?,`doorsStartingOpen` = ?,`numberOfHidingPlaces` = ?,`sanityMonitor` = ?,`activityMonitor` = ?,`fuseBoxAtStartOfContract` = ?,`fuseBoxVisibleOnMap` = ?,`cursedPossessionsQuantity` = ?,`cursedPossessions` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CustomDifficultyEntity) {
        statement.bindLong(1, entity.id.toLong())
        val _tmpName: String? = entity.name
        if (_tmpName == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpName)
        }
        statement.bindText(3, __StartingSanity_enumToString(entity.startingSanity))
        statement.bindText(4, __SanityPillRestoration_enumToString(entity.sanityPillRestoration))
        statement.bindText(5, __SanityDrainSpeed_enumToString(entity.sanityDrainSpeed))
        statement.bindText(6, __Sprinting_enumToString(entity.sprinting))
        statement.bindText(7, __PlayerSpeed_enumToString(entity.playerSpeed))
        statement.bindText(8, __Flashlights_enumToString(entity.flashlights))
        statement.bindText(9, __LoseItemsAndConsumables_enumToString(entity.loseItemsAndConsumables))
        statement.bindText(10, __GhostSpeed_enumToString(entity.ghostSpeed))
        statement.bindText(11, __RoamingFrequency_enumToString(entity.roamingFrequency))
        statement.bindText(12, __ChangingFavoriteRoom_enumToString(entity.changingFavouriteRoom))
        statement.bindText(13, __ActivityLevel_enumToString(entity.activityLevel))
        statement.bindText(14, __EventFrequency_enumToString(entity.eventFrequency))
        statement.bindText(15, __FriendlyGhost_enumToString(entity.friendlyGhost))
        statement.bindText(16, __GracePeriod_enumToString(entity.gracePeriod))
        statement.bindText(17, __HuntDuration_enumToString(entity.huntDuration))
        statement.bindText(18, __KillsExtendHunts_enumToString(entity.killsExtendHunts))
        statement.bindText(19, __EvidenceGiven_enumToString(entity.evidenceGiven))
        statement.bindText(20, __FingerprintChance_enumToString(entity.fingerprintChance))
        statement.bindText(21, __FingerprintDuration_enumToString(entity.fingerprintDuration))
        statement.bindText(22, __SetupTime_enumToString(entity.setupTime))
        statement.bindText(23, __Weather_enumToString(entity.weather))
        statement.bindText(24, __DoorsStartingOpen_enumToString(entity.doorsStartingOpen))
        statement.bindText(25, __NumberOfHidingPlaces_enumToString(entity.numberOfHidingPlaces))
        statement.bindText(26, __SanityMonitor_enumToString(entity.sanityMonitor))
        statement.bindText(27, __ActivityMonitor_enumToString(entity.activityMonitor))
        statement.bindText(28, __FuseBoxAtStartOfContract_enumToString(entity.fuseBoxAtStartOfContract))
        statement.bindText(29, __FuseBoxVisibleOnMap_enumToString(entity.fuseBoxVisibleOnMap))
        statement.bindText(30, __CursedPossessionsQuantity_enumToString(entity.cursedPossessionsQuantity))
        val _tmp: String? = __difficultyTypeConverters.fromCursedPossessionList(entity.cursedPossessions)
        if (_tmp == null) {
          statement.bindNull(31)
        } else {
          statement.bindText(31, _tmp)
        }
        statement.bindLong(32, entity.id.toLong())
      }
    }
  }

  public override suspend fun insert(difficulty: CustomDifficultyEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCustomDifficultyEntity.insert(_connection, difficulty)
  }

  public override suspend fun insertAll(difficulties: List<CustomDifficultyEntity>): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCustomDifficultyEntity.insert(_connection, difficulties)
  }

  public override suspend fun delete(difficulty: CustomDifficultyEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfCustomDifficultyEntity.handle(_connection, difficulty)
  }

  public override suspend fun update(difficulty: CustomDifficultyEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfCustomDifficultyEntity.handle(_connection, difficulty)
  }

  public override suspend fun insertWithLimit(difficulty: CustomDifficultyEntity): Boolean = performInTransactionSuspending(__db) {
    super@CustomDifficultyDao_Impl.insertWithLimit(difficulty)
  }

  public override fun getAll(): Flow<List<CustomDifficultyEntity>> {
    val _sql: String = "SELECT * FROM custom_difficulties"
    return createFlow(__db, false, arrayOf("custom_difficulties")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfStartingSanity: Int = getColumnIndexOrThrow(_stmt, "startingSanity")
        val _columnIndexOfSanityPillRestoration: Int = getColumnIndexOrThrow(_stmt, "sanityPillRestoration")
        val _columnIndexOfSanityDrainSpeed: Int = getColumnIndexOrThrow(_stmt, "sanityDrainSpeed")
        val _columnIndexOfSprinting: Int = getColumnIndexOrThrow(_stmt, "sprinting")
        val _columnIndexOfPlayerSpeed: Int = getColumnIndexOrThrow(_stmt, "playerSpeed")
        val _columnIndexOfFlashlights: Int = getColumnIndexOrThrow(_stmt, "flashlights")
        val _columnIndexOfLoseItemsAndConsumables: Int = getColumnIndexOrThrow(_stmt, "loseItemsAndConsumables")
        val _columnIndexOfGhostSpeed: Int = getColumnIndexOrThrow(_stmt, "ghostSpeed")
        val _columnIndexOfRoamingFrequency: Int = getColumnIndexOrThrow(_stmt, "roamingFrequency")
        val _columnIndexOfChangingFavouriteRoom: Int = getColumnIndexOrThrow(_stmt, "changingFavouriteRoom")
        val _columnIndexOfActivityLevel: Int = getColumnIndexOrThrow(_stmt, "activityLevel")
        val _columnIndexOfEventFrequency: Int = getColumnIndexOrThrow(_stmt, "eventFrequency")
        val _columnIndexOfFriendlyGhost: Int = getColumnIndexOrThrow(_stmt, "friendlyGhost")
        val _columnIndexOfGracePeriod: Int = getColumnIndexOrThrow(_stmt, "gracePeriod")
        val _columnIndexOfHuntDuration: Int = getColumnIndexOrThrow(_stmt, "huntDuration")
        val _columnIndexOfKillsExtendHunts: Int = getColumnIndexOrThrow(_stmt, "killsExtendHunts")
        val _columnIndexOfEvidenceGiven: Int = getColumnIndexOrThrow(_stmt, "evidenceGiven")
        val _columnIndexOfFingerprintChance: Int = getColumnIndexOrThrow(_stmt, "fingerprintChance")
        val _columnIndexOfFingerprintDuration: Int = getColumnIndexOrThrow(_stmt, "fingerprintDuration")
        val _columnIndexOfSetupTime: Int = getColumnIndexOrThrow(_stmt, "setupTime")
        val _columnIndexOfWeather: Int = getColumnIndexOrThrow(_stmt, "weather")
        val _columnIndexOfDoorsStartingOpen: Int = getColumnIndexOrThrow(_stmt, "doorsStartingOpen")
        val _columnIndexOfNumberOfHidingPlaces: Int = getColumnIndexOrThrow(_stmt, "numberOfHidingPlaces")
        val _columnIndexOfSanityMonitor: Int = getColumnIndexOrThrow(_stmt, "sanityMonitor")
        val _columnIndexOfActivityMonitor: Int = getColumnIndexOrThrow(_stmt, "activityMonitor")
        val _columnIndexOfFuseBoxAtStartOfContract: Int = getColumnIndexOrThrow(_stmt, "fuseBoxAtStartOfContract")
        val _columnIndexOfFuseBoxVisibleOnMap: Int = getColumnIndexOrThrow(_stmt, "fuseBoxVisibleOnMap")
        val _columnIndexOfCursedPossessionsQuantity: Int = getColumnIndexOrThrow(_stmt, "cursedPossessionsQuantity")
        val _columnIndexOfCursedPossessions: Int = getColumnIndexOrThrow(_stmt, "cursedPossessions")
        val _result: MutableList<CustomDifficultyEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CustomDifficultyEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String?
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName)
          }
          val _tmpStartingSanity: DifficultySettingResources.StartingSanity
          _tmpStartingSanity = __StartingSanity_stringToEnum(_stmt.getText(_columnIndexOfStartingSanity))
          val _tmpSanityPillRestoration: DifficultySettingResources.SanityPillRestoration
          _tmpSanityPillRestoration = __SanityPillRestoration_stringToEnum(_stmt.getText(_columnIndexOfSanityPillRestoration))
          val _tmpSanityDrainSpeed: DifficultySettingResources.SanityDrainSpeed
          _tmpSanityDrainSpeed = __SanityDrainSpeed_stringToEnum(_stmt.getText(_columnIndexOfSanityDrainSpeed))
          val _tmpSprinting: DifficultySettingResources.Sprinting
          _tmpSprinting = __Sprinting_stringToEnum(_stmt.getText(_columnIndexOfSprinting))
          val _tmpPlayerSpeed: DifficultySettingResources.PlayerSpeed
          _tmpPlayerSpeed = __PlayerSpeed_stringToEnum(_stmt.getText(_columnIndexOfPlayerSpeed))
          val _tmpFlashlights: DifficultySettingResources.Flashlights
          _tmpFlashlights = __Flashlights_stringToEnum(_stmt.getText(_columnIndexOfFlashlights))
          val _tmpLoseItemsAndConsumables: DifficultySettingResources.LoseItemsAndConsumables
          _tmpLoseItemsAndConsumables = __LoseItemsAndConsumables_stringToEnum(_stmt.getText(_columnIndexOfLoseItemsAndConsumables))
          val _tmpGhostSpeed: DifficultySettingResources.GhostSpeed
          _tmpGhostSpeed = __GhostSpeed_stringToEnum(_stmt.getText(_columnIndexOfGhostSpeed))
          val _tmpRoamingFrequency: DifficultySettingResources.RoamingFrequency
          _tmpRoamingFrequency = __RoamingFrequency_stringToEnum(_stmt.getText(_columnIndexOfRoamingFrequency))
          val _tmpChangingFavouriteRoom: DifficultySettingResources.ChangingFavoriteRoom
          _tmpChangingFavouriteRoom = __ChangingFavoriteRoom_stringToEnum(_stmt.getText(_columnIndexOfChangingFavouriteRoom))
          val _tmpActivityLevel: DifficultySettingResources.ActivityLevel
          _tmpActivityLevel = __ActivityLevel_stringToEnum(_stmt.getText(_columnIndexOfActivityLevel))
          val _tmpEventFrequency: DifficultySettingResources.EventFrequency
          _tmpEventFrequency = __EventFrequency_stringToEnum(_stmt.getText(_columnIndexOfEventFrequency))
          val _tmpFriendlyGhost: DifficultySettingResources.FriendlyGhost
          _tmpFriendlyGhost = __FriendlyGhost_stringToEnum(_stmt.getText(_columnIndexOfFriendlyGhost))
          val _tmpGracePeriod: DifficultySettingResources.GracePeriod
          _tmpGracePeriod = __GracePeriod_stringToEnum(_stmt.getText(_columnIndexOfGracePeriod))
          val _tmpHuntDuration: DifficultySettingResources.HuntDuration
          _tmpHuntDuration = __HuntDuration_stringToEnum(_stmt.getText(_columnIndexOfHuntDuration))
          val _tmpKillsExtendHunts: DifficultySettingResources.KillsExtendHunts
          _tmpKillsExtendHunts = __KillsExtendHunts_stringToEnum(_stmt.getText(_columnIndexOfKillsExtendHunts))
          val _tmpEvidenceGiven: DifficultySettingResources.EvidenceGiven
          _tmpEvidenceGiven = __EvidenceGiven_stringToEnum(_stmt.getText(_columnIndexOfEvidenceGiven))
          val _tmpFingerprintChance: DifficultySettingResources.FingerprintChance
          _tmpFingerprintChance = __FingerprintChance_stringToEnum(_stmt.getText(_columnIndexOfFingerprintChance))
          val _tmpFingerprintDuration: DifficultySettingResources.FingerprintDuration
          _tmpFingerprintDuration = __FingerprintDuration_stringToEnum(_stmt.getText(_columnIndexOfFingerprintDuration))
          val _tmpSetupTime: DifficultySettingResources.SetupTime
          _tmpSetupTime = __SetupTime_stringToEnum(_stmt.getText(_columnIndexOfSetupTime))
          val _tmpWeather: DifficultySettingResources.Weather
          _tmpWeather = __Weather_stringToEnum(_stmt.getText(_columnIndexOfWeather))
          val _tmpDoorsStartingOpen: DifficultySettingResources.DoorsStartingOpen
          _tmpDoorsStartingOpen = __DoorsStartingOpen_stringToEnum(_stmt.getText(_columnIndexOfDoorsStartingOpen))
          val _tmpNumberOfHidingPlaces: DifficultySettingResources.NumberOfHidingPlaces
          _tmpNumberOfHidingPlaces = __NumberOfHidingPlaces_stringToEnum(_stmt.getText(_columnIndexOfNumberOfHidingPlaces))
          val _tmpSanityMonitor: DifficultySettingResources.SanityMonitor
          _tmpSanityMonitor = __SanityMonitor_stringToEnum(_stmt.getText(_columnIndexOfSanityMonitor))
          val _tmpActivityMonitor: DifficultySettingResources.ActivityMonitor
          _tmpActivityMonitor = __ActivityMonitor_stringToEnum(_stmt.getText(_columnIndexOfActivityMonitor))
          val _tmpFuseBoxAtStartOfContract: DifficultySettingResources.FuseBoxAtStartOfContract
          _tmpFuseBoxAtStartOfContract = __FuseBoxAtStartOfContract_stringToEnum(_stmt.getText(_columnIndexOfFuseBoxAtStartOfContract))
          val _tmpFuseBoxVisibleOnMap: DifficultySettingResources.FuseBoxVisibleOnMap
          _tmpFuseBoxVisibleOnMap = __FuseBoxVisibleOnMap_stringToEnum(_stmt.getText(_columnIndexOfFuseBoxVisibleOnMap))
          val _tmpCursedPossessionsQuantity: DifficultySettingResources.CursedPossessionsQuantity
          _tmpCursedPossessionsQuantity = __CursedPossessionsQuantity_stringToEnum(_stmt.getText(_columnIndexOfCursedPossessionsQuantity))
          val _tmpCursedPossessions: List<DifficultySettingResources.CursedPossession>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCursedPossessions)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCursedPossessions)
          }
          val _tmp_1: List<DifficultySettingResources.CursedPossession>? = __difficultyTypeConverters.toCursedPossessionList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<com.tritiumgaming.shared.`data`.difficultysetting.mapper.DifficultySettingResources.CursedPossession>', but it was NULL.")
          } else {
            _tmpCursedPossessions = _tmp_1
          }
          _item = CustomDifficultyEntity(_tmpId,_tmpName,_tmpStartingSanity,_tmpSanityPillRestoration,_tmpSanityDrainSpeed,_tmpSprinting,_tmpPlayerSpeed,_tmpFlashlights,_tmpLoseItemsAndConsumables,_tmpGhostSpeed,_tmpRoamingFrequency,_tmpChangingFavouriteRoom,_tmpActivityLevel,_tmpEventFrequency,_tmpFriendlyGhost,_tmpGracePeriod,_tmpHuntDuration,_tmpKillsExtendHunts,_tmpEvidenceGiven,_tmpFingerprintChance,_tmpFingerprintDuration,_tmpSetupTime,_tmpWeather,_tmpDoorsStartingOpen,_tmpNumberOfHidingPlaces,_tmpSanityMonitor,_tmpActivityMonitor,_tmpFuseBoxAtStartOfContract,_tmpFuseBoxVisibleOnMap,_tmpCursedPossessionsQuantity,_tmpCursedPossessions)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Int): CustomDifficultyEntity? {
    val _sql: String = "SELECT * FROM custom_difficulties WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfStartingSanity: Int = getColumnIndexOrThrow(_stmt, "startingSanity")
        val _columnIndexOfSanityPillRestoration: Int = getColumnIndexOrThrow(_stmt, "sanityPillRestoration")
        val _columnIndexOfSanityDrainSpeed: Int = getColumnIndexOrThrow(_stmt, "sanityDrainSpeed")
        val _columnIndexOfSprinting: Int = getColumnIndexOrThrow(_stmt, "sprinting")
        val _columnIndexOfPlayerSpeed: Int = getColumnIndexOrThrow(_stmt, "playerSpeed")
        val _columnIndexOfFlashlights: Int = getColumnIndexOrThrow(_stmt, "flashlights")
        val _columnIndexOfLoseItemsAndConsumables: Int = getColumnIndexOrThrow(_stmt, "loseItemsAndConsumables")
        val _columnIndexOfGhostSpeed: Int = getColumnIndexOrThrow(_stmt, "ghostSpeed")
        val _columnIndexOfRoamingFrequency: Int = getColumnIndexOrThrow(_stmt, "roamingFrequency")
        val _columnIndexOfChangingFavouriteRoom: Int = getColumnIndexOrThrow(_stmt, "changingFavouriteRoom")
        val _columnIndexOfActivityLevel: Int = getColumnIndexOrThrow(_stmt, "activityLevel")
        val _columnIndexOfEventFrequency: Int = getColumnIndexOrThrow(_stmt, "eventFrequency")
        val _columnIndexOfFriendlyGhost: Int = getColumnIndexOrThrow(_stmt, "friendlyGhost")
        val _columnIndexOfGracePeriod: Int = getColumnIndexOrThrow(_stmt, "gracePeriod")
        val _columnIndexOfHuntDuration: Int = getColumnIndexOrThrow(_stmt, "huntDuration")
        val _columnIndexOfKillsExtendHunts: Int = getColumnIndexOrThrow(_stmt, "killsExtendHunts")
        val _columnIndexOfEvidenceGiven: Int = getColumnIndexOrThrow(_stmt, "evidenceGiven")
        val _columnIndexOfFingerprintChance: Int = getColumnIndexOrThrow(_stmt, "fingerprintChance")
        val _columnIndexOfFingerprintDuration: Int = getColumnIndexOrThrow(_stmt, "fingerprintDuration")
        val _columnIndexOfSetupTime: Int = getColumnIndexOrThrow(_stmt, "setupTime")
        val _columnIndexOfWeather: Int = getColumnIndexOrThrow(_stmt, "weather")
        val _columnIndexOfDoorsStartingOpen: Int = getColumnIndexOrThrow(_stmt, "doorsStartingOpen")
        val _columnIndexOfNumberOfHidingPlaces: Int = getColumnIndexOrThrow(_stmt, "numberOfHidingPlaces")
        val _columnIndexOfSanityMonitor: Int = getColumnIndexOrThrow(_stmt, "sanityMonitor")
        val _columnIndexOfActivityMonitor: Int = getColumnIndexOrThrow(_stmt, "activityMonitor")
        val _columnIndexOfFuseBoxAtStartOfContract: Int = getColumnIndexOrThrow(_stmt, "fuseBoxAtStartOfContract")
        val _columnIndexOfFuseBoxVisibleOnMap: Int = getColumnIndexOrThrow(_stmt, "fuseBoxVisibleOnMap")
        val _columnIndexOfCursedPossessionsQuantity: Int = getColumnIndexOrThrow(_stmt, "cursedPossessionsQuantity")
        val _columnIndexOfCursedPossessions: Int = getColumnIndexOrThrow(_stmt, "cursedPossessions")
        val _result: CustomDifficultyEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpName: String?
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName)
          }
          val _tmpStartingSanity: DifficultySettingResources.StartingSanity
          _tmpStartingSanity = __StartingSanity_stringToEnum(_stmt.getText(_columnIndexOfStartingSanity))
          val _tmpSanityPillRestoration: DifficultySettingResources.SanityPillRestoration
          _tmpSanityPillRestoration = __SanityPillRestoration_stringToEnum(_stmt.getText(_columnIndexOfSanityPillRestoration))
          val _tmpSanityDrainSpeed: DifficultySettingResources.SanityDrainSpeed
          _tmpSanityDrainSpeed = __SanityDrainSpeed_stringToEnum(_stmt.getText(_columnIndexOfSanityDrainSpeed))
          val _tmpSprinting: DifficultySettingResources.Sprinting
          _tmpSprinting = __Sprinting_stringToEnum(_stmt.getText(_columnIndexOfSprinting))
          val _tmpPlayerSpeed: DifficultySettingResources.PlayerSpeed
          _tmpPlayerSpeed = __PlayerSpeed_stringToEnum(_stmt.getText(_columnIndexOfPlayerSpeed))
          val _tmpFlashlights: DifficultySettingResources.Flashlights
          _tmpFlashlights = __Flashlights_stringToEnum(_stmt.getText(_columnIndexOfFlashlights))
          val _tmpLoseItemsAndConsumables: DifficultySettingResources.LoseItemsAndConsumables
          _tmpLoseItemsAndConsumables = __LoseItemsAndConsumables_stringToEnum(_stmt.getText(_columnIndexOfLoseItemsAndConsumables))
          val _tmpGhostSpeed: DifficultySettingResources.GhostSpeed
          _tmpGhostSpeed = __GhostSpeed_stringToEnum(_stmt.getText(_columnIndexOfGhostSpeed))
          val _tmpRoamingFrequency: DifficultySettingResources.RoamingFrequency
          _tmpRoamingFrequency = __RoamingFrequency_stringToEnum(_stmt.getText(_columnIndexOfRoamingFrequency))
          val _tmpChangingFavouriteRoom: DifficultySettingResources.ChangingFavoriteRoom
          _tmpChangingFavouriteRoom = __ChangingFavoriteRoom_stringToEnum(_stmt.getText(_columnIndexOfChangingFavouriteRoom))
          val _tmpActivityLevel: DifficultySettingResources.ActivityLevel
          _tmpActivityLevel = __ActivityLevel_stringToEnum(_stmt.getText(_columnIndexOfActivityLevel))
          val _tmpEventFrequency: DifficultySettingResources.EventFrequency
          _tmpEventFrequency = __EventFrequency_stringToEnum(_stmt.getText(_columnIndexOfEventFrequency))
          val _tmpFriendlyGhost: DifficultySettingResources.FriendlyGhost
          _tmpFriendlyGhost = __FriendlyGhost_stringToEnum(_stmt.getText(_columnIndexOfFriendlyGhost))
          val _tmpGracePeriod: DifficultySettingResources.GracePeriod
          _tmpGracePeriod = __GracePeriod_stringToEnum(_stmt.getText(_columnIndexOfGracePeriod))
          val _tmpHuntDuration: DifficultySettingResources.HuntDuration
          _tmpHuntDuration = __HuntDuration_stringToEnum(_stmt.getText(_columnIndexOfHuntDuration))
          val _tmpKillsExtendHunts: DifficultySettingResources.KillsExtendHunts
          _tmpKillsExtendHunts = __KillsExtendHunts_stringToEnum(_stmt.getText(_columnIndexOfKillsExtendHunts))
          val _tmpEvidenceGiven: DifficultySettingResources.EvidenceGiven
          _tmpEvidenceGiven = __EvidenceGiven_stringToEnum(_stmt.getText(_columnIndexOfEvidenceGiven))
          val _tmpFingerprintChance: DifficultySettingResources.FingerprintChance
          _tmpFingerprintChance = __FingerprintChance_stringToEnum(_stmt.getText(_columnIndexOfFingerprintChance))
          val _tmpFingerprintDuration: DifficultySettingResources.FingerprintDuration
          _tmpFingerprintDuration = __FingerprintDuration_stringToEnum(_stmt.getText(_columnIndexOfFingerprintDuration))
          val _tmpSetupTime: DifficultySettingResources.SetupTime
          _tmpSetupTime = __SetupTime_stringToEnum(_stmt.getText(_columnIndexOfSetupTime))
          val _tmpWeather: DifficultySettingResources.Weather
          _tmpWeather = __Weather_stringToEnum(_stmt.getText(_columnIndexOfWeather))
          val _tmpDoorsStartingOpen: DifficultySettingResources.DoorsStartingOpen
          _tmpDoorsStartingOpen = __DoorsStartingOpen_stringToEnum(_stmt.getText(_columnIndexOfDoorsStartingOpen))
          val _tmpNumberOfHidingPlaces: DifficultySettingResources.NumberOfHidingPlaces
          _tmpNumberOfHidingPlaces = __NumberOfHidingPlaces_stringToEnum(_stmt.getText(_columnIndexOfNumberOfHidingPlaces))
          val _tmpSanityMonitor: DifficultySettingResources.SanityMonitor
          _tmpSanityMonitor = __SanityMonitor_stringToEnum(_stmt.getText(_columnIndexOfSanityMonitor))
          val _tmpActivityMonitor: DifficultySettingResources.ActivityMonitor
          _tmpActivityMonitor = __ActivityMonitor_stringToEnum(_stmt.getText(_columnIndexOfActivityMonitor))
          val _tmpFuseBoxAtStartOfContract: DifficultySettingResources.FuseBoxAtStartOfContract
          _tmpFuseBoxAtStartOfContract = __FuseBoxAtStartOfContract_stringToEnum(_stmt.getText(_columnIndexOfFuseBoxAtStartOfContract))
          val _tmpFuseBoxVisibleOnMap: DifficultySettingResources.FuseBoxVisibleOnMap
          _tmpFuseBoxVisibleOnMap = __FuseBoxVisibleOnMap_stringToEnum(_stmt.getText(_columnIndexOfFuseBoxVisibleOnMap))
          val _tmpCursedPossessionsQuantity: DifficultySettingResources.CursedPossessionsQuantity
          _tmpCursedPossessionsQuantity = __CursedPossessionsQuantity_stringToEnum(_stmt.getText(_columnIndexOfCursedPossessionsQuantity))
          val _tmpCursedPossessions: List<DifficultySettingResources.CursedPossession>
          val _tmp: String?
          if (_stmt.isNull(_columnIndexOfCursedPossessions)) {
            _tmp = null
          } else {
            _tmp = _stmt.getText(_columnIndexOfCursedPossessions)
          }
          val _tmp_1: List<DifficultySettingResources.CursedPossession>? = __difficultyTypeConverters.toCursedPossessionList(_tmp)
          if (_tmp_1 == null) {
            error("Expected NON-NULL 'kotlin.collections.List<com.tritiumgaming.shared.`data`.difficultysetting.mapper.DifficultySettingResources.CursedPossession>', but it was NULL.")
          } else {
            _tmpCursedPossessions = _tmp_1
          }
          _result = CustomDifficultyEntity(_tmpId,_tmpName,_tmpStartingSanity,_tmpSanityPillRestoration,_tmpSanityDrainSpeed,_tmpSprinting,_tmpPlayerSpeed,_tmpFlashlights,_tmpLoseItemsAndConsumables,_tmpGhostSpeed,_tmpRoamingFrequency,_tmpChangingFavouriteRoom,_tmpActivityLevel,_tmpEventFrequency,_tmpFriendlyGhost,_tmpGracePeriod,_tmpHuntDuration,_tmpKillsExtendHunts,_tmpEvidenceGiven,_tmpFingerprintChance,_tmpFingerprintDuration,_tmpSetupTime,_tmpWeather,_tmpDoorsStartingOpen,_tmpNumberOfHidingPlaces,_tmpSanityMonitor,_tmpActivityMonitor,_tmpFuseBoxAtStartOfContract,_tmpFuseBoxVisibleOnMap,_tmpCursedPossessionsQuantity,_tmpCursedPossessions)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM custom_difficulties"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  private fun __StartingSanity_enumToString(_value: DifficultySettingResources.StartingSanity): String = when (_value) {
    DifficultySettingResources.StartingSanity.SANITY_0 -> "SANITY_0"
    DifficultySettingResources.StartingSanity.SANITY_25 -> "SANITY_25"
    DifficultySettingResources.StartingSanity.SANITY_50 -> "SANITY_50"
    DifficultySettingResources.StartingSanity.SANITY_75 -> "SANITY_75"
    DifficultySettingResources.StartingSanity.SANITY_100 -> "SANITY_100"
  }

  private fun __SanityPillRestoration_enumToString(_value: DifficultySettingResources.SanityPillRestoration): String = when (_value) {
    DifficultySettingResources.SanityPillRestoration.RESTORE_0 -> "RESTORE_0"
    DifficultySettingResources.SanityPillRestoration.RESTORE_5 -> "RESTORE_5"
    DifficultySettingResources.SanityPillRestoration.RESTORE_10 -> "RESTORE_10"
    DifficultySettingResources.SanityPillRestoration.RESTORE_20 -> "RESTORE_20"
    DifficultySettingResources.SanityPillRestoration.RESTORE_25 -> "RESTORE_25"
    DifficultySettingResources.SanityPillRestoration.RESTORE_30 -> "RESTORE_30"
    DifficultySettingResources.SanityPillRestoration.RESTORE_35 -> "RESTORE_35"
    DifficultySettingResources.SanityPillRestoration.RESTORE_40 -> "RESTORE_40"
    DifficultySettingResources.SanityPillRestoration.RESTORE_45 -> "RESTORE_45"
    DifficultySettingResources.SanityPillRestoration.RESTORE_50 -> "RESTORE_50"
    DifficultySettingResources.SanityPillRestoration.RESTORE_75 -> "RESTORE_75"
    DifficultySettingResources.SanityPillRestoration.RESTORE_100 -> "RESTORE_100"
  }

  private fun __SanityDrainSpeed_enumToString(_value: DifficultySettingResources.SanityDrainSpeed): String = when (_value) {
    DifficultySettingResources.SanityDrainSpeed.SPEED_0 -> "SPEED_0"
    DifficultySettingResources.SanityDrainSpeed.SPEED_50 -> "SPEED_50"
    DifficultySettingResources.SanityDrainSpeed.SPEED_100 -> "SPEED_100"
    DifficultySettingResources.SanityDrainSpeed.SPEED_150 -> "SPEED_150"
    DifficultySettingResources.SanityDrainSpeed.SPEED_200 -> "SPEED_200"
  }

  private fun __Sprinting_enumToString(_value: DifficultySettingResources.Sprinting): String = when (_value) {
    DifficultySettingResources.Sprinting.OFF -> "OFF"
    DifficultySettingResources.Sprinting.ON -> "ON"
    DifficultySettingResources.Sprinting.SPRINT_INFINITE -> "SPRINT_INFINITE"
  }

  private fun __PlayerSpeed_enumToString(_value: DifficultySettingResources.PlayerSpeed): String = when (_value) {
    DifficultySettingResources.PlayerSpeed.SPEED_50 -> "SPEED_50"
    DifficultySettingResources.PlayerSpeed.SPEED_75 -> "SPEED_75"
    DifficultySettingResources.PlayerSpeed.SPEED_100 -> "SPEED_100"
    DifficultySettingResources.PlayerSpeed.SPEED_125 -> "SPEED_125"
    DifficultySettingResources.PlayerSpeed.SPEED_150 -> "SPEED_150"
  }

  private fun __Flashlights_enumToString(_value: DifficultySettingResources.Flashlights): String = when (_value) {
    DifficultySettingResources.Flashlights.OFF -> "OFF"
    DifficultySettingResources.Flashlights.ON -> "ON"
  }

  private fun __LoseItemsAndConsumables_enumToString(_value: DifficultySettingResources.LoseItemsAndConsumables): String = when (_value) {
    DifficultySettingResources.LoseItemsAndConsumables.OFF -> "OFF"
    DifficultySettingResources.LoseItemsAndConsumables.ON -> "ON"
  }

  private fun __GhostSpeed_enumToString(_value: DifficultySettingResources.GhostSpeed): String = when (_value) {
    DifficultySettingResources.GhostSpeed.SPEED_50 -> "SPEED_50"
    DifficultySettingResources.GhostSpeed.SPEED_75 -> "SPEED_75"
    DifficultySettingResources.GhostSpeed.SPEED_100 -> "SPEED_100"
    DifficultySettingResources.GhostSpeed.SPEED_125 -> "SPEED_125"
    DifficultySettingResources.GhostSpeed.SPEED_150 -> "SPEED_150"
  }

  private fun __RoamingFrequency_enumToString(_value: DifficultySettingResources.RoamingFrequency): String = when (_value) {
    DifficultySettingResources.RoamingFrequency.LOW -> "LOW"
    DifficultySettingResources.RoamingFrequency.MEDIUM -> "MEDIUM"
    DifficultySettingResources.RoamingFrequency.HIGH -> "HIGH"
  }

  private fun __ChangingFavoriteRoom_enumToString(_value: DifficultySettingResources.ChangingFavoriteRoom): String = when (_value) {
    DifficultySettingResources.ChangingFavoriteRoom.NONE -> "NONE"
    DifficultySettingResources.ChangingFavoriteRoom.LOW -> "LOW"
    DifficultySettingResources.ChangingFavoriteRoom.MEDIUM -> "MEDIUM"
    DifficultySettingResources.ChangingFavoriteRoom.HIGH -> "HIGH"
    DifficultySettingResources.ChangingFavoriteRoom.VERY_HIGH -> "VERY_HIGH"
  }

  private fun __ActivityLevel_enumToString(_value: DifficultySettingResources.ActivityLevel): String = when (_value) {
    DifficultySettingResources.ActivityLevel.LOW -> "LOW"
    DifficultySettingResources.ActivityLevel.MEDIUM -> "MEDIUM"
    DifficultySettingResources.ActivityLevel.HIGH -> "HIGH"
  }

  private fun __EventFrequency_enumToString(_value: DifficultySettingResources.EventFrequency): String = when (_value) {
    DifficultySettingResources.EventFrequency.LOW -> "LOW"
    DifficultySettingResources.EventFrequency.MEDIUM -> "MEDIUM"
    DifficultySettingResources.EventFrequency.HIGH -> "HIGH"
  }

  private fun __FriendlyGhost_enumToString(_value: DifficultySettingResources.FriendlyGhost): String = when (_value) {
    DifficultySettingResources.FriendlyGhost.OFF -> "OFF"
    DifficultySettingResources.FriendlyGhost.ON -> "ON"
  }

  private fun __GracePeriod_enumToString(_value: DifficultySettingResources.GracePeriod): String = when (_value) {
    DifficultySettingResources.GracePeriod.PERIOD_0 -> "PERIOD_0"
    DifficultySettingResources.GracePeriod.PERIOD_1 -> "PERIOD_1"
    DifficultySettingResources.GracePeriod.PERIOD_2 -> "PERIOD_2"
    DifficultySettingResources.GracePeriod.PERIOD_3 -> "PERIOD_3"
    DifficultySettingResources.GracePeriod.PERIOD_4 -> "PERIOD_4"
    DifficultySettingResources.GracePeriod.PERIOD_5 -> "PERIOD_5"
  }

  private fun __HuntDuration_enumToString(_value: DifficultySettingResources.HuntDuration): String = when (_value) {
    DifficultySettingResources.HuntDuration.LOW -> "LOW"
    DifficultySettingResources.HuntDuration.MEDIUM -> "MEDIUM"
    DifficultySettingResources.HuntDuration.HIGH -> "HIGH"
  }

  private fun __KillsExtendHunts_enumToString(_value: DifficultySettingResources.KillsExtendHunts): String = when (_value) {
    DifficultySettingResources.KillsExtendHunts.OFF -> "OFF"
    DifficultySettingResources.KillsExtendHunts.LOW -> "LOW"
    DifficultySettingResources.KillsExtendHunts.MEDIUM -> "MEDIUM"
    DifficultySettingResources.KillsExtendHunts.HIGH -> "HIGH"
  }

  private fun __EvidenceGiven_enumToString(_value: DifficultySettingResources.EvidenceGiven): String = when (_value) {
    DifficultySettingResources.EvidenceGiven.COUNT_0 -> "COUNT_0"
    DifficultySettingResources.EvidenceGiven.COUNT_1 -> "COUNT_1"
    DifficultySettingResources.EvidenceGiven.COUNT_2 -> "COUNT_2"
    DifficultySettingResources.EvidenceGiven.COUNT_3 -> "COUNT_3"
  }

  private fun __FingerprintChance_enumToString(_value: DifficultySettingResources.FingerprintChance): String = when (_value) {
    DifficultySettingResources.FingerprintChance.CHANCE_0 -> "CHANCE_0"
    DifficultySettingResources.FingerprintChance.CHANCE_25 -> "CHANCE_25"
    DifficultySettingResources.FingerprintChance.CHANCE_50 -> "CHANCE_50"
    DifficultySettingResources.FingerprintChance.CHANCE_75 -> "CHANCE_75"
    DifficultySettingResources.FingerprintChance.CHANCE_100 -> "CHANCE_100"
  }

  private fun __FingerprintDuration_enumToString(_value: DifficultySettingResources.FingerprintDuration): String = when (_value) {
    DifficultySettingResources.FingerprintDuration.DURATION_NEVER -> "DURATION_NEVER"
    DifficultySettingResources.FingerprintDuration.DURATION_15 -> "DURATION_15"
    DifficultySettingResources.FingerprintDuration.DURATION_30 -> "DURATION_30"
    DifficultySettingResources.FingerprintDuration.DURATION_60 -> "DURATION_60"
    DifficultySettingResources.FingerprintDuration.DURATION_90 -> "DURATION_90"
    DifficultySettingResources.FingerprintDuration.DURATION_120 -> "DURATION_120"
    DifficultySettingResources.FingerprintDuration.DURATION_180 -> "DURATION_180"
    DifficultySettingResources.FingerprintDuration.DURATION_INFINITE -> "DURATION_INFINITE"
  }

  private fun __SetupTime_enumToString(_value: DifficultySettingResources.SetupTime): String = when (_value) {
    DifficultySettingResources.SetupTime.TIME_0 -> "TIME_0"
    DifficultySettingResources.SetupTime.TIME_30 -> "TIME_30"
    DifficultySettingResources.SetupTime.TIME_60 -> "TIME_60"
    DifficultySettingResources.SetupTime.TIME_120 -> "TIME_120"
    DifficultySettingResources.SetupTime.TIME_180 -> "TIME_180"
    DifficultySettingResources.SetupTime.TIME_240 -> "TIME_240"
    DifficultySettingResources.SetupTime.TIME_300 -> "TIME_300"
  }

  private fun __Weather_enumToString(_value: DifficultySettingResources.Weather): String = when (_value) {
    DifficultySettingResources.Weather.RANDOM -> "RANDOM"
    DifficultySettingResources.Weather.LIGHT_RAIN -> "LIGHT_RAIN"
    DifficultySettingResources.Weather.HEAVY_RAIN -> "HEAVY_RAIN"
    DifficultySettingResources.Weather.SNOW -> "SNOW"
    DifficultySettingResources.Weather.WINDY -> "WINDY"
    DifficultySettingResources.Weather.CLEAR -> "CLEAR"
    DifficultySettingResources.Weather.FOG -> "FOG"
    DifficultySettingResources.Weather.SUNRISE -> "SUNRISE"
    DifficultySettingResources.Weather.BLOOD_MOON -> "BLOOD_MOON"
  }

  private fun __DoorsStartingOpen_enumToString(_value: DifficultySettingResources.DoorsStartingOpen): String = when (_value) {
    DifficultySettingResources.DoorsStartingOpen.NONE -> "NONE"
    DifficultySettingResources.DoorsStartingOpen.LOW -> "LOW"
    DifficultySettingResources.DoorsStartingOpen.MEDIUM -> "MEDIUM"
    DifficultySettingResources.DoorsStartingOpen.HIGH -> "HIGH"
  }

  private fun __NumberOfHidingPlaces_enumToString(_value: DifficultySettingResources.NumberOfHidingPlaces): String = when (_value) {
    DifficultySettingResources.NumberOfHidingPlaces.NONE -> "NONE"
    DifficultySettingResources.NumberOfHidingPlaces.LOW -> "LOW"
    DifficultySettingResources.NumberOfHidingPlaces.MEDIUM -> "MEDIUM"
    DifficultySettingResources.NumberOfHidingPlaces.HIGH -> "HIGH"
    DifficultySettingResources.NumberOfHidingPlaces.VERY_HIGH -> "VERY_HIGH"
  }

  private fun __SanityMonitor_enumToString(_value: DifficultySettingResources.SanityMonitor): String = when (_value) {
    DifficultySettingResources.SanityMonitor.OFF -> "OFF"
    DifficultySettingResources.SanityMonitor.ON -> "ON"
  }

  private fun __ActivityMonitor_enumToString(_value: DifficultySettingResources.ActivityMonitor): String = when (_value) {
    DifficultySettingResources.ActivityMonitor.OFF -> "OFF"
    DifficultySettingResources.ActivityMonitor.ON -> "ON"
  }

  private fun __FuseBoxAtStartOfContract_enumToString(_value: DifficultySettingResources.FuseBoxAtStartOfContract): String = when (_value) {
    DifficultySettingResources.FuseBoxAtStartOfContract.BROKEN -> "BROKEN"
    DifficultySettingResources.FuseBoxAtStartOfContract.OFF -> "OFF"
    DifficultySettingResources.FuseBoxAtStartOfContract.ON -> "ON"
  }

  private fun __FuseBoxVisibleOnMap_enumToString(_value: DifficultySettingResources.FuseBoxVisibleOnMap): String = when (_value) {
    DifficultySettingResources.FuseBoxVisibleOnMap.OFF -> "OFF"
    DifficultySettingResources.FuseBoxVisibleOnMap.ON -> "ON"
  }

  private fun __CursedPossessionsQuantity_enumToString(_value: DifficultySettingResources.CursedPossessionsQuantity): String = when (_value) {
    DifficultySettingResources.CursedPossessionsQuantity.NONE -> "NONE"
    DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_1 -> "QUANTITY_1"
    DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_2 -> "QUANTITY_2"
    DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_3 -> "QUANTITY_3"
    DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_4 -> "QUANTITY_4"
    DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_5 -> "QUANTITY_5"
    DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_6 -> "QUANTITY_6"
    DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_7 -> "QUANTITY_7"
  }

  private fun __StartingSanity_stringToEnum(_value: String): DifficultySettingResources.StartingSanity = when (_value) {
    "SANITY_0" -> DifficultySettingResources.StartingSanity.SANITY_0
    "SANITY_25" -> DifficultySettingResources.StartingSanity.SANITY_25
    "SANITY_50" -> DifficultySettingResources.StartingSanity.SANITY_50
    "SANITY_75" -> DifficultySettingResources.StartingSanity.SANITY_75
    "SANITY_100" -> DifficultySettingResources.StartingSanity.SANITY_100
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __SanityPillRestoration_stringToEnum(_value: String): DifficultySettingResources.SanityPillRestoration = when (_value) {
    "RESTORE_0" -> DifficultySettingResources.SanityPillRestoration.RESTORE_0
    "RESTORE_5" -> DifficultySettingResources.SanityPillRestoration.RESTORE_5
    "RESTORE_10" -> DifficultySettingResources.SanityPillRestoration.RESTORE_10
    "RESTORE_20" -> DifficultySettingResources.SanityPillRestoration.RESTORE_20
    "RESTORE_25" -> DifficultySettingResources.SanityPillRestoration.RESTORE_25
    "RESTORE_30" -> DifficultySettingResources.SanityPillRestoration.RESTORE_30
    "RESTORE_35" -> DifficultySettingResources.SanityPillRestoration.RESTORE_35
    "RESTORE_40" -> DifficultySettingResources.SanityPillRestoration.RESTORE_40
    "RESTORE_45" -> DifficultySettingResources.SanityPillRestoration.RESTORE_45
    "RESTORE_50" -> DifficultySettingResources.SanityPillRestoration.RESTORE_50
    "RESTORE_75" -> DifficultySettingResources.SanityPillRestoration.RESTORE_75
    "RESTORE_100" -> DifficultySettingResources.SanityPillRestoration.RESTORE_100
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __SanityDrainSpeed_stringToEnum(_value: String): DifficultySettingResources.SanityDrainSpeed = when (_value) {
    "SPEED_0" -> DifficultySettingResources.SanityDrainSpeed.SPEED_0
    "SPEED_50" -> DifficultySettingResources.SanityDrainSpeed.SPEED_50
    "SPEED_100" -> DifficultySettingResources.SanityDrainSpeed.SPEED_100
    "SPEED_150" -> DifficultySettingResources.SanityDrainSpeed.SPEED_150
    "SPEED_200" -> DifficultySettingResources.SanityDrainSpeed.SPEED_200
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __Sprinting_stringToEnum(_value: String): DifficultySettingResources.Sprinting = when (_value) {
    "OFF" -> DifficultySettingResources.Sprinting.OFF
    "ON" -> DifficultySettingResources.Sprinting.ON
    "SPRINT_INFINITE" -> DifficultySettingResources.Sprinting.SPRINT_INFINITE
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __PlayerSpeed_stringToEnum(_value: String): DifficultySettingResources.PlayerSpeed = when (_value) {
    "SPEED_50" -> DifficultySettingResources.PlayerSpeed.SPEED_50
    "SPEED_75" -> DifficultySettingResources.PlayerSpeed.SPEED_75
    "SPEED_100" -> DifficultySettingResources.PlayerSpeed.SPEED_100
    "SPEED_125" -> DifficultySettingResources.PlayerSpeed.SPEED_125
    "SPEED_150" -> DifficultySettingResources.PlayerSpeed.SPEED_150
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __Flashlights_stringToEnum(_value: String): DifficultySettingResources.Flashlights = when (_value) {
    "OFF" -> DifficultySettingResources.Flashlights.OFF
    "ON" -> DifficultySettingResources.Flashlights.ON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __LoseItemsAndConsumables_stringToEnum(_value: String): DifficultySettingResources.LoseItemsAndConsumables = when (_value) {
    "OFF" -> DifficultySettingResources.LoseItemsAndConsumables.OFF
    "ON" -> DifficultySettingResources.LoseItemsAndConsumables.ON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __GhostSpeed_stringToEnum(_value: String): DifficultySettingResources.GhostSpeed = when (_value) {
    "SPEED_50" -> DifficultySettingResources.GhostSpeed.SPEED_50
    "SPEED_75" -> DifficultySettingResources.GhostSpeed.SPEED_75
    "SPEED_100" -> DifficultySettingResources.GhostSpeed.SPEED_100
    "SPEED_125" -> DifficultySettingResources.GhostSpeed.SPEED_125
    "SPEED_150" -> DifficultySettingResources.GhostSpeed.SPEED_150
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __RoamingFrequency_stringToEnum(_value: String): DifficultySettingResources.RoamingFrequency = when (_value) {
    "LOW" -> DifficultySettingResources.RoamingFrequency.LOW
    "MEDIUM" -> DifficultySettingResources.RoamingFrequency.MEDIUM
    "HIGH" -> DifficultySettingResources.RoamingFrequency.HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __ChangingFavoriteRoom_stringToEnum(_value: String): DifficultySettingResources.ChangingFavoriteRoom = when (_value) {
    "NONE" -> DifficultySettingResources.ChangingFavoriteRoom.NONE
    "LOW" -> DifficultySettingResources.ChangingFavoriteRoom.LOW
    "MEDIUM" -> DifficultySettingResources.ChangingFavoriteRoom.MEDIUM
    "HIGH" -> DifficultySettingResources.ChangingFavoriteRoom.HIGH
    "VERY_HIGH" -> DifficultySettingResources.ChangingFavoriteRoom.VERY_HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __ActivityLevel_stringToEnum(_value: String): DifficultySettingResources.ActivityLevel = when (_value) {
    "LOW" -> DifficultySettingResources.ActivityLevel.LOW
    "MEDIUM" -> DifficultySettingResources.ActivityLevel.MEDIUM
    "HIGH" -> DifficultySettingResources.ActivityLevel.HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __EventFrequency_stringToEnum(_value: String): DifficultySettingResources.EventFrequency = when (_value) {
    "LOW" -> DifficultySettingResources.EventFrequency.LOW
    "MEDIUM" -> DifficultySettingResources.EventFrequency.MEDIUM
    "HIGH" -> DifficultySettingResources.EventFrequency.HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __FriendlyGhost_stringToEnum(_value: String): DifficultySettingResources.FriendlyGhost = when (_value) {
    "OFF" -> DifficultySettingResources.FriendlyGhost.OFF
    "ON" -> DifficultySettingResources.FriendlyGhost.ON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __GracePeriod_stringToEnum(_value: String): DifficultySettingResources.GracePeriod = when (_value) {
    "PERIOD_0" -> DifficultySettingResources.GracePeriod.PERIOD_0
    "PERIOD_1" -> DifficultySettingResources.GracePeriod.PERIOD_1
    "PERIOD_2" -> DifficultySettingResources.GracePeriod.PERIOD_2
    "PERIOD_3" -> DifficultySettingResources.GracePeriod.PERIOD_3
    "PERIOD_4" -> DifficultySettingResources.GracePeriod.PERIOD_4
    "PERIOD_5" -> DifficultySettingResources.GracePeriod.PERIOD_5
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __HuntDuration_stringToEnum(_value: String): DifficultySettingResources.HuntDuration = when (_value) {
    "LOW" -> DifficultySettingResources.HuntDuration.LOW
    "MEDIUM" -> DifficultySettingResources.HuntDuration.MEDIUM
    "HIGH" -> DifficultySettingResources.HuntDuration.HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __KillsExtendHunts_stringToEnum(_value: String): DifficultySettingResources.KillsExtendHunts = when (_value) {
    "OFF" -> DifficultySettingResources.KillsExtendHunts.OFF
    "LOW" -> DifficultySettingResources.KillsExtendHunts.LOW
    "MEDIUM" -> DifficultySettingResources.KillsExtendHunts.MEDIUM
    "HIGH" -> DifficultySettingResources.KillsExtendHunts.HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __EvidenceGiven_stringToEnum(_value: String): DifficultySettingResources.EvidenceGiven = when (_value) {
    "COUNT_0" -> DifficultySettingResources.EvidenceGiven.COUNT_0
    "COUNT_1" -> DifficultySettingResources.EvidenceGiven.COUNT_1
    "COUNT_2" -> DifficultySettingResources.EvidenceGiven.COUNT_2
    "COUNT_3" -> DifficultySettingResources.EvidenceGiven.COUNT_3
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __FingerprintChance_stringToEnum(_value: String): DifficultySettingResources.FingerprintChance = when (_value) {
    "CHANCE_0" -> DifficultySettingResources.FingerprintChance.CHANCE_0
    "CHANCE_25" -> DifficultySettingResources.FingerprintChance.CHANCE_25
    "CHANCE_50" -> DifficultySettingResources.FingerprintChance.CHANCE_50
    "CHANCE_75" -> DifficultySettingResources.FingerprintChance.CHANCE_75
    "CHANCE_100" -> DifficultySettingResources.FingerprintChance.CHANCE_100
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __FingerprintDuration_stringToEnum(_value: String): DifficultySettingResources.FingerprintDuration = when (_value) {
    "DURATION_NEVER" -> DifficultySettingResources.FingerprintDuration.DURATION_NEVER
    "DURATION_15" -> DifficultySettingResources.FingerprintDuration.DURATION_15
    "DURATION_30" -> DifficultySettingResources.FingerprintDuration.DURATION_30
    "DURATION_60" -> DifficultySettingResources.FingerprintDuration.DURATION_60
    "DURATION_90" -> DifficultySettingResources.FingerprintDuration.DURATION_90
    "DURATION_120" -> DifficultySettingResources.FingerprintDuration.DURATION_120
    "DURATION_180" -> DifficultySettingResources.FingerprintDuration.DURATION_180
    "DURATION_INFINITE" -> DifficultySettingResources.FingerprintDuration.DURATION_INFINITE
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __SetupTime_stringToEnum(_value: String): DifficultySettingResources.SetupTime = when (_value) {
    "TIME_0" -> DifficultySettingResources.SetupTime.TIME_0
    "TIME_30" -> DifficultySettingResources.SetupTime.TIME_30
    "TIME_60" -> DifficultySettingResources.SetupTime.TIME_60
    "TIME_120" -> DifficultySettingResources.SetupTime.TIME_120
    "TIME_180" -> DifficultySettingResources.SetupTime.TIME_180
    "TIME_240" -> DifficultySettingResources.SetupTime.TIME_240
    "TIME_300" -> DifficultySettingResources.SetupTime.TIME_300
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __Weather_stringToEnum(_value: String): DifficultySettingResources.Weather = when (_value) {
    "RANDOM" -> DifficultySettingResources.Weather.RANDOM
    "LIGHT_RAIN" -> DifficultySettingResources.Weather.LIGHT_RAIN
    "HEAVY_RAIN" -> DifficultySettingResources.Weather.HEAVY_RAIN
    "SNOW" -> DifficultySettingResources.Weather.SNOW
    "WINDY" -> DifficultySettingResources.Weather.WINDY
    "CLEAR" -> DifficultySettingResources.Weather.CLEAR
    "FOG" -> DifficultySettingResources.Weather.FOG
    "SUNRISE" -> DifficultySettingResources.Weather.SUNRISE
    "BLOOD_MOON" -> DifficultySettingResources.Weather.BLOOD_MOON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __DoorsStartingOpen_stringToEnum(_value: String): DifficultySettingResources.DoorsStartingOpen = when (_value) {
    "NONE" -> DifficultySettingResources.DoorsStartingOpen.NONE
    "LOW" -> DifficultySettingResources.DoorsStartingOpen.LOW
    "MEDIUM" -> DifficultySettingResources.DoorsStartingOpen.MEDIUM
    "HIGH" -> DifficultySettingResources.DoorsStartingOpen.HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __NumberOfHidingPlaces_stringToEnum(_value: String): DifficultySettingResources.NumberOfHidingPlaces = when (_value) {
    "NONE" -> DifficultySettingResources.NumberOfHidingPlaces.NONE
    "LOW" -> DifficultySettingResources.NumberOfHidingPlaces.LOW
    "MEDIUM" -> DifficultySettingResources.NumberOfHidingPlaces.MEDIUM
    "HIGH" -> DifficultySettingResources.NumberOfHidingPlaces.HIGH
    "VERY_HIGH" -> DifficultySettingResources.NumberOfHidingPlaces.VERY_HIGH
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __SanityMonitor_stringToEnum(_value: String): DifficultySettingResources.SanityMonitor = when (_value) {
    "OFF" -> DifficultySettingResources.SanityMonitor.OFF
    "ON" -> DifficultySettingResources.SanityMonitor.ON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __ActivityMonitor_stringToEnum(_value: String): DifficultySettingResources.ActivityMonitor = when (_value) {
    "OFF" -> DifficultySettingResources.ActivityMonitor.OFF
    "ON" -> DifficultySettingResources.ActivityMonitor.ON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __FuseBoxAtStartOfContract_stringToEnum(_value: String): DifficultySettingResources.FuseBoxAtStartOfContract = when (_value) {
    "BROKEN" -> DifficultySettingResources.FuseBoxAtStartOfContract.BROKEN
    "OFF" -> DifficultySettingResources.FuseBoxAtStartOfContract.OFF
    "ON" -> DifficultySettingResources.FuseBoxAtStartOfContract.ON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __FuseBoxVisibleOnMap_stringToEnum(_value: String): DifficultySettingResources.FuseBoxVisibleOnMap = when (_value) {
    "OFF" -> DifficultySettingResources.FuseBoxVisibleOnMap.OFF
    "ON" -> DifficultySettingResources.FuseBoxVisibleOnMap.ON
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  private fun __CursedPossessionsQuantity_stringToEnum(_value: String): DifficultySettingResources.CursedPossessionsQuantity = when (_value) {
    "NONE" -> DifficultySettingResources.CursedPossessionsQuantity.NONE
    "QUANTITY_1" -> DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_1
    "QUANTITY_2" -> DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_2
    "QUANTITY_3" -> DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_3
    "QUANTITY_4" -> DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_4
    "QUANTITY_5" -> DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_5
    "QUANTITY_6" -> DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_6
    "QUANTITY_7" -> DifficultySettingResources.CursedPossessionsQuantity.QUANTITY_7
    else -> throw IllegalArgumentException("Can't convert value to enum, unknown value: " + _value)
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
