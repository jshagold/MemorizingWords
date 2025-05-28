package com.example.memorizingwords.di

import com.example.memorizingwords.trigger.JapaneseWordPagingRefreshTrigger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TriggerModule {

    @Provides
    @Singleton
    fun providesJapaneseWordPagingRefreshTrigger(): JapaneseWordPagingRefreshTrigger = JapaneseWordPagingRefreshTrigger()
}