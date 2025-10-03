package com.tritiumgaming.data.ghostname.repository

import com.tritiumgaming.data.ghostname.dto.toDomain
import com.tritiumgaming.data.ghostname.source.GhostNameDataSource
import com.tritiumgaming.shared.operation.domain.ghostname.model.GhostName
import com.tritiumgaming.shared.operation.domain.ghostname.repository.GhostNameRepository

class GhostNameRepositoryImpl(
    private val localSource: GhostNameDataSource
): GhostNameRepository {

    var names: List<GhostName> = emptyList()

    override fun getNames(): Result<List<GhostName>> {

        if(names.isEmpty()) {
            val result = localSource.get().map { dto -> dto.toDomain() }

            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get names", it))
            }

            result.getOrNull()?.let { names = it }
        }

        return localSource.get().map { dto -> dto.toDomain() }
    }

    override fun getNamesBy(
        namePriority: GhostName.NamePriority?,
        gender: GhostName.Gender?
    ): Result<List<GhostName>> {

        val result = getNames()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get names", it))
        }

        var filteredResult = result.getOrThrow()

        filteredResult =
            if(namePriority != null) {
                filteredResult.let { names ->
                    return Result.success(names.filter {
                        it.priority == namePriority }) }
            } else { filteredResult }

        filteredResult =
            if(gender != null) {
                filteredResult.let { names ->
                    return Result.success(names.filter {
                        it.gender == gender }) }
            } else { filteredResult }

        return Result.success(filteredResult)
    }

}

