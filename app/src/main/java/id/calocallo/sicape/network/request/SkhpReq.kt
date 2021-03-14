package id.calocallo.sicape.network.request

data class SkhpReq(
    var id_personel: Int?,
    var is_memenuhi_syarat: Int?,
    var tanggal_kenaikan_pangkat: String?,
    var kota_keluar: String?,
    var tanggal_keluar: String?,
    var nama_kabid_propam: String?,
    var pangkat_kabid_propam: String?,
    var nrp_kabid_propam: String?,
    var kepada_penerima: String?,
    var kota_penerima: String?
/*    var no_skhp: String?,
    var id_personel: Int?,
    var var_memiliki_pelanggaran_pidana: Int?,
    var var_memiliki_pelanggaran_kode_etik: Int?,
    var var_memiliki_pelanggaran_dvariplin: Int?,
    var var_status_selesai: Int?,
    var hasil_keputusan: String?,
    var kota_keluar: String?,
    var tanggal_keluar: String?,
    var jabatan_yang_mengeluarkan: String?,
    var nama_yang_mengeluarkan: String?,
    var pangkat_yang_mengeluarkan: String?,
    var nrp_yang_mengeluarkan: String?,
    var kepada: String?*/
) {
    constructor() : this(
        null, null, null, null, null, null,
        null, null, null, null
    )
}