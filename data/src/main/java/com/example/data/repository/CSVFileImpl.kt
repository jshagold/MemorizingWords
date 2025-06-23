package com.example.data.repository

import com.example.database.dao.JapaneseDao
import com.example.domain.repository.repository.CSVFile
import javax.inject.Inject

class CSVFileImpl @Inject constructor(
    private val japaneseDao: JapaneseDao
) : CSVFile {
    override fun exportToCSVFromJapanese() {
        japaneseDao.getAllWords()



    }
}