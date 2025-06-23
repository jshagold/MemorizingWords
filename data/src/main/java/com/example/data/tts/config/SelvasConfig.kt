package com.example.data.tts.config

import com.example.data.BuildConfig

object SelvasConfig {
    const val SERVICE_HOST: String = BuildConfig.SELVAS_TTS_HOST
    const val SERVICE_PORT: String = BuildConfig.SELVAS_TTS_PORT

    const val CONN_TIMEOUT: Int = 3
    const val READ_TIMEOUT: Int = 60

    const val PROP_PITCH: Int = 100
    const val PROP_SPEED: Int = 100
    const val PROP_VOLUME: Int = 100

    const val OPT_CHARACTER_SET: Int = 1 // 0: KSC5601 / 1: UTF-8
    const val OPT_NFLAG: Int = -1 // NO USE
    const val OPT_CONTENT_TYPE: Int = -1 // Text/Plain

    const val LANGUAGE: Int = 0 // korean
    
    const val SPEAKER_1: Int = 24 // 이나
    const val SPEAKER_2: Int = 29 // 진우
}