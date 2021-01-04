package id.calocallo.sicape.network.request

data class SaksiLhpReq(
    var status_saksi: String?,
    var id_personel: Int?,
    var nama: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var isi_keterangan_saksi: String?
) {
    constructor() : this(
        "", 0, "",
        "", "", "", "", ""
    )
}