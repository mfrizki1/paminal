package id.calocallo.sicape.network.request

data class SkhpReq(
    var no_skhp: String?,
    var id_personel: Int?,
    var is_memiliki_pelanggaran_pidana: Int?,
    var is_memiliki_pelanggaran_kode_etik: Int?,
    var is_memiliki_pelanggaran_disiplin: Int?,
    var is_status_selesai: Int?,
    var hasil_keputusan: String?,
    var kota_keluar: String?,
    var tanggal_keluar: String?,
    var jabatan_yang_mengeluarkan: String?,
    var nama_yang_mengeluarkan: String?,
    var pangkat_yang_mengeluarkan: String?,
    var nrp_yang_mengeluarkan: String?,
    var kepada: String?
) {
    constructor() : this(
        null, null, null, null, null, null,
        null, null, null, null, null,
        null, null, null
    )
}