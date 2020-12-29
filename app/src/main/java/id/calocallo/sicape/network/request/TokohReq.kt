package id.calocallo.sicape.network.request

class TokohReq(
    var nama: String?,
    var asal_negara: String?,
    var alasan: String?,
    var keterangan: String?
){
    constructor():this("","","","")
}