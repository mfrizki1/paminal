package id.calocallo.sicape.network.request

data class AddSinglePekerjaanReq(
    var pekerjaan:String?,
    var golongan: String?,
    var instansi: String?,
    var berapa_tahun: String?,
    var keterangan: String?
) {
    constructor() : this("","","","","")
}