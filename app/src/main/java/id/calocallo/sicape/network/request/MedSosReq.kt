package id.calocallo.sicape.network.request

data class MedSosReq (
    var nama_medsos: String?,
    var nama_akun: String?,
    var alasan: String?,
    var keterangan: String?
){
    constructor():this("","","","")
}