package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.graphics.PointF
import android.util.Log

class PoiDto {
    var id: Int = -1
    var name: String = ""
    var type: PoiTypeDto = PoiTypeDto.entries[0]
    var point: PointF? = null

    constructor() {
        id = -1
        name = ""
    }

    constructor(tempPoiData: PoiDto?) {
        if (tempPoiData == null) {
            return
        }

        id = tempPoiData.id
        name = tempPoiData.name
        type = tempPoiData.type
        point = tempPoiData.point
    }

    constructor(id: Int, name: String, type: Int, x: Float, y: Float) {
        this.id = id
        this.name = name
        setType(type)
        point = PointF(x, y)
    }


    private fun setType(type: Int) {
        this.type = PoiTypeDto.entries[type]
    }

    fun hasName(): Boolean {
        return name.isNotEmpty()
    }

    fun hasId(): Boolean {
        return this.id >= -1
    }

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room Name: $type] [Room points: $point]"
    }

    val isReady: Boolean
        get() = point != null

    fun print() {
        Log.d("Maps", "$id $name $type $point")
    }
}
