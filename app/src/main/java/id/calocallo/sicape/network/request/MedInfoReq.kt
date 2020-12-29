package id.calocallo.sicape.network.request

data class MedInfoReq (
    var sumber: String?,
    var topik: String?,
    var alasan: String?,
    var keterangan: String?
){
    constructor():this("","","","")
}