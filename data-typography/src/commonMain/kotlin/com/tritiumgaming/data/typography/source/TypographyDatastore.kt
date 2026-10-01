package com.tritiumgaming.data.typography.source

interface LocalTypographyDataSource<T> {

    fun getTypographies(): Result<T>

}