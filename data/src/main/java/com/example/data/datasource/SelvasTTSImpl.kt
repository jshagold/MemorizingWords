package com.example.data.datasource

import com.example.core.result.Result
import com.example.data.tts.config.SelvasConfig
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import selvytts.Pttsnet
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class SelvasTTSImpl @Inject constructor() : SelvasTTS {

//    private val pttsNet = Pttsnet()

    override fun downloadVoiceFile(baseDir: File, textList: List<String>): Flow<Result<Pair<Int, String>>> = flow {
        val files = baseDir.listFiles()
        files?.forEach { it.delete() }

        textList.forEachIndexed { index, text ->
            val returnValue = downVoiceFileToBuffer(text)
            Napier.e { "return value $returnValue" }


            emit(Result.Failure(index.toString()))
//            if(pttsNet.SpeechBuffer != null) {
//                pttsNet.SpeechBuffer?.let { byteArray ->
//                    val ttsFile = File(baseDir.path, "${index}.mp3")
//                    val tempFile = File(ttsFile.parentFile, "${ttsFile.name}.tmp")
//
//                    BufferedOutputStream(FileOutputStream(tempFile)).use { outputStream ->
//                        outputStream.write(byteArray)
//                    }
//                    tempFile.renameTo(ttsFile)
//
//                    emit(Result.Success(Pair(index, ttsFile.name)))
//                }
//            } else {
//                Napier.e { "pttsNet.SpeechBuffer is null ${pttsNet.errorMsg}" }
//                emit(Result.Failure(index.toString()))
//            }
        }
    }.flowOn(Dispatchers.IO)

    // return이 0 이상이면 성공
    private fun downVoiceFileToBuffer(text: String): Int {
        return try {
            0
//            pttsNet.PTTSNET_BUFFER(
//                SelvasConfig.SERVICE_HOST,
//                SelvasConfig.SERVICE_PORT,
//                SelvasConfig.CONN_TIMEOUT,
//                SelvasConfig.READ_TIMEOUT,
//                text,
//                SelvasConfig.LANGUAGE,
//                SelvasConfig.SPEAKER_2,
//                Pttsnet.PTTSNET_FMT22K_16BIT_WAVE,
//                SelvasConfig.PROP_PITCH,
//                SelvasConfig.PROP_SPEED,
//                SelvasConfig.PROP_VOLUME,
//                SelvasConfig.OPT_NFLAG,
//                SelvasConfig.OPT_CONTENT_TYPE,
//                SelvasConfig.OPT_CHARACTER_SET
//            )
        } catch (e: Exception) {
            Napier.e(throwable = e, message = "pttsNet Error")
            0
        }
    }
}