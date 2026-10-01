package com.tritiumgaming.data.palette.source

interface LocalPaletteDataSource<T> {

    fun getPalettes(): Result<T>

}