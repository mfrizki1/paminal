package id.calocallo.sicape.network.request

data class ChangePassReq(
    var password: String?,
    var password_confirmation: String?
){
    constructor():this(null, null)
}