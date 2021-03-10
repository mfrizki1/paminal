package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel


data class SktbReq(
    var id_lp: String?,/*ADD*/
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_yang_menetapkan: String?,
    var pangkat_kabid_propam: String?,
    var nrp_kabid_propam: String?,
    var tembusan: String?

) {
    constructor() : this(
        null, null, null, null,
        null, null, null
    )
}