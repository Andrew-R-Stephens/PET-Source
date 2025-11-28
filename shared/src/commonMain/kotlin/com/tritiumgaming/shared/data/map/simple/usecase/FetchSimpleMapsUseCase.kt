package com.tritiumgaming.shared.data.map.simple.usecase

class FetchSimpleMapsUseCase(
    private val simpleMapRepository: com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.map.simple.model.SimpleWorldMap>> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get simple maps", it)) }

        return result
    }
}