package com.example.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*


class AllNewsViewModel : ViewModel() {

    private val _newsState = MutableStateFlow<Resource>(Resource.Loading)
    val newsState get() = _newsState.asStateFlow()

    private val _openDetailFragment = MutableSharedFlow<Content>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val openDetailFragment get() = _openDetailFragment.asSharedFlow()

    private var items: List<Content> = emptyList()


    fun getNews() {
        viewModelScope.launch {
            val respons = RetrofitClient.newsService().getNews()

            println(respons)
            if (respons.isSuccessful) {
                val body: News? = respons.body()
                _newsState.value = Resource.Success(body?.content ?: emptyList())

                items = body?.content ?: emptyList()
            } else {
                val errorBody = respons.errorBody()
                _newsState.value = Resource.Error(errorBody?.toString() ?: "")
            }
        }
    }

    fun onItemClick(itemId: String) {
        val list = items
        val item = list.firstOrNull { it.id == itemId }

        _openDetailFragment.tryEmit(item!!)
    }
}
