package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.PutKkeOnRpphModel

data class RpphReq(
    var no_rpph:String?,
    var id_lp:Int?,
    var nama_dinas:String?,
    var no_nota_dinas:String?,
    var tanggal_nota_dinas:String?,
    var penerima_salinan_keputusan:String?,
    var kota_penetapan:String?,
    var tanggal_penetapan:String?,
    var nama_kabid_propam:String?,
    var pangkat_kabid_propam:String?,
    var nrp_kabid_propam:String?
) {
    constructor() : this(
        null, null, null, null,
        null, null, null, null,
        null, null, null
    )
}