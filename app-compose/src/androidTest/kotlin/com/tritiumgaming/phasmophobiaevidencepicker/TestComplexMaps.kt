package com.tritiumgaming.phasmophobiaevidencepicker

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.service.ComplexMapLocalService
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

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

    @Test
    fun fetchComplexMaps(): Unit =
        runTest(testDispatcher) {
            val result = complexMapsRepository.fetchMaps()

            assertTrue("${ result.exceptionOrNull()?.message }", result.isSuccess)

            println(result.getOrNull())
            result.getOrNull()?.print()
        }
}