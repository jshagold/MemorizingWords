package com.example.data.di

import com.example.data.repository.CSVFileImpl
import com.example.domain.repository.repository.CSVFile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CSVModule {

    @Binds
    @Singleton
    fun bindCSVFile(csvFileImpl: CSVFileImpl): CSVFile

}