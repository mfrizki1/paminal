package id.calocallo.sicape.network.request

import id.calocallo.sicape.network.response.LpPasalResp

data class LpDisiplinReq(
    var no_lp: String?,
    var uraian_pelanggaran: String?,
    var id_personel_terlapor: Int?,
    var id_personel_pelapor: Int?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
    var id_personel_operator: Int?,

    var macam_pelanggaran: String?,
    var keterangan_pelapor: String?,
    var kronologis_dari_pelapor: String?,
    var rincian_pelanggaran_disiplin: String?,
    var pasal_dilanggar: ArrayList<LpPasalResp>?
) {
    constructor() : this(
        "", "",  0,0, "", "",
        "", "", "", "", 0, "",
        "", "", "", ArrayList()
    )
}