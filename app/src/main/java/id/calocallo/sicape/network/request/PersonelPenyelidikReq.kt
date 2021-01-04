package id.calocallo.sicape.network.request

data class PersonelPenyelidikReq (
    var id_personel: Int?,
    var is_ketua: String?
){
    constructor():this(0,"")
}