package com.golnegari.core.network.model

import com.golnegari.core.network.base.BaseJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListJson(
    @SerialName("page") val page : Int?,
    @SerialName("results") val result: List<MovieJsonModel>?
) : BaseJson
