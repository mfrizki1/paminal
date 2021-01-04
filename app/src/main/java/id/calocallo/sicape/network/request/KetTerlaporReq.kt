package id.calocallo.sicape.network.request

data class KetTerlaporReq(
    var id_personel: Int?,
    var isi_keterangan_terlapor: String?
) {
    constructor() : this(0, "")
}