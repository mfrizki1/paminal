package id.calocallo.sicape.network.request

data class SkhdReq(
    var no_skhd: String?,/**/
    var id_lp: Int?,/**/
    var bidang: String?,/**/
    var no_berkas_perkara: String?,/*!*/
    var tanggal_buat_berkas_perkara: String?,/*!*/
    var tanggal_sidang_disiplin: String?,/*!*/
    var hukuman: String?,/**/
    var tanggal_disampaikan_ke_terhukum: String?,/**/
    var waktu_disampaikan_ke_terhukum: String?,/**/
    var kota_penetapan: String?,/**/
    var tanggal_penetapan: String?,/**/
    var jabatan_yang_menetapkan: String?,/**/
    var kesatuan_yang_menetapkan: String?,/*!*/
    var nama_yang_menetapkan: String?,/**/
    var pangkat_yang_menetapkan: String?,/**/
    var nrp_yang_menetapkan: String?,/**/
    var tembusan: String?/**/

    /*var id_lhp: Int?,
    var menimbang_p2: String?,
    var memperlihatkan: String?*/

) {
    constructor() : this(
        null, null, null, null,
        null, null, null, null,
        null, null, null, null,
        null, null, null, null, null
    )
}