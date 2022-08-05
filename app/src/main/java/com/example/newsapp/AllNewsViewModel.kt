package com.example.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


class AllNewsViewModel : ViewModel() {

    private val _news = MutableStateFlow<List<Content>>(emptyList())
    val news get() = _news.asStateFlow()

    private val _openDetailFragment = MutableSharedFlow<Content>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val openDetailFragment get() = _openDetailFragment.asSharedFlow()


    fun getNews() {
        viewModelScope.launch {
            val respons = RetrofitClient.newsService().getNews()

            println(respons)
            if (respons.isSuccessful) {
                val body: News? = respons.body()
                _news.value = body?.content ?: emptyList()
            } else {
                val errorBody = respons.errorBody()
            }
        }
    }

    fun onItemClick(itemId: String) {
        val list = news.value
        val item = list.firstOrNull { it.id == itemId }

        _openDetailFragment.tryEmit(item!!)
    }
}
