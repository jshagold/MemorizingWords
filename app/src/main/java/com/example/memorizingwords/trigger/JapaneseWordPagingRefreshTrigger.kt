package com.example.memorizingwords.trigger

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JapaneseWordPagingRefreshTrigger @Inject constructor() {
    private var _refreshState: MutableStateFlow<Int> = MutableStateFlow(0)
    val refreshState: StateFlow<Int> = _refreshState.asStateFlow()


    fun notifyRefresh() {
        _refreshState.value += 1
    }
}