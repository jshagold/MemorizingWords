package com.example.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.constants.Paging.PAGE_SIZE
import com.example.data.mapper.toData
import com.example.data.mapper.toDomain
import com.example.database.dao.JapaneseDao
import com.example.domain.repository.model.JapaneseWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomJapaneseWordPagingSource @Inject constructor(
    private val japaneseDao: JapaneseDao,
    private val idList: List<Long>,
) : PagingSource<Int, JapaneseWord>() {
    override fun getRefreshKey(state: PagingState<Int, JapaneseWord>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JapaneseWord> {
        return try {
            val page = params.key ?: 1
            val wordList: List<JapaneseWord> = withContext(Dispatchers.IO) {
                val list = mutableListOf<JapaneseWord>()
                idList.drop((page-1) * PAGE_SIZE).take(PAGE_SIZE).forEach { id ->
                    japaneseDao.getWordById(id).last()?.let {
                        list.add(it.toData().toDomain())
                    }
                }

                list
            }

            LoadResult.Page(
                data = wordList,
                prevKey = if(page == 1) null else page - 1,
                nextKey = if(wordList.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}