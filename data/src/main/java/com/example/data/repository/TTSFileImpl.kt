package com.example.data.repository

import com.example.core.result.Result
import com.example.data.datasource.SelvasTTS
import com.example.domain.repository.repository.TTSFile
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class TTSFileImpl @Inject constructor(
    private val selvasTTS: SelvasTTS
) : TTSFile {
    override fun downloadVoiceFile(baseDir: File, textList: List<String>): Flow<Result<Pair<Int, String>>> {
        return selvasTTS.downloadVoiceFile(baseDir, textList)
    }
}