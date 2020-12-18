package id.calocallo.sicape.network.request

data class SinglePendReq(
    var pendidikan: String,
    var tahun_awal: String,
    var tahun_akhir: String,
    var kota: String,
    var yang_membiayai: String,
    var keterangan: String?
) {
    constructor() : this("", "", "", "", "", "")
}