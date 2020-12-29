package id.calocallo.sicape.network.request

data class SahabatReq(
    var nama: String?,
    var jenis_kelamin:String?,
    var umur: String?,
    var alamat: String?,
    var pekerjaan: String?,
    var alasan: String?,
    var keterangan:String?
){
    constructor():this("","","","","","","")
}