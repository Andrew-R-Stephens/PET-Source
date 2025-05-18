package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source

interface TypographyDataSource<T> {

    suspend fun fetchAll(): T

}