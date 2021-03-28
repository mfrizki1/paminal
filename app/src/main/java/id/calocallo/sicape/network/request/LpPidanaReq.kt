package id.calocallo.sicape.network.request

data class LpPidanaReq(
    var no_lp: String?,
    var uraian_pelanggaran: String?,/*1*/
    var kota_buat_laporan: String?,/*1*/
    var tanggal_buat_laporan: String?,/*1*/
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
    var waktu_buat_laporan: String?,/*1*/
    var pasal_dilanggar: ArrayList<ListIdPasalReq>?,/*1*/


    /*lhp*/
    var id_lhp: Int?,
    var id_personel_terlapor_lhp: Int?,
    var isi_laporan: String?,/*1*/

    /*personel*/
    var id_personel_pelapor: Int?,
    var id_personel_terlapor: Int?,

    /*sipil*/
    var nama_pelapor: String?,/*1*/
    var tempat_lahir_pelapor: String?,/*1*/
    var tanggal_lahir_pelapor: String?,/*1*/
    var agama_pelapor: String?,/*1*/
    var pekerjaan_pelapor: String?,/*1*/
    var kewarganegaraan_pelapor: String?,/*1*/
    var alamat_pelapor: String?,/*1*/
    var no_telp_pelapor: String?,/*1*/
    var nik_ktp_pelapor: String?,/*1*/
    var jenis_kelamin_pelapor: String?/*1*/

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
        null,
        null,
        null,
        null
    )
}