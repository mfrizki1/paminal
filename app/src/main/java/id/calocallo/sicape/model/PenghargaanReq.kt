package id.calocallo.sicape.model

data class PenghargaanReq(
    var penghargaan: String?,
    var diterima_dari: String?,
    var dalam_rangka: String?,
    var tahun: String?,
    var keterangan: String?
) {
    constructor() : this("", "", "", "", "")
}