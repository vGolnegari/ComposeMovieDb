package com.golnegari.core.domain.base


sealed class DataResult<DomainModel : BaseDomainModel> {

    data class Loading<DomainModel : BaseDomainModel>(
        val data: DomainModel? = null,
        val isCommon: Boolean = true
    ) : DataResult<DomainModel>()

    data class Success<DomainModel : BaseDomainModel> constructor(val data: DomainModel) :
        DataResult<DomainModel>()

    data class Error<DomainModel : BaseDomainModel> constructor(
        val type: DataResultErrorType,
    ) : DataResult<DomainModel>()
}

enum class DataResultErrorType {
    NETWORK_GENERIC_ERROR, INTERNET_CONNECTION_FAILED,
}
