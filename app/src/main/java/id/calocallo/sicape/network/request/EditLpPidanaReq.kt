package id.calocallo.sicape.network.request

import id.calocallo.sicape.network.response.LpPasalResp

data class EditLpPidanaReq (
    var no_lp: String?,
//    var id_satuan_kerja: String?,
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
    var alamat_pelapor: String?,
    var no_telp_pelapor: String?,
    var nik_ktp_pelapor: String?,

    var uraian_pelanggaran: String?
){
    constructor() : this(
        "",  0,
        "", "", "", "",
        "", "", "", "", "",
        "", 0, "",
        "","","","",
        "","",""
    )
}