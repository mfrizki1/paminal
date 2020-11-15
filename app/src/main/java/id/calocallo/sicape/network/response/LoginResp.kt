package id.calocallo.sicape.network.response

data class LoginResp(
    val status: Int,
    val token: String,
    val hak_akses: String
)