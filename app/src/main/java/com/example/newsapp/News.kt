package com.example.newsapp

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val content: List<Content>
):Parcelable


@Parcelize
data class Content(
    val id: String,
    val descriptionEN: String,
    val descriptionKA: String? = null,
    val descriptionRU: String? = null,
    val titleEN: String? = null,
    val titleKA: String? = null,
    val titleRU: String? = null,
    val published: Int? = null,
    val publish_date: String? = null,
    @Json(name="created_at")
    val createdAt: Long,
    @Json(name="updated_at")
    val updatedAt: Long? = null,
    val category: String? = null,
    val cover: String? = null,
    val isLast: Boolean? = null
): Parcelable