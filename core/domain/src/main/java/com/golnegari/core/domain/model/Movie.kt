package com.golnegari.core.domain.model

import com.golnegari.core.domain.base.BaseDomainModel

data class Movie(val id: Int, val title: String = "", val posterPath: String = "") : BaseDomainModel
