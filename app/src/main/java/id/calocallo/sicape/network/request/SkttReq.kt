package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd

data class SkttReq(
    var id_lp: Int?,/*ADD*/
    var no_laporan_hasil_audit_investigasi: String?,
    var kota_penetapan: String?,
    var tanggal_penetapan: String?,
    var nama_kabid_propam: String?,
    var pangkat_kabid_propam: String?,
    var nrp_kabid_propam: String?,
    var tembusan: String?

    /*var id_lhp: Int?,
    var id_lp: Int?,
    var no_sktt: String?,
    var menimbang: String?,
    var mengingat_p5: String?,
    var tanggal_penetapan: String?,
    var kota_penetapan: String?,
    var jabatan_yang_menetapkan: String?,
    var nrp_kabid_propam: String?,
    var tembusan: String?*/
) {
    constructor() : this(
        null, null, null, null, null, null,
        null, null
    )
}