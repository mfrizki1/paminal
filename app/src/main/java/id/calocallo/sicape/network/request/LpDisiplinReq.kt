package id.calocallo.sicape.network.request

data class LpDisiplinReq(
    var no_lp: String?,/**/
    var id_satuan_kerja: Int?,/**/
    var uraian_pelanggaran: String?,/**/
    var id_personel_terlapor: Int?,/**/
    var id_personel_pelapor: Int?,/**/
    var kota_buat_laporan: String?,/**/
    var tanggal_buat_laporan: String?,/**/
    var nama_kep_spkt: String?,/**/
    var pangkat_kep_spkt: String?,/**/
    var nrp_kep_spkt: String?,/**/
    var jabatan_kep_spkt: String?,/**/
    var kesatuan_yang_mengetahui: String?,
//    var id_personel_operator: Int?,






    var macam_pelanggaran: String?,/**/
    var keterangan_pelapor: String?,/**/
    var kronologis_dari_pelapor: String?,/**/
    var rincian_pelanggaran_disiplin: String?,/**/
    var waktu_buat_laporan: String?,/**/
    var pasal_dilanggar: ArrayList<ListIdPasalReq>?/**/
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
//        null,
        null
    )
}