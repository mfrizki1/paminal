package id.calocallo.sicape.network.request

data class TindDisiplinReq(
    var id_personel: Int?,
    var isi_tindakan_disiplin : String?,
    var keterangan: String?
){
    constructor():this(null,null,null)
}