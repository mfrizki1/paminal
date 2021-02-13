package id.calocallo.sicape.network.request

data class RelasiReq(
    var nama: String?,
    var lokasi: String?
){
    constructor():this(null, null)
}