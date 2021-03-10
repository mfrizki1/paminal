package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkttMinResp(
    var id:Int?,
    var no_sktt:String?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at:String?
):Parcelable
