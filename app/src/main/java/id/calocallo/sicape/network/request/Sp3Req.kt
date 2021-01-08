package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel

data class Sp3Req(
    var id_skhd: Int?,
    var id_putkke: Int?,
    var no_sp4: String?,
    var mengingat_p4: String?,
    var mengingat_p5: String?,
    var menetapkan_p1: String?,
    var kota_keluar: String?,
    var tanggal_keluar: String?,
    var tembusan: String?,
    var nama_akreditor: String?,
    var pangkat_akreditor: String?,
    var nrp_akreditor: String?

    ) {
    constructor() : this(
        null, null, null, null, null, null,
        null, null, null, null, null,
        null
    )
}