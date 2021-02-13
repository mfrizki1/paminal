package id.calocallo.sicape.model

data class AddPendidikanModel(
    var pendidikan: String?,
    var jenis: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var kota: String?,
    var yang_membiayai: String?,
    var keterangan: String?
) {
    constructor() : this(null, null, null, null, null, null, null)
}