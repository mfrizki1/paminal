package id.calocallo.sicape.network.request

data class LpReqEdit(
    var id_personel_dilapor: Int?,
    var id_personel_terlapor: Int?,
    var no_lp: String?,
    var id_pelanggaran: Int?,
    var alat_bukti: String?,
    var keterangan: String?
) {
    constructor() : this(0, 0, "", 0, "","")
}