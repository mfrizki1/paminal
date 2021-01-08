package id.calocallo.sicape.network.request

data class SkhdReq(
    var id_lhp: Int?,
    var id_lp: Int?,
    var bidang: String?,
    var no_skhd: String?,
    var menimbang_p2: String?,
    var memperlihatkan: String?,
    var hukuman: String?,
    var tanggal_disampaikan_ke_terhukum: String?,
    var waktu_disampaikan_ke_terhukum: String?,
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
        null, null, null, null,
        null, null, null, null
    )
}