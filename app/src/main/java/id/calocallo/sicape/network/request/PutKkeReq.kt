package id.calocallo.sicape.network.request

data class PutKkeReq(
    var id_lhp: Int?,
    var id_lp: Int?,
    var no_putkke: String?,
    var pembukaan_putusan: String?,
    var menimbang_p2: String?,
    var mengingat_p4: String?,
    var memperhatikan_p1: String?,
    var memperhatikan_p3: String?,
    var memperhatikan_p5: String?,
    var sanksi_rekomendasi: String?,
    var memutuskan_p1: String?,
    var sanksi_hasil_keputusan: String?,
    var nama_ketua_komisi: String?,
    var pangkat_ketua_komisi: String?,
    var nrp_ketua_komisi: String?,
    var nama_wakil_ketua_komisi: String?,
    var pangkat_wakil_ketua_komisi: String?,
    var nrp_wakil_ketua_komisi: String?,
    var nama_anggota_komisi: String?,
    var pangkat_anggota_komisi: String?,
    var nrp_anggota_komisi: String?,
    var kota_putusan: String?,
    var tanggal_putusan: String?
) {
    constructor() : this(
        null, null, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null
    )
}