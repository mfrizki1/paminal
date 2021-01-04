package id.calocallo.sicape.network.request

data class PasalReq (
    var nama_pasal: String?,
    var isi_pasal: String?
){
    constructor():this("","")
}