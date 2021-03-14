package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkhpMinResp(
    var id:Int?,
    var no_skhp:String?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at:String?
):Parcelable
