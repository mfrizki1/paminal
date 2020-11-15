package id.calocallo.sicape.model

data class PendUmumModel(
    var id: Int,
    var pendidikan: String,
    var tahun_awal: String,
    var tahun_akhir: String,
    var kota: String,
    var yang_membiayai: String,
    var keterangan: String?
) {
    constructor() : this(0,"", "", "", "", "", "")
}