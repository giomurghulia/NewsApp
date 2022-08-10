package com.example.newsapp

sealed class Resource {
    data class Success(val items: List<Content>) : Resource()
    data class Error(val errorMessage: String) : Resource()
    object Loading : Resource()
}