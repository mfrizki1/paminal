package id.calocallo.sicape.network.request

data class AdminReq(
    var id_personel: Int?,
    var nama: String?,
    var username: String?,
    var password: String?,
    var password_confirmation: String?,
    var is_aktif: Int?
){
    constructor():this(null,null,null,null,null,null)
}