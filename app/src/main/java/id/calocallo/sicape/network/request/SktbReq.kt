package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel


data class SktbReq(
    var id_lp: Int?,/*ADD*/
    var no_sktb: String?,
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var tembusan: String?

) {
    constructor() : this(
        null, null, null, null, null,
        null, null, null
    )
}