package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddLhpResp(
    var lhp: LhpResp?
) : Parcelable

@Parcelize
data class LhpResp(
    var id: Int?,/**/
    var no_lhp: String?,/**/
    var tentang: String?,/**/
    var no_surat_perintah_penyelidikan: String?,/**/
    var tugas_pokok: String?,/**/
    var pokok_permasalahan: String?,/**/
    var keterangan_ahli: String?,/**/
    var kesimpulan: String?,/**/
    var rekomendasi: String?,/**/
    var kota_buat_laporan: String?,/**/
    var tanggal_buat_laporan: String?,/**/
    var status_penyelidikan: String?,/**/

    var referensi_penyelidikan: ArrayList<RefPenyelidikanResp>?,/**/
    var personel_penyelidik: ArrayList<PersonelPenyelidikResp>?,/**/
//    var keterangan_terlapor: ArrayList<KetTerlaporLhpResp>?,
    var is_ada_dokumen: Int?,/**/
    var dokumen: DokResp?,
    var user_creator: UserCreatorResp?,/**/
    var user_updater: UserCreatorResp?,/**/
    var user_deleter: UserCreatorResp?,/**/
    var created_at: String?,/**/
    var updated_at: String?,/**/
    var deleted_at: String?,/**/

    //textmultiline
    var surat: String?,
    var petunjuk: String?,
    var barang_bukti: String?,
    var analisa: String?,
    var isTerbukti: Int?,
    var tanggal_mulai_penyelidikan: String?,
    var wilayah_hukum_penyelidikan: String?

) : Parcelable {
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
        null,
        null
    )
}







