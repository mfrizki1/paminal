package id.calocallo.sicape.network.request

data class FotoReq(
    var foto_kanan: String?,
    var foto_muka: String?,
    var foto_kiri: String?
){
    constructor():this("","","")
}