package id.calocallo.sicape.network.request

import id.calocallo.sicape.network.response.PasalDilanggarResp
import id.calocallo.sicape.network.response.LpSaksiResp

data class LpKkeReq(
//    var no_lp: String?,
    var id_satuan_kerja: Int?,
    var uraian_pelanggaran: String?,/**/
    var id_personel_terlapor: Int?,/**/
    var kota_buat_laporan: String?,/**/
    var tanggal_buat_laporan: String?,/**/
    var nama_kep_spkt: String?,/**/
    var pangkat_kep_spkt: String?,/**/
    var nrp_kep_spkt: String?,/**/
    var jabatan_kep_spkt: String?,/**/
//    var kesatuan_yang_mengetahui: String?,
//    var id_personel_operator: Int?,

    var isi_laporan: String?,/**/
    var alat_bukti: String?,/**/
    var id_personel_pelapor: Int?,/**/
    var pasal_dilanggar: ArrayList<ListIdPasalReq>?,/**/
    var saksi_kode_etik: ArrayList<SaksiReq>?/**/
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
        null
    )
}