package com.example.domain.repository.repository

import com.example.core.result.Result
import kotlinx.coroutines.flow.Flow
import java.io.File

interface TTSFile {
    fun downloadVoiceFile(baseDir: File, textList: List<String>): Flow<Result<Pair<Int, String>>>
}