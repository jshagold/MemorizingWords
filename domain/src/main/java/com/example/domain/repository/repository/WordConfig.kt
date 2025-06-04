package com.example.domain.repository.repository

import kotlinx.coroutines.flow.Flow

interface WordConfig {

    fun getAllJapaneseIdList(): Flow<List<Long>>

}