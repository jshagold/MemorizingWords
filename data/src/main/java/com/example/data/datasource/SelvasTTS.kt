package com.example.data.datasource

import com.example.core.result.Result
import kotlinx.coroutines.flow.Flow
import java.io.File

interface SelvasTTS {

    fun downloadVoiceFile(baseDir: File, textList: List<String>): Flow<Result<Pair<Int, String>>>

}