package id.calocallo.sicape.network.request

data class SipilPelaporReq(
    var nama_sipil: String?,
    var agama_sipil: String?,
    var pekerjaan_sipil: String?,
    var kewarganegaraan_sipil: String?,
    var alamat_sipil: String?,
    var no_telp_sipil: String?,
    var nik_sipil: String?
) {
    constructor() : this("", "", "", "", "", "", "")
}