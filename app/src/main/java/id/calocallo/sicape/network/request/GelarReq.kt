package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.TanggPesertaModel

data class GelarReq(
    /*gelar1*/
    var tentang: String?,
    var dasar_dasar: String?,
    var tanggal: String?,
    var pukul: String?,
    var tempat: String?,
    var uraian: String?,
    var hari: String?,
    var id_pemapar: Int?,
    var id_peserta: ArrayList<Int>?,
    var id_pimpinan: Int?,

    /*Tanggapan*/
    var list_tanggapan: ArrayList<TanggPesertaModel>,

    /*paparan*/
    var dasar_paparan: String?,
    var kronologis_paparan: String?,

    /*gelar4*/
    var kesimpulan: String?,
    var rekomendasi: String?,
    var kota_dibuat: String?,
    var tanggal_dibuat: String?,

    var nama_pimpinan: String?,
    var nrp_pimpinan: String?,
    var pangkat_pimpinan: String?,

    var nama_notulen: String?,
    var nrp_notulen: String?,
    var pangkat_notulen: String?

) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        ArrayList(),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}