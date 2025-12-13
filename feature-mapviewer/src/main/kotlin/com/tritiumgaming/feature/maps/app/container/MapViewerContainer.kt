package com.tritiumgaming.feature.maps.app.container

import android.content.Context
import com.tritiumgaming.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.data.map.complex.source.service.ComplexMapLocalService
import com.tritiumgaming.data.map.simple.repository.SimpleMapRepositoryImpl
import com.tritiumgaming.data.map.simple.source.SimpleMapDataSource
import com.tritiumgaming.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.shared.data.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.shared.data.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapIndexUseCase

class MapViewerContainer(
    applicationContext: Context
) {

    // Simple Map
    private val simpleMapRepository: SimpleMapRepository by lazy {
        val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(
            applicationContext = applicationContext
        )
        SimpleMapRepositoryImpl(
            localSource = simpleMapLocalDataSource
        )
    }
    internal val fetchSimpleMapsUseCase = FetchSimpleMapsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val fetchMapThumbnailsUseCase = FetchMapThumbnailsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val incrementMapIndexUseCase = IncrementMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val decrementMapIndexUseCase = DecrementMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val incrementMapFloorIndexUseCase = IncrementMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val decrementMapFloorIndexUseCase = DecrementMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val getSimpleMapIdUseCase = GetSimpleMapIdUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val getSimpleMapNameUseCase = GetSimpleMapNameUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val getSimpleMapSizeUseCase = GetSimpleMapSizeUseCase(
        simpleMapRepository = simpleMapRepository
    )

    // Complex Map
    private val complexMapRepository: ComplexMapRepository by lazy {
        val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
            applicationContext = applicationContext,
            service = ComplexMapLocalService()
        )
        ComplexMapRepositoryImpl(
            localSource = complexMapLocalDataSource
        )
    }
    internal val fetchComplexMapsUseCase = FetchComplexMapsUseCase(
        complexMapRepository = complexMapRepository
    )

}
