package id.calocallo.sicape.network.request

data class EditLpPidanaReq(
    var no_lp: String?,
    var id_satuan_kerja: Int?,
    var id_personel_terlapor: Int?,
//    var id_pelanggaran: Int?,
    var kota_buat_laporan: String?,
    var tanggal_buat_laporan: String?,
    var nama_yang_mengetahui: String?,
    var pangkat_yang_mengetahui: String?,
    var nrp_yang_mengetahui: String?,
    var jabatan_yang_mengetahui: String?,
    var kesatuan_yang_mengetahui: String?,

    var status_pelapor: String?,
    var isi_laporan: String?,
    var pembukaan_laporan: String?,
    var id_personel_pelapor: Int?,
    var nama_pelapor: String?,
    var agama_pelapor: String?,
    var pekerjaan_pelapor: String?,
    var kewarganegaraan_pelapor: String?,
    var jenis_kelamin_pelapor: String?,
    var alamat_pelapor: String?,
    var no_telp_pelapor: String?,
    var nik_ktp_pelapor: String?,
    var tempat_lahir_pelapor: String?,/*1*/
    var tanggal_lahir_pelapor: String?,/*1*/
    var uraian_pelanggaran: String?,
    var waktu_buat_laporan: String?
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
        null
    )
}