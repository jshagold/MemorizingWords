package com.example.data.di

import com.example.data.repository.StudyJapaneseImpl
import com.example.domain.repository.repository.StudyJapanese
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StudyLanguageModule {

    @Binds
    @Singleton
    fun bindStudyJapanese(studyJapaneseImpl: StudyJapaneseImpl): StudyJapanese
}