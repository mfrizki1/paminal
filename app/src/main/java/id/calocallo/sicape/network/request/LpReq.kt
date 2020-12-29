package id.calocallo.sicape.network.request

import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpSaksiResp


data class LpReq(
    var id_personel_dilapor: Int?,
    var id_personel_terlapor: Int?,
    var no_lp: String?,
    var id_pelanggaran: Int?,
    var alat_bukti: String?,
    var listPasal: ArrayList<LpPasalResp>?,
    var listSaksi: MutableList<LpSaksiResp>?,
    var keterangan: String?

) {
    constructor() : this(0, 0, "", 0,"", ArrayList(), ArrayList(), "")
}