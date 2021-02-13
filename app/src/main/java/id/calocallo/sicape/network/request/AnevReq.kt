package id.calocallo.sicape.network.request

data class AnevReq(
    var tahun: String?,
    var rentang: String?,
    var semester: String?,
    var bulan: String?,
    var berdasarkan: String?
) {
    constructor() : this(null, null, null, null, null)
}