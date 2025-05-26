package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.MemorizingWordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMemorizingWordDatabase(
        @ApplicationContext appContext: Context
    ): MemorizingWordDatabase = Room.databaseBuilder(
        context = appContext,
        klass = MemorizingWordDatabase::class.java,
        name = "memorizing_word_database",
    )
        .addMigrations()
        .build()

}