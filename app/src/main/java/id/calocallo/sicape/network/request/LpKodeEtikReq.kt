package id.calocallo.sicape.network.request

import id.calocallo.sicape.network.response.LpPasalResp

data class LpKodeEtikReq(
    var no_lp: String?,
    var kategori: String?,
    var id_personel_terlapor: Int?,
    var id_pelanggaran: Int?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
    var id_personel_operator: Int?,

    var isi_laporan: String?,
    var alat_bukti: String?,
    var id_personel_pelapor: Int?,
    var listPasal: ArrayList<LpPasalResp>?
) {
    constructor() : this(
        "",  "", 0, 0,
        "", "", "", "", "",
        "", 0, "", "", 0, ArrayList()
    )
}