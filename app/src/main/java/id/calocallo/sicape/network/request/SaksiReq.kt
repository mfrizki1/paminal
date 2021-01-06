package id.calocallo.sicape.network.request

data class SaksiReq (
    var nama_saksi: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var isKorban: Int?
){
    constructor():this("","","","","",null)
}