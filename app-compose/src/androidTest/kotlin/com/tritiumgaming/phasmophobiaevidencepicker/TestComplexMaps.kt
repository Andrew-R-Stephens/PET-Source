package com.tritiumgaming.phasmophobiaevidencepicker

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.service.ComplexMapLocalService
import com.tritiumgaming.shared.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before

class TestComplexMaps {

    private lateinit var mockContext: Context

    private lateinit var complexMapService: ComplexMapLocalService
    private lateinit var complexMapLocalDataSource: ComplexMapLocalDataSource
    private lateinit var complexMapsRepository: ComplexMapRepositoryImpl
    private lateinit var fetchComplexMapsUseCase: FetchComplexMapsUseCase

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setUp() {
        mockContext = InstrumentationRegistry.getInstrumentation().targetContext

        complexMapService = ComplexMapLocalService()
        complexMapLocalDataSource = ComplexMapLocalDataSource(
            applicationContext = mockContext,
            service = complexMapService
        )
        complexMapsRepository = ComplexMapRepositoryImpl(
            localSource = complexMapLocalDataSource
        )
        fetchComplexMapsUseCase = FetchComplexMapsUseCase(complexMapsRepository)
    }

}