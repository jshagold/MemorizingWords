package com.example.database.di

import com.example.database.MemorizingWordDatabase
import com.example.database.dao.JapaneseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesJapaneseDao(
        database: MemorizingWordDatabase
    ): JapaneseDao = database.japaneseDao()

}