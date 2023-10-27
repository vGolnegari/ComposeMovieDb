package com.golnegari.core.network.model

import com.golnegari.core.network.base.BaseJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreJsonModel(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
) : BaseJson
