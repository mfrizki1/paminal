package id.calocallo.sicape.network.request

data class EditFotoReq (
    var id_foto_kanan: Int?,
    var id_foto_muka: Int?,
    var id_foto_kiri: Int?
){
    constructor():this(0,0,0)
}