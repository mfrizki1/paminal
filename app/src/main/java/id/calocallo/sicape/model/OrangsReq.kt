package id.calocallo.sicape.model

data class OrangsReq(
    var nama: String?,
    var jenis_kelamin: String?,
    var umur: String?,
    var alamat: String?,
    var pekerjaan: String?,
    var keterangan: String?
) {
    constructor() : this("", "", "", "", "", "")
}