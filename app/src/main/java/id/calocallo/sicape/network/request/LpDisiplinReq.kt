package id.calocallo.sicape.network.request

data class LpDisiplinReq(
    var no_lp: String?,/**/
    var id_satuan_kerja: Int?,/**/
    var uraian_pelanggaran: String?,/**/
    var id_personel_terlapor: Int?,/**/
    var id_personel_pelapor: Int?,/**/
    var kota_buat_laporan: String?,/**/
    var tanggal_buat_laporan: String?,/**/
    var nama_kabid_propam: String?,/**/
    var pangkat_kabid_propam: String?,/**/
    var nrp_kabid_propam: String?,/**/
    var jabatan_kabid_propam: String?,/**/
    var kesatuan_yang_mengetahui: String?,
//    var id_personel_operator: Int?,






    var macam_pelanggaran: String?,/**/
    var keterangan_pelapor: String?,/**/
    var kronologis_dari_pelapor: String?,/**/
    var rincian_pelanggaran_disiplin: String?,/**/
    var waktu_buat_laporan: String?,/**/
    var pasal_dilanggar: ArrayList<ListIdPasalReq>?,/**/
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
        null,
        null,
        null,
//        null,
        null
    )
}