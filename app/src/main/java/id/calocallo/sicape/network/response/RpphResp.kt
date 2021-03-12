package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PutKkeOnRpphModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RpphResp(
    var id:Int?,
    var no_rpph:String?,
    var lp:LpResp?,
    var nama_dinas:String?,
    var no_nota_dinas:String?,
    var tanggal_nota_dinas:String?,
    var penerima_salinan_keputusan:String?,
    var kota_penetapan:String?,
    var tanggal_penetapan:String?,
    var nama_kabid_propam:String?,
    var pangkat_kabid_propam:String?,
    var nrp_kabid_propam:String?,
    var is_ada_dokumen:Int?,
    var dokumen:DokResp?,
    var user_creator:UserResp?,
    var user_updater:UserResp?,
    var user_deleter:UserResp?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at:String?
    /*var id: Int?,
    var putkke: PutKkeOnRpphModel?,
    var no_rpph: String?,
    var dasar_ph: String?,
    var isi_rekomendasi: String?,
    var tanggal_penetapan: String?,
    var kota_penetapan: String?,
    var jabatan_yang_menetapkan: String?,
    var nama_yang_menetapkan: String?,
    var pangkat_yang_menetapkan: String?,
    var nrp_yang_menetapkan: String?,
    var tembusan: String?,
    var created_at: String?,
    var updated_at: String?*/
) : Parcelable

@Parcelize
data class AddRpphResp(
    var rpph: RpphResp?
):Parcelable