package com.golnegari.core.network.model

import com.golnegari.core.network.base.BaseJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieJsonModel(
    @SerialName("id") val id: Int?,
    @SerialName("title") val title: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null
) : BaseJson
