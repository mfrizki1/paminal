package id.calocallo.sicape.network.request

data class LpPidanaReq(
//    var no_lp: String?,
    var nama_kep_spkt: String?,
    var pangkat_kep_spkt: String?,
    var nrp_kep_spkt: String?,
    var jabatan_kep_spkt: String?,
//    var kesatuan_yang_mengetahui: String?,
//    var status_pelapor: String?,
//    var pembukaan_laporan: String?,
//    var id_personel_pelapor: Int?,
    var id_satuan_kerja: Int?,
    var id_personel_terlapor: Int?,/*1*/
    var kota_buat_laporan: String?,/*1*/
    var tanggal_buat_laporan: String?,/*1*/
    var waktu_buat_laporan: String?,/*1*/

    var isi_laporan: String?,/*1*/
    var nama_pelapor: String?,/*1*/
    var agama_pelapor: String?,/*1*/
    var pekerjaan_pelapor: String?,/*1*/
    var kewarganegaraan_pelapor: String?,/*1*/
    var alamat_pelapor: String?,/*1*/
    var no_telp_pelapor: String?,/*1*/
    var nik_ktp_pelapor: String?,/*1*/
    var jenis_kelamin_pelapor: String?,/*1*/
    var tempat_lahir_pelapor: String?,/*1*/
    var tanggal_lahir_pelapor: String?,/*1*/

    var uraian_pelanggaran: String?,/*1*/
    var pasal_dilanggar: ArrayList<ListIdPasalReq>?/*1*/
//    var listSaksi: MutableList<LpSaksiResp>?

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
        null
//        null,
//        null,
//        null
    )
}