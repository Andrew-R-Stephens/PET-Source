package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldRoom

data class ComplexRoomDto(
    val id: Int,
    val name: String,
    val roomArea: ComplexRoomAreaDto
) {

    override fun toString(): String {
        return "\n\t\t[Room ID: $id] [Room Name: $name] [Room points: $roomArea]"
    }

    @Synchronized
    fun print() {
        Log.d("Maps", "$id $name")
        roomArea.print()
    }
}

fun List<ComplexRoomDto>.toDomain() = map { roomDto -> roomDto.toDomain() }

fun ComplexRoomDto.toDomain() = ComplexWorldRoom(
    id = id,
    name = name,
    roomArea = roomArea.toDomain()
)