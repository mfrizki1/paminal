package id.calocallo.sicape.network.request

data class CatatanPersReq(
    var jenis: String?,
    var keterangan: String?,
    var tanggal_ditahan: String?,
    var tempat_ditahan: String?,
    var tanggal_dihukum: String?,
    var tempat_dihukum: String?
) {
    constructor() : this("", "", "", "", "","")
}