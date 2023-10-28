package com.golnegari.core.network.model

import com.golnegari.core.network.base.BaseJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailJsonModel(
    @SerialName("backdrop_path")
    val backdrop_path: String?,

    @SerialName("genres")
    val genres: List<GenreJsonModel>?,

    @SerialName("id")
    val id: Int?,

    @SerialName("original_language")
    val original_language: String?,

    @SerialName("original_title")
    val original_title: String?,

    @SerialName("overview")
    val overview: String?,

    @SerialName("poster_path")
    val poster_path: String?,

    @SerialName("release_date")
    val release_date: String?,

    @SerialName("vote_average")
    val vote_average: Double?,

    @SerialName("vote_count")
    val vote_count: Int?
) : BaseJson
