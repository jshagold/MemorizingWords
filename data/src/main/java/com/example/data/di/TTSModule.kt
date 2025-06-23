package com.example.data.di

import com.example.data.datasource.SelvasTTS
import com.example.data.datasource.SelvasTTSImpl
import com.example.data.repository.TTSFileImpl
import com.example.domain.repository.repository.TTSFile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface TTSModule {

    @Binds
    @Singleton
    fun bindTTSFile(ttsFileImpl: TTSFileImpl): TTSFile

    @Binds
    @Singleton
    fun bindSelvasTTS(selvasTTSImpl: SelvasTTSImpl): SelvasTTS
}