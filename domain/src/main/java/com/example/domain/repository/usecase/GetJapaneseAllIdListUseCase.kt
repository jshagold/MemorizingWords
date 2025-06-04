package com.example.domain.repository.usecase

import com.example.domain.repository.repository.WordConfig
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJapaneseAllIdListUseCase @Inject constructor(
    private val wordConfig: WordConfig
) {
    operator fun invoke(): Flow<List<Long>> {
        return wordConfig.getAllJapaneseIdList()
    }
}