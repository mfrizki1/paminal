package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SktbResp(
    var id:Int?,
    var no_sktb:String?,
    var lp:LpResp?,
    var kota_penetapan:String?,
    var tanggal_penetapan:String?,
    var nama_yang_mengetahui:String?,
    var pangkat_yang_mengetahui:String?,
    var nrp_yang_mengetahui:String?,
    var tembusan:String?,
    var is_ada_dokumen:Int?,
    var dokumen:DokResp?,
    var user_creator:UserResp?,
    var user_updater:UserResp?,
    var user_deleter:UserResp?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at:String?
) : Parcelable

@Parcelize
data class AddSktbResp(
   var sktb: SktbResp?
) : Parcelable