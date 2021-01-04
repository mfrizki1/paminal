package id.calocallo.sicape.network.request

data class EditLpKkeReq(
    var no_lp: String?,
    var uraian_pelanggaran: String?,
    var id_personel_terlapor: Int?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
//    var id_personel_operator: Int?,

    var isi_laporan: String?,
    var alat_bukti: String?,
    var id_personel_pelapor: Int?
){
    constructor() : this(
        "",  "", 0,
        "", "", "", "", "",
        "",  "", "", 0
    )
}