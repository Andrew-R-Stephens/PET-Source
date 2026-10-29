package com.tritiumgaming.feature.maps.app.container

import android.content.Context
import com.tritiumgaming.data.map.combined.dto.repository.MapRepositoryImpl
import com.tritiumgaming.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.data.map.complex.source.service.ComplexMapLocalService
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapId
import com.tritiumgaming.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.data.map.source.simple.local.SimpleMapDataSource
import com.tritiumgaming.data.map.usecase.GetMapsFlowUseCase
import com.tritiumgaming.feature.maps.R

class MapViewerContainer(
    applicationContext: Context
) {

    private val mapRepository: MapRepositoryImpl by lazy {
        fun getString(id: Int): String = applicationContext.getString(id)
        val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(
            mapIdentifiers = SimpleMapLocalDataSource.MapIdentifiers(
                pairs = mapOf(
                    MapId.SUNNY_MEADOWS to getString(R.string.map_id_meadows),
                    MapId.SUNNY_MEADOWS_RESTRICTED to getString(R.string.map_id_meadows_r),
                    MapId.POINT_HOPE to getString(R.string.map_id_pointhope),
                    MapId.CAMP_MAPLE to getString(R.string.map_id_campmaple),
                    MapId.CAMP_WOODWIND to getString(R.string.map_id_campwoodwind),
                    MapId.DINER to getString(R.string.map_id_diner),
                    MapId.BLEASDALE_FARMHOUSE to getString(R.string.map_id_bleasdale),
                    MapId.GRAFTON_FARMHOUSE to getString(R.string.map_id_grafton),
                    MapId.BROWNSTONE_HIGHSCHOOL to getString(R.string.map_id_brownstone),
                    MapId.EDGEFIELD to getString(R.string.map_id_edgefield),
                    MapId.TANGLEWOOD to getString(R.string.map_id_tanglewood),
                    MapId.WILLOW to getString(R.string.map_id_willow),
                    MapId.RIDGEVIEW to getString(R.string.map_id_ridgeview),
                    MapId.PRISON to getString(R.string.map_id_prison)
                )
            )
        )
        val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
            applicationContext = applicationContext,
            service = ComplexMapLocalService()
        )

        MapRepositoryImpl(
            localSource = simpleMapLocalDataSource,
            assetSource = complexMapLocalDataSource
        )
    }

    internal val fetchMapsFlowUseCase = GetMapsFlowUseCase(
        repository = mapRepository
    )

}
