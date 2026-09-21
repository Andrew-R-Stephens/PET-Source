package com.tritiumgaming.shared.data.market.typography.source

interface LocalTypographyDataSource<T> {

    fun getTypographies(): Result<T>

}