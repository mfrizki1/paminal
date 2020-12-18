package id.calocallo.sicape.network.response

data class Base1Resp<T>(
    val message: String,
    val status: Int,
    val data: T
)