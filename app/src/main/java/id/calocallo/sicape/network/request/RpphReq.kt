package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.PutKkeOnRpphModel

data class RpphReq(
    var id_putkke: Int?,
    var no_rpph: String?,
    var dasar_ph: String?,
    var isi_rekomendasi: String?,
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
        null, null, null
    )
}