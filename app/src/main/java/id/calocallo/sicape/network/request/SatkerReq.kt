package id.calocallo.sicape.network.request

data class SatkerReq(
    var kesatuan:String?,
    var alamat_kantor:String?,
    var  no_telp_kantor:String?
){
    constructor():this(null,null, null)
}

data class SettingReq(
    var alamat_kantor_polda:String?,
    var no_telp_kantor_polda:String?
){
    constructor():this(null,null)
}
