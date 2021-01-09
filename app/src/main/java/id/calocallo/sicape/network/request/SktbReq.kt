package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel


data class SktbReq(
    var id_skhd: Int?,
    var id_putkke: Int?,
    var no_sktb: String?,
    var menimbang_p2: String?,
    var mengingat_p5: String?,
    var tanggal_penetapan: String?,
    var kota_penetapan: String?,
    var jabatan_yang_menetapkan: String?,
    var nama_yang_menetapkan: String?,
    var pangkat_yang_menetapkan: String?,
    var nrp_yang_menetapkan: String?,
    var tembusan: String?
) {
    constructor() : this(
        null, null, null, null,
        null, null, null, null,
        null, null, null,
        null
    )
}