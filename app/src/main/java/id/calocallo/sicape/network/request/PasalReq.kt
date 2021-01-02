package id.calocallo.sicape.network.request

data class PasalReq (
    var nama_pasal: String,
    var detail_pasal: String
){
    constructor():this("","")
}