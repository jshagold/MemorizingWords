package com.example.domain.repository.usecase

import com.example.domain.repository.model.JapaneseWord
import com.example.domain.repository.repository.StudyJapanese
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetJapaneseWordUseCase @Inject constructor(
    private val studyJapanese: StudyJapanese
) {
    operator fun invoke(wordId: Long): Flow<JapaneseWord> {
        return studyJapanese.getWordById(wordId)
    }
}