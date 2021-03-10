package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LhgResp(
    val id: Int?,
    val dugaan: String?,
    val pangkat_yang_menangani: String?,
    val nama_yang_menangani: String?,
    val dasar: String?,
    val tanggal: String?,
    val waktu_mulai: String?,
    val waktu_selesai: String?,
    val tempat: String?,
    val pangkat_pimpinan: String?,
    val nama_pimpinan: String?,
    val nrp_pimpinan: String?,
    val pangkat_pemapar: String?,
    val nama_pemapar: String?,
    val kronologis_kasus: String?,
    val no_surat_perintah_penyidikan: String?,
    val nama_notulen: String?,
    val pangkat_notulen: String?,
    val nrp_notulen: String?,
    val peserta_gelar: ArrayList<PesertaLhgResp>?,
    val is_ada_dokumen: Int?,
    val dokumen: DokResp?,
    val user_creator: UserResp?,
    val user_updater: UserResp?,
    val user_deleter: UserResp?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
):Parcelable

@Parcelize
data class AddLhgResp(
    var lhg: LhgResp?
):Parcelable
