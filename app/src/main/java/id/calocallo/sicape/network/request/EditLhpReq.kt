package id.calocallo.sicape.network.request

data class EditLhpReq(
    var no_lhp: String?,/**/
    var tentang: String?,/**/
    var no_surat_perintah_penyelidikan: String?,/**/
    var tanggal_mulai_penyelidikan: String?,/**/
//    var daerah_penyelidikan: String?,
    var tugas_pokok: String?,/**/
    var pokok_permasalahan: String?,/**/
    var keterangan_ahli: String?,/**/
    var kesimpulan: String?,/**/
    var rekomendasi: String?,/**/
    var kota_buat_laporan: String?,/**/
    var tanggal_buat_laporan: String?,/**/

    //textmultiline
    var surat: String?,/**/
    var petunjuk: String?,/**/
    var barang_bukti: String?,/**/
    var analisa: String?,/**/
    var is_terbukti: Int?
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
        null
    )
}