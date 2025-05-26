package com.example.data.di

import com.example.data.repository.SettingJapaneseImpl
import com.example.domain.repository.repository.SettingJapanese
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingLanguageModule {

    @Binds
    @Singleton
    fun bindSettingJapanese(settingJapaneseImpl: SettingJapaneseImpl): SettingJapanese

}