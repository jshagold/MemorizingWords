package com.example.domain.repository.usecase

import com.example.domain.repository.model.JapaneseWord
import com.example.domain.repository.repository.StudyJapanese
import javax.inject.Inject

class GetJapaneseWordUseCase @Inject constructor(
    private val studyJapanese: StudyJapanese
) {
    suspend operator fun invoke(wordId: Long): JapaneseWord {
        return studyJapanese.getWordById(wordId)
    }
}